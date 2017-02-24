package com.example.iksandecade.jadwalsholat.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by iksandecade on 20/02/17.
 */
@Entity
public class Jadwal {

    @Id
    private Long id;

    @NotNull
    private Long subuh;
    private Long dzuhur;
    private Long ashar;
    private Long magrib;
    private Long isya;
    private Long createdAt;
    private Long updatedAt;
    @Generated(hash = 272405874)
    public Jadwal(Long id, @NotNull Long subuh, Long dzuhur, Long ashar,
            Long magrib, Long isya, Long createdAt, Long updatedAt) {
        this.id = id;
        this.subuh = subuh;
        this.dzuhur = dzuhur;
        this.ashar = ashar;
        this.magrib = magrib;
        this.isya = isya;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    @Generated(hash = 2080059390)
    public Jadwal() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getSubuh() {
        return this.subuh;
    }
    public void setSubuh(Long subuh) {
        this.subuh = subuh;
    }
    public Long getDzuhur() {
        return this.dzuhur;
    }
    public void setDzuhur(Long dzuhur) {
        this.dzuhur = dzuhur;
    }
    public Long getAshar() {
        return this.ashar;
    }
    public void setAshar(Long ashar) {
        this.ashar = ashar;
    }
    public Long getMagrib() {
        return this.magrib;
    }
    public void setMagrib(Long magrib) {
        this.magrib = magrib;
    }
    public Long getIsya() {
        return this.isya;
    }
    public void setIsya(Long isya) {
        this.isya = isya;
    }
    public Long getCreatedAt() {
        return this.createdAt;
    }
    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
    public Long getUpdatedAt() {
        return this.updatedAt;
    }
    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

}
