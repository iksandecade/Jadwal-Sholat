package com.example.iksandecade.jadwalsholat.model;

/**
 * Created by iksandecade on 21/02/17.
 */

public class WaktuModel {

    private String jadwal;
    private Long waktu;
    private boolean separator;

    public WaktuModel() {

    }

    public String getJadwal() {
        return jadwal;
    }

    public void setJadwal(String jadwal) {
        this.jadwal = jadwal;
    }

    public Long getWaktu() {
        return waktu;
    }

    public void setWaktu(Long waktu) {
        this.waktu = waktu;
    }

    public boolean getSeparator() {
        return separator;
    }

    public void setSeparator(boolean separator) {
        this.separator = separator;
    }
}
