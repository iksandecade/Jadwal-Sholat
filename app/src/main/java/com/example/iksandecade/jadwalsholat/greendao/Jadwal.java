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
    private String id;

    @NotNull
    private Long subuh;
    private Long dzuhur;
    private Long ashar;
    private Long magrib;
    private Long isya;
    private Long createdAt;
    private Long updatedAt;
    private String region;
    private Long filter;
    
    @Generated(hash = 2095419015)
    public Jadwal(String id, @NotNull Long subuh, Long dzuhur, Long ashar,
            Long magrib, Long isya, Long createdAt, Long updatedAt, String region,
            Long filter) {
        this.id = id;
        this.subuh = subuh;
        this.dzuhur = dzuhur;
        this.ashar = ashar;
        this.magrib = magrib;
        this.isya = isya;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.region = region;
        this.filter = filter;
    }
    @Generated(hash = 2080059390)
    public Jadwal() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
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
    public String getRegion() {
        return this.region;
    }
    public void setRegion(String region) {
        this.region = region;
    }
    public Long getFilter() {
        return this.filter;
    }
    public void setFilter(Long filter) {
        this.filter = filter;
    }
    
  
}
