package com.example.iksandecade.jadwalsholat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.iksandecade.jadwalsholat.adapter.MainRecyclerAdapter;
import com.example.iksandecade.jadwalsholat.greendao.DaoHandler;
import com.example.iksandecade.jadwalsholat.greendao.DaoSession;
import com.example.iksandecade.jadwalsholat.greendao.Jadwal;
import com.example.iksandecade.jadwalsholat.model.JadwalBulanModel;
import com.example.iksandecade.jadwalsholat.model.SecondResultModel;
import com.example.iksandecade.jadwalsholat.model.WaktuModel;
import com.example.iksandecade.jadwalsholat.retrofit.JadwalServiceClient;
import com.example.iksandecade.jadwalsholat.retrofit.ServiceGenerator;
import com.example.iksandecade.jadwalsholat.utils.SPJadwalSholat;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    DaoSession daoSession;
    RecyclerView rvMain;
    MainRecyclerAdapter mainRecyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<WaktuModel> waktuModelList = new ArrayList<>();
    private Subscription mSubGetData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        daoSession = DaoHandler.getInstance(this);
        rvMain = (RecyclerView) findViewById(R.id.rvMain);
        mainRecyclerAdapter = new MainRecyclerAdapter(waktuModelList);
        layoutManager = new LinearLayoutManager(getApplicationContext());

        rvMain.setLayoutManager(layoutManager);
        rvMain.setAdapter(mainRecyclerAdapter);


        JadwalServiceClient client = ServiceGenerator.createService(JadwalServiceClient.class);
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd");
        Date date = new Date();
        Log.d("data", dateFormat.format(date) + "");
        String[] data = dateFormat.format(date).split(" ");
        Observable<JadwalBulanModel> call = client.callJadwalTahun(SPJadwalSholat.getKota(this), "7", "0", data[0]);
        mSubGetData = call.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JadwalBulanModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(JadwalBulanModel jadwalBulanModel) {
                        Jadwal jadwal = new Jadwal();
                        Timestamp timestamp = null;
                        boolean isExist = false;
                        List<SecondResultModel> secondResultModels = jadwalBulanModel.getResult();
                        List<Jadwal> jadwals = daoSession.getJadwalDao().queryBuilder().list();
                        for (int m = 0; m < jadwals.size(); m++) {
                            if (jadwalBulanModel.getData().getAddress().equals(jadwals.get(m).getRegion())) {
                                isExist = true;
                            }
                        }
                        if (!isExist) {
                            for (int i = 1; i <= secondResultModels.size(); i++) {
                                try {
                                    String day = secondResultModels.get(i - 1).getDay();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MMM dd", Locale.US);
                                    Date parsedDate = dateFormat.parse("2017 " + day);
                                    timestamp = new Timestamp(parsedDate.getTime());
                                    int position = i - 1;
                                    jadwal.setId(jadwalBulanModel.getData().getAddress() + timestamp.getTime());
                                    jadwal.setSubuh(getTimeStamp(day, secondResultModels.get(position).getTime().getFajr(), i));
                                    jadwal.setDzuhur(getTimeStamp(day, secondResultModels.get(position).getTime().getDhuhr(), i));
                                    jadwal.setAshar(getTimeStamp(day, secondResultModels.get(position).getTime().getAsr(), i));
                                    jadwal.setMagrib(getTimeStamp(day, secondResultModels.get(position).getTime().getMaghrib(), i));
                                    jadwal.setIsya(getTimeStamp(day, secondResultModels.get(position).getTime().getIsha(), i));
                                    jadwal.setRegion(jadwalBulanModel.getData().getAddress());
                                    jadwal.setCreatedAt(System.currentTimeMillis());
                                    jadwal.setUpdatedAt(System.currentTimeMillis());
                                    jadwal.setFilter(timestamp.getTime());
                                    daoSession.getJadwalDao().insert(jadwal);
                                } catch (Exception e) {//this generic but you can control another types of exception
                                    e.printStackTrace();
                                }


                            }
                        } else {
                            Toast.makeText(MainActivity.this, "done", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubGetData != null && !mSubGetData.isUnsubscribed())
            mSubGetData.unsubscribe();
    }

    private long getTimeStamp(String date, String data, int i) {
        long result = 0l;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MMM dd kk:mm", Locale.US);
            Date parsedDate = dateFormat.parse("2017 " + date + " " + data);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            result = timestamp.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
