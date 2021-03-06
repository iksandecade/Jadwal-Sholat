package com.example.iksandecade.jadwalsholat.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.iksandecade.jadwalsholat.R;
import com.example.iksandecade.jadwalsholat.model.WaktuModel;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by iksandecade on 21/02/17.
 */

public class MainRecyclerAdapter extends AdapterDelegate<List<WaktuModel>> {
    private LayoutInflater layoutInflater;

    MainRecyclerAdapter(Activity activity) {
        this.layoutInflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull List<WaktuModel> items, int position) {
        return !items.get(position).getSeparator();
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new MainRecycleHolder(layoutInflater.inflate(R.layout.item_main, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<WaktuModel> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        MainRecycleHolder mainRecycleHolder = (MainRecycleHolder) holder;
        String jadwal = items.get(position).getJadwal();
        long waktu = items.get(position).getWaktu();
        mainRecycleHolder.tvJadwal.setText(jadwal);
        mainRecycleHolder.tvWaktu.setText(getWaktu(waktu));
    }


    private String getWaktu(long data) {

        Date date = new Date(data);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk:mm");
        return simpleDateFormat.format(date);
    }

    private static class MainRecycleHolder extends RecyclerView.ViewHolder {
        TextView tvJadwal;
        TextView tvWaktu;

        MainRecycleHolder(View view) {
            super(view);
            tvJadwal = (TextView) itemView.findViewById(R.id.tvJadwal);
            tvWaktu = (TextView) itemView.findViewById(R.id.tvWaktu);
        }
    }


}
