package com.example.iksandecade.jadwalsholat;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.iksandecade.jadwalsholat.adapter.MainRecyclerAdapter;
import com.example.iksandecade.jadwalsholat.greendao.DaoHandler;
import com.example.iksandecade.jadwalsholat.greendao.DaoSession;
import com.example.iksandecade.jadwalsholat.greendao.Jadwal;
import com.example.iksandecade.jadwalsholat.greendao.JadwalDao;
import com.example.iksandecade.jadwalsholat.model.JadwalBulanModel;
import com.example.iksandecade.jadwalsholat.model.JadwalModel;
import com.example.iksandecade.jadwalsholat.model.SecondResultModel;
import com.example.iksandecade.jadwalsholat.model.WaktuModel;
import com.example.iksandecade.jadwalsholat.retrofit.JadwalServiceClient;
import com.example.iksandecade.jadwalsholat.retrofit.ServiceGenerator;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    DaoSession daoSession;
    RecyclerView rvMain;
    MainRecyclerAdapter mainRecyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<WaktuModel> waktuModelList = new ArrayList<>();

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
        Observable<JadwalBulanModel> call = client.callJadwalBulan("bandung", "7", "0", data[0], data[1]);
        call.subscribeOn(Schedulers.computation())
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
                        List<SecondResultModel> secondResultModels = jadwalBulanModel.getResult();
                        for (int i = 1; i <= secondResultModels.size(); i++) {
                            try {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM dd");
                                Date parsedDate = dateFormat.parse("2017 02 " + i);
                                timestamp = new java.sql.Timestamp(parsedDate.getTime());
                                int position = i - 1;
                                jadwal.setId(timestamp.getTime());
                                jadwal.setSubuh(getTimeStamp(secondResultModels.get(position).getTime().getFajr(), i));
                                jadwal.setDzuhur(getTimeStamp(secondResultModels.get(position).getTime().getDhuhr(), i));
                                jadwal.setAshar(getTimeStamp(secondResultModels.get(position).getTime().getAsr(), i));
                                jadwal.setMagrib(getTimeStamp(secondResultModels.get(position).getTime().getMaghrib(), i));
                                jadwal.setIsya(getTimeStamp(secondResultModels.get(position).getTime().getIsha(), i));
                                jadwal.setCreatedAt(System.currentTimeMillis());
                                jadwal.setUpdatedAt(System.currentTimeMillis());
                                daoSession.getJadwalDao().insert(jadwal);
                            } catch (Exception e) {//this generic but you can control another types of exception
                                e.printStackTrace();
                            }



                        }

                        List<Jadwal> jadwals = daoSession.getJadwalDao().queryBuilder().where(JadwalDao.Properties.Id.between(1485882000000l, 1486141200000l)).list();
//                        String data = jadwals.get(1).getAshar();

//
//                        WaktuModel waktuModel = new WaktuModel();
//                        waktuModel.setJadwal("Subuh");
//                        waktuModel.setWaktu(jadwal.getSubuh());
//                        waktuModelList.add(0, waktuModel);
//
//                        WaktuModel waktuModel1 = new WaktuModel();
//                        waktuModel1.setJadwal("Dzuhur");
//                        waktuModel1.setWaktu(jadwal.getDzuhur());
//                        waktuModelList.add(1, waktuModel1);
//
//                        WaktuModel waktuModel2 = new WaktuModel();
//                        waktuModel2.setJadwal("Ashar");
//                        waktuModel2.setWaktu(jadwal.getAshar());
//                        waktuModelList.add(2, waktuModel2);
//
//                        WaktuModel waktuModel3 = new WaktuModel();
//                        waktuModel3.setJadwal("Maghrib");
//                        waktuModel3.setWaktu(jadwal.getMagrib());
//                        waktuModelList.add(3, waktuModel3);
//
//                        WaktuModel waktuModel4 = new WaktuModel();
//                        waktuModel4.setJadwal("Isya");
//                        waktuModel4.setWaktu(jadwal.getIsya());
//                        waktuModelList.add(4, waktuModel4);
//
//                        mainRecyclerAdapter.notifyDataSetChanged();
                    }
                });
    }


    private long getTimeStamp(String data, int i) {
        long result = 0l;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM dd kk:mm");
            Date parsedDate = dateFormat.parse("2017 02 " + i + " " + data);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            result = timestamp.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
