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
 * Created by iksandecade on 03/03/17.
 */

public class SeparatorAdapter extends AdapterDelegate<List<WaktuModel>> {
    private LayoutInflater layoutInflater;

    SeparatorAdapter(Activity activity) {
        this.layoutInflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull List<WaktuModel> items, int position) {
        return items.get(position).getSeparator();
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new SeparatorHolder(layoutInflater.inflate(R.layout.item_separator, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<WaktuModel> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        SeparatorHolder separatorHolder = (SeparatorHolder) holder;
        separatorHolder.tvDays.setText(getWaktu(items.get(position).getWaktu()));
    }

    private static class SeparatorHolder extends RecyclerView.ViewHolder {
        TextView tvDays;
        public SeparatorHolder(View itemView) {
            super(itemView);
            tvDays = (TextView) itemView.findViewById(R.id.tvDays);
        }
    }

    private String getWaktu(long data) {

        Date date = new Date(data);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd");
        return simpleDateFormat.format(date);
    }
}
