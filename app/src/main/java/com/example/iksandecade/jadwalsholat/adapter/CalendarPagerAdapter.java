package com.example.iksandecade.jadwalsholat.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.iksandecade.jadwalsholat.R;
import com.example.iksandecade.jadwalsholat.greendao.Jadwal;
import com.example.iksandecade.jadwalsholat.model.WaktuModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iksandecade on 23/02/17.
 */

public class CalendarPagerAdapter extends PagerAdapter {

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
