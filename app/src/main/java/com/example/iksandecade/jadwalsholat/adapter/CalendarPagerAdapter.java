package com.example.iksandecade.jadwalsholat.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iksandecade.jadwalsholat.R;
import com.example.iksandecade.jadwalsholat.eventbus.Events;
import com.example.iksandecade.jadwalsholat.eventbus.GlobalBus;
import com.example.iksandecade.jadwalsholat.greendao.Jadwal;
import com.example.iksandecade.jadwalsholat.model.WaktuModel;
import com.example.iksandecade.jadwalsholat.utils.SPJadwalSholat;

import java.util.ArrayList;
import java.util.List;

import static com.example.iksandecade.jadwalsholat.utils.JadwalUtils.getSisa;
import static com.example.iksandecade.jadwalsholat.utils.JadwalUtils.isCondition;

/**
 * Created by iksandecade on 23/02/17.
 */

public class CalendarPagerAdapter extends PagerAdapter {

    private static String SHALAT_SUBUH = "Subuh";
    private static String SHALAT_DZUHUR = "Dzuhur";
    private static String SHALAT_ASHAR = "Ashar";
    private static String SHALAT_MAGHRIB = "Maghrib";
    private static String SHALAT_ISYA = "Isya";
    private Context context;
    private List<Jadwal> jadwalList;

    public CalendarPagerAdapter(Context context, List<Jadwal> jadwalList) {
        this.context = context;
        this.jadwalList = jadwalList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.item_calendar, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvCalendar);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context.getApplicationContext());
        List<WaktuModel> waktuModels = new ArrayList<>();


        if (position == 0) {
            long subuh = jadwalList.get(0).getSubuh();
            long dzuhur = jadwalList.get(0).getDzuhur();
            long ashar = jadwalList.get(0).getAshar();
            long magrib = jadwalList.get(0).getMagrib();
            long isya = jadwalList.get(0).getIsya();
            long lastSecond = 0;

            SPJadwalSholat.setTodaySubuh(context, subuh);
            SPJadwalSholat.setTodayDzuhur(context, dzuhur);
            SPJadwalSholat.setTodayAshar(context, ashar);
            SPJadwalSholat.setTodayMaghrib(context, magrib);
            SPJadwalSholat.setTodayIsya(context, isya);

            String[] message = {"", ""};

            if (isCondition(subuh)) {

                WaktuModel waktuModel1 = new WaktuModel();
                waktuModel1.setJadwal(SHALAT_DZUHUR);
                waktuModel1.setWaktu(dzuhur);
                waktuModels.add(0, waktuModel1);

                WaktuModel waktuModel2 = new WaktuModel();
                waktuModel2.setJadwal(SHALAT_ASHAR);
                waktuModel2.setWaktu(ashar);
                waktuModels.add(1, waktuModel2);

                WaktuModel waktuModel3 = new WaktuModel();
                waktuModel3.setJadwal(SHALAT_MAGHRIB);
                waktuModel3.setWaktu(magrib);
                waktuModels.add(2, waktuModel3);

                WaktuModel waktuModel4 = new WaktuModel();
                waktuModel4.setJadwal(SHALAT_ISYA);
                waktuModel4.setWaktu(isya);
                waktuModels.add(3, waktuModel4);

                message[0] = getSisa(subuh) + " to";
                message[1] = SHALAT_SUBUH;
                lastSecond = subuh;
            } else if (isCondition(dzuhur)) {

                WaktuModel waktuModel2 = new WaktuModel();
                waktuModel2.setJadwal(SHALAT_ASHAR);
                waktuModel2.setWaktu(ashar);
                waktuModels.add(0, waktuModel2);

                WaktuModel waktuModel3 = new WaktuModel();
                waktuModel3.setJadwal(SHALAT_MAGHRIB);
                waktuModel3.setWaktu(magrib);
                waktuModels.add(1, waktuModel3);

                WaktuModel waktuModel4 = new WaktuModel();
                waktuModel4.setJadwal(SHALAT_ISYA);
                waktuModel4.setWaktu(isya);
                waktuModels.add(2, waktuModel4);

                message[0] = getSisa(dzuhur) + " to";
                message[1] = SHALAT_DZUHUR;
                lastSecond = dzuhur;
            } else if (isCondition(ashar)) {

                WaktuModel waktuModel3 = new WaktuModel();
                waktuModel3.setJadwal(SHALAT_MAGHRIB);
                waktuModel3.setWaktu(magrib);
                waktuModels.add(0, waktuModel3);

                WaktuModel waktuModel4 = new WaktuModel();
                waktuModel4.setJadwal(SHALAT_ISYA);
                waktuModel4.setWaktu(isya);
                waktuModels.add(1, waktuModel4);

                message[0] = getSisa(ashar) + " to";
                message[1] = SHALAT_ASHAR;
                lastSecond = ashar;
            } else if (isCondition(magrib)) {

                WaktuModel waktuModel4 = new WaktuModel();
                waktuModel4.setJadwal(SHALAT_ISYA);
                waktuModel4.setWaktu(isya);
                waktuModels.add(0, waktuModel4);

                message[0] = getSisa(magrib) + " to";
                message[1] = SHALAT_MAGHRIB;
                lastSecond = magrib;
            } else if (isCondition(isya)) {

                message[0] = getSisa(isya) + " to";
                message[1] = SHALAT_ISYA;
                lastSecond = isya;
            }
            SPJadwalSholat.setLastSecond(context, lastSecond);
            Events.ActivityActivityMessage activityActivityMessage = new Events.ActivityActivityMessage(message[0], message[1]);
            GlobalBus.getEventBus().postSticky(activityActivityMessage);

        } else {
            WaktuModel waktuModel = new WaktuModel();
            waktuModel.setJadwal("Subuh");
            waktuModel.setWaktu(jadwalList.get(position).getSubuh());
            waktuModels.add(0, waktuModel);

            WaktuModel waktuModel1 = new WaktuModel();
            waktuModel1.setJadwal("Dzuhur");
            waktuModel1.setWaktu(jadwalList.get(position).getDzuhur());
            waktuModels.add(1, waktuModel1);

            WaktuModel waktuModel2 = new WaktuModel();
            waktuModel2.setJadwal("Ashar");
            waktuModel2.setWaktu(jadwalList.get(position).getAshar());
            waktuModels.add(2, waktuModel2);

            WaktuModel waktuModel3 = new WaktuModel();
            waktuModel3.setJadwal("Maghrib");
            waktuModel3.setWaktu(jadwalList.get(position).getMagrib());
            waktuModels.add(3, waktuModel3);

            WaktuModel waktuModel4 = new WaktuModel();
            waktuModel4.setJadwal("Isya");
            waktuModel4.setWaktu(jadwalList.get(position).getIsya());
            waktuModels.add(4, waktuModel4);
        }
        MainRecyclerAdapter mainRecyclerAdapter = new MainRecyclerAdapter(waktuModels);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mainRecyclerAdapter);

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return jadwalList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }


}
