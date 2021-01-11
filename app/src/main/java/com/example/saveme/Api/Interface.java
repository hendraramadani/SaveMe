package com.example.saveme.Api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import com.example.saveme.Model.Auth.DataLaporan;
import com.example.saveme.Model.Auth.Login;
import com.example.saveme.Model.Auth.Register;
import com.example.saveme.Model.Auth.RumahSakitData;

import java.util.List;

public interface Interface {
    @FormUrlEncoded
    @POST("user/login")
    Call<Login> postLogin(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/register")
    Call<Register> postRegister(@Field("name") String nama, @Field("email") String email,
                                @Field("address") String alamat, @Field("phone") String NoHP,
                                @Field("password") String password);

    @FormUrlEncoded
    @POST("laporan")
    Call<DataLaporan> postLaporan(@Field("pelapor") String pelapor, @Field("kejadian") String kejadian,
                                  @Field("deskripsi") String deskripsi, @Field("longitude") Float longitude,
                                  @Field("latitude") Float latitude, @Field("foto") String foto);

    @GET("laporan")
    Call<List<DataLaporan>> getNearbyAmbulance();
}
