package com.example.iksandecade.jadwalsholat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.iksandecade.jadwalsholat.R;
import com.example.iksandecade.jadwalsholat.model.WaktuModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by iksandecade on 21/02/17.
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.Holder> {
    List<WaktuModel> waktuModels;

    public MainRecyclerAdapter(List<WaktuModel> waktuModels) {
        this.waktuModels = waktuModels;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        String jadwal = waktuModels.get(position).getJadwal();
        long waktu = waktuModels.get(position).getWaktu();

        holder.tvJadwal.setText(jadwal);
        holder.tvWaktu.setText(getWaktu(waktu));
    }

    @Override
    public int getItemCount() {
        return waktuModels.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView tvJadwal;
        TextView tvWaktu;

        public Holder(View itemView) {
            super(itemView);
            tvJadwal = (TextView) itemView.findViewById(R.id.tvJadwal);
            tvWaktu = (TextView) itemView.findViewById(R.id.tvWaktu);
        }
    }

    private String getWaktu(long data) {

        Date date = new Date(data);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk:mm");
        return simpleDateFormat.format(date);
    }

}
