package com.vnat.appbanhang.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SanPham implements Serializable {

@SerializedName("idsp")
@Expose
private Integer idsp;
@SerializedName("tensp")
@Expose
private String tensp;
@SerializedName("giasp")
@Expose
private Integer giasp;
@SerializedName("hinhanhsp")
@Expose
private String hinhanhsp;
@SerializedName("motasp")
@Expose
private String motasp;
@SerializedName("idloaisp")
@Expose
private Integer idloaisp;
@SerializedName("idhang")
@Expose
private Integer idhang;

    public SanPham(Integer idsp, String tensp, Integer giasp, String hinhanhsp, String motasp, Integer idloaisp, Integer idhang) {
        this.idsp = idsp;
        this.tensp = tensp;
        this.giasp = giasp;
        this.hinhanhsp = hinhanhsp;
        this.motasp = motasp;
        this.idloaisp = idloaisp;
        this.idhang = idhang;
    }

    public Integer getIdsp() {
        return idsp;
    }

    public void setIdsp(Integer idsp) {
        this.idsp = idsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public Integer getGiasp() {
        return giasp;
    }

    public void setGiasp(Integer giasp) {
        this.giasp = giasp;
    }

    public String getHinhanhsp() {
        return hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        this.hinhanhsp = hinhanhsp;
    }

    public String getMotasp() {
        return motasp;
    }

    public void setMotasp(String motasp) {
        this.motasp = motasp;
    }

    public Integer getIdloaisp() {
        return idloaisp;
    }

    public void setIdloaisp(Integer idloaisp) {
        this.idloaisp = idloaisp;
    }

    public Integer getIdhang() {
        return idhang;
    }

    public void setIdhang(Integer idhang) {
        this.idhang = idhang;
    }
}