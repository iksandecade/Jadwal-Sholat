package com.example.iksandecade.jadwalsholat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by iksandecade on 22/02/17.
 */

public class JadwalBulanModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private DataModel data;
    @SerializedName("result")
    @Expose
    private List<SecondResultModel> result = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataModel getData() {
        return data;
    }

    public void setData(DataModel data) {
        this.data = data;
    }

    public List<SecondResultModel> getResult() {
        return result;
    }

    public void setResult(List<SecondResultModel> result) {
        this.result = result;
    }

}
