package com.example.iksandecade.jadwalsholat.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by iksandecade on 28/02/17.
 */
@Entity
public class Kota {
    @Id
    private String idKota;

    @NotNull
    private String namaKota;

    @Generated(hash = 27990658)
    public Kota(String idKota, @NotNull String namaKota) {
        this.idKota = idKota;
        this.namaKota = namaKota;
    }

    @Generated(hash = 1555707845)
    public Kota() {
    }

    public String getIdKota() {
        return this.idKota;
    }

    public void setIdKota(String idKota) {
        this.idKota = idKota;
    }

    public String getNamaKota() {
        return this.namaKota;
    }

    public void setNamaKota(String namaKota) {
        this.namaKota = namaKota;
    }



}
