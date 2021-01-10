package com.example.saveme.Model.Auth;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DataLaporan {
    @SerializedName("id")
    private Integer id;
    @SerializedName("pelapor")
    private String pelapor;
    @SerializedName("rumahsakit")
    private String rumahsakit;
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
    @SerializedName("action")
    private Boolean action;
    @SerializedName("updatedAt")
    private Date updatedAt;
    @SerializedName("createdAt")
    private Date createdAt;

    public Boolean getAction() {
        return action;
    }

    public void setAction(Boolean action) {
        this.action = action;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRumahsakit() {
        return rumahsakit;
    }

    public void setRumahsakit(String rumahsakit) {
        this.rumahsakit = rumahsakit;
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