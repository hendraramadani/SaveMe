package com.example.saveme.Api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import com.example.saveme.Model.Auth.DataLaporan;
import com.example.saveme.Model.Auth.Login;
import com.example.saveme.Model.Auth.Register;

public interface Interface {
    @FormUrlEncoded
    @POST("login")
    Call<Login> postLogin(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<Register> postRegister(@Field("name") String nama, @Field("email") String email, @Field("password") String password, @Field("phone") String NoHP, @Field("address") String alamat);

    @FormUrlEncoded
    @POST("laporan")
    Call<DataLaporan> postLaporan(@Field("pelapor") Integer pelapor, @Field("kejadian") String kejadian,
                                  @Field("deskripsi") String deskripsi, @Field("longitude") Float longitude,
                                  @Field("latitude") Float latitude, @Field("foto") String foto);
}
