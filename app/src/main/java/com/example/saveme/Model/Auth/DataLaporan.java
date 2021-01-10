package com.example.saveme.Model.Auth;

import com.google.gson.annotations.SerializedName;

public class DataLaporan {
    @SerializedName("pelapor")
    private String pelapor;
    @SerializedName("kejadian")
    private String kejadian;
    @SerializedName("deskripsi")
    private String deskripsi;
    @SerializedName("longitude")
    private Float longitude;
    @SerializedName("latitude")
    private Float latitude;
    @SerializedName("foto")
    private String foto;

    public DataLaporan(String pelapor, String kejadian, String deskripsi, Float longitude, Float latitude, String foto) {
        this.pelapor = pelapor;
        this.kejadian = kejadian;
        this.deskripsi = deskripsi;
        this.longitude = longitude;
        this.latitude = latitude;
        this.foto = foto;
    }

    public void setPelapor(String pelapor) {
        this.pelapor = pelapor;
    }

    public String getPelapor() {
        return pelapor;
    }
    public String getKejadian() {
        return kejadian;
    }

    public void setKejadian(String kejadian) {
        this.kejadian = kejadian;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}