package com.example.iksandecade.jadwalsholat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.iksandecade.jadwalsholat.greendao.DaoHandler;
import com.example.iksandecade.jadwalsholat.greendao.DaoSession;
import com.example.iksandecade.jadwalsholat.greendao.Kota;
import com.example.iksandecade.jadwalsholat.utils.SPJadwalSholat;

public class SplashActivity extends AppCompatActivity {

    DaoSession daoSession;
    String[] namaKota;
    String[] idKota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (SPJadwalSholat.getIsFirst(this)) {
            daoSession = DaoHandler.getInstance(this);

            namaKota = new String[]{
                    "Jakarta",
                    "Banda Aceh",
                    "Medan",
                    "Padang",
                    "Pekanbaru",
                    "Jambi",
                    "Palembang",
                    "Bengkulu",
                    "Bandar Lampung",
                    "Pangkal Pinang",
                    "Tanjung Pinang",
                    "Yogyakarta",
                    "Bandung",
                    "Semarang",
                    "Surabaya",
                    "Serang",
                    "Denpasar",
                    "Kupang",
                    "Mataram",
                    "Pontianak",
                    "Palangka Raya",
                    "Banjarmasin",
                    "Samarinda",
                    "Tanjung Selor",
                    "Manado",
                    "Palu",
                    "Makassar",
                    "Kendari",
                    "Mamuju",
                    "Gorontalo",
                    "Ambon",
                    "Sofifi",
                    "Jayapura",
                    "Manokwari"
            };

            idKota = new String[]{
                    "JKT",
                    "ACH",
                    "MDN",
                    "PDG",
                    "PKB",
                    "JMB",
                    "PLB",
                    "BKL",
                    "BL",
                    "PP",
                    "TP",
                    "YKT",
                    "BDG",
                    "SRG",
                    "SBY",
                    "SAG",
                    "DPR",
                    "KPG",
                    "MTM",
                    "PON",
                    "PR",
                    "BJN",
                    "SMR",
                    "TS",
                    "MND",
                    "PAL",
                    "MKS",
                    "KDI",
                    "MMU",
                    "GOR",
                    "MBO",
                    "SFF",
                    "JPR",
                    "MKI"
            };


            for (int i = 0; i < namaKota.length; i++) {
                Kota kota = new Kota();
                kota.setIdKota(idKota[i]);
                kota.setNamaKota(namaKota[i]);
                daoSession.getKotaDao().insert(kota);
            }

            Intent i = new Intent(this, SettingActivity.class);
            startActivity(i);

        } else {
            Intent i = new Intent(this, CalendarV2Activity.class);
            startActivity(i);
        }

    }
}
