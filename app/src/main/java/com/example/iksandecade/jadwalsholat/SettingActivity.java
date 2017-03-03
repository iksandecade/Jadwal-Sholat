package com.example.iksandecade.jadwalsholat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.iksandecade.jadwalsholat.greendao.DaoHandler;
import com.example.iksandecade.jadwalsholat.greendao.DaoSession;
import com.example.iksandecade.jadwalsholat.greendao.Jadwal;
import com.example.iksandecade.jadwalsholat.greendao.Kota;
import com.example.iksandecade.jadwalsholat.greendao.KotaDao;
import com.example.iksandecade.jadwalsholat.model.JadwalBulanModel;
import com.example.iksandecade.jadwalsholat.model.JadwalModel;
import com.example.iksandecade.jadwalsholat.model.SecondResultModel;
import com.example.iksandecade.jadwalsholat.receiver.ConnectionReceiver;
import com.example.iksandecade.jadwalsholat.retrofit.JadwalServiceClient;
import com.example.iksandecade.jadwalsholat.retrofit.ServiceGenerator;
import com.example.iksandecade.jadwalsholat.utils.JadwalUtils;
import com.example.iksandecade.jadwalsholat.utils.SPJadwalSholat;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SettingActivity extends AppCompatActivity {

    Spinner spinner;
    DaoSession daoSession;
    Toolbar toolbar;
    String city;
    ProgressDialog progressDialog;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        spinner = (Spinner) findViewById(R.id.spSetting);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        daoSession = DaoHandler.getInstance(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");

        setSupportActionBar(toolbar);

        List<Kota> kotaList = daoSession.getKotaDao().queryBuilder().orderAsc(KotaDao.Properties.NamaKota).list();
        List<String> kotas = new ArrayList<>();
        for (int i = 0; i < kotaList.size(); i++) {
            kotas.add(i, kotaList.get(i).getNamaKota());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kotas);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                city = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            if (ConnectionReceiver.isConnecting(this)) {
                progressDialog.show();
                callAPI();
            } else {
                Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void callAPI() {
        JadwalServiceClient client = ServiceGenerator.createService(JadwalServiceClient.class);
        Observable<JadwalBulanModel> call = client.callJadwalBulan(city, "7", "0", JadwalUtils.getNowYear(), JadwalUtils.getNowMonth());

        subscription = call.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JadwalBulanModel>() {
                    @Override
                    public void onCompleted() {
                        progressDialog.cancel();
                        SPJadwalSholat.setIsFirst(SettingActivity.this, false);
                        Intent intent = new Intent(SettingActivity.this, CalendarV2Activity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(JadwalBulanModel jadwalBulanModel) {
                        try {

                            String region = jadwalBulanModel.getData().getAddress();
                            List<SecondResultModel> secondResultModels = jadwalBulanModel.getResult();
                            Kota kota = daoSession.getKotaDao().queryBuilder().where(KotaDao.Properties.NamaKota.eq(city)).unique();
                            if (!isExist(region, daoSession.getJadwalDao().queryBuilder().list())) {
                                for (int i = 0; i < secondResultModels.size(); i++) {
                                    String day = secondResultModels.get(i).getDay();
                                    String subuh = secondResultModels.get(i).getTime().getFajr();
                                    String dzuhur = secondResultModels.get(i).getTime().getDhuhr();
                                    String ashar = secondResultModels.get(i).getTime().getAsr();
                                    String magrib = secondResultModels.get(i).getTime().getMaghrib();
                                    String isya = secondResultModels.get(i).getTime().getIsha();

                                    Jadwal jadwal = new Jadwal();
                                    jadwal.setId(kota.getIdKota() + JadwalUtils.getDay(day));
                                    jadwal.setSubuh(JadwalUtils.getJadwal(day, subuh));
                                    jadwal.setDzuhur(JadwalUtils.getJadwal(day, dzuhur));
                                    jadwal.setAshar(JadwalUtils.getJadwal(day, ashar));
                                    jadwal.setMagrib(JadwalUtils.getJadwal(day, magrib));
                                    jadwal.setIsya(JadwalUtils.getJadwal(day, isya));
                                    jadwal.setRegion(city);
                                    jadwal.setCreatedAt(System.currentTimeMillis());
                                    jadwal.setFilter(JadwalUtils.getDay(day));
                                    daoSession.getJadwalDao().insert(jadwal);
                                    SPJadwalSholat.setKota(SettingActivity.this, kota.getNamaKota());
//                                    int progress = (secondResultModels.size() / 100) * i;
//                                    progressDialog.setProgress(progress);

                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private boolean isExist(String regionOne, List<Jadwal> jadwals) {
        boolean result = false;
        for (int i = 0; i < jadwals.size(); i++) {
            if (jadwals.get(i).getRegion().equals(regionOne))
                result = true;
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }
}
