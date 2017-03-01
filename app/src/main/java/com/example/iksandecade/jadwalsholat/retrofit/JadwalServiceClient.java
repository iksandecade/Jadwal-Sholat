package com.example.iksandecade.jadwalsholat.retrofit;

import com.example.iksandecade.jadwalsholat.model.JadwalBulanModel;
import com.example.iksandecade.jadwalsholat.model.JadwalModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by iksandecade on 17/02/17.
 */

public interface JadwalServiceClient {

    @GET("/api/pray-times")
    Observable<JadwalModel> callJadwal(
            @Query("address") String lokasi,
            @Query("timezone") String timezone,
            @Query("method") String method,
            @Query("year") String tahun,
            @Query("month") String bulan,
            @Query("day") String hari
    );

    @GET("/api/pray-times")
    Observable<JadwalBulanModel> callJadwalBulan(
            @Query("address") String lokasi,
            @Query("timezone") String timezone,
            @Query("method") String method,
            @Query("year") String tahun,
            @Query("month") String bulan
    );

    @GET("/api/pray-times")
    Observable<JadwalBulanModel> callJadwalTahun(
            @Query("address") String lokasi,
            @Query("timezone") String timezone,
            @Query("method") String method,
            @Query("year") String year
    );


}
