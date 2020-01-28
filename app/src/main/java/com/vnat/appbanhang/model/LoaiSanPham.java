package com.vnat.appbanhang.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoaiSanPham {

    @SerializedName("idloaisp")
    @Expose
    private Integer idloaisp;
    @SerializedName("tenloaisp")
    @Expose
    private String tenloaisp;
    @SerializedName("hinhanhloaisp")
    @Expose
    private String hinhanhloaisp;

    public LoaiSanPham(Integer idloaisp, String tenloaisp, String hinhanhloaisp) {
        this.idloaisp = idloaisp;
        this.tenloaisp = tenloaisp;
        this.hinhanhloaisp = hinhanhloaisp;
    }

    public Integer getIdloaisp() {
        return idloaisp;
    }

    public void setIdloaisp(Integer idloaisp) {
        this.idloaisp = idloaisp;
    }

    public String getTenloaisp() {
        return tenloaisp;
    }

    public void setTenloaisp(String tenloaisp) {
        this.tenloaisp = tenloaisp;
    }

    public String getHinhanhloaisp() {
        return hinhanhloaisp;
    }

    public void setHinhanhloaisp(String hinhanhloaisp) {
        this.hinhanhloaisp = hinhanhloaisp;
    }

}