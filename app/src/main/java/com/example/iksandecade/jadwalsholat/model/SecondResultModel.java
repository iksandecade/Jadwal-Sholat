package com.example.iksandecade.jadwalsholat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by iksandecade on 22/02/17.
 */

public class SecondResultModel {
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("time")
    @Expose
    private ResultModel time;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public ResultModel getTime() {
        return time;
    }

    public void setTime(ResultModel time) {
        this.time = time;
    }
}
