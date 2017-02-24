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
        holder.tvRemaining.setText(getSisa(waktu));
    }

    @Override
    public int getItemCount() {
        return waktuModels.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView tvJadwal;
        TextView tvWaktu;
        TextView tvRemaining;

        public Holder(View itemView) {
            super(itemView);
            tvJadwal = (TextView) itemView.findViewById(R.id.tvJadwal);
            tvWaktu = (TextView) itemView.findViewById(R.id.tvWaktu);
            tvRemaining = (TextView) itemView.findViewById(R.id.tvRemaining);
        }
    }

    private String getWaktu(long data) {

        Date date = new Date(data);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk:mm");
        return simpleDateFormat.format(date);
    }

    private String getSisa(long data) {
        String result = "";
        long milliseconds1 = System.currentTimeMillis();
        long milliseconds2 = data;

        long diff = milliseconds2 - milliseconds1;
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if (diffDays != 0 && diffDays > 0) {
            result = diffDays + " Hari";
        } else if (diffHours != 0 && diffHours > 0) {
            result = diffHours + " Jam";
        } else if (diffMinutes > 0) {
            result = diffMinutes + " Menit";
        } else{
//            result
        }

        return result;
    }
}
