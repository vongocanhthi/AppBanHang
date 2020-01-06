package com.vnat.appbanhang.model;

import java.io.Serializable;

/**
 * Created by Admin on 4/14/2018.
 */

public class Loaisp implements Serializable {
    public int id;
    public String tenLoaiSanPham,hinhAnhLoaiSanPham;

    public Loaisp() {

    }

    public Loaisp(int id, String tenLoaiSanPham, String hinhAnhLoaiSanPham) {
        this.id = id;
        this.tenLoaiSanPham = tenLoaiSanPham;
        this.hinhAnhLoaiSanPham = hinhAnhLoaiSanPham;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoaiSanPham() {
        return tenLoaiSanPham;
    }

    public void setTenLoaiSanPham(String tenLoaiSanPham) {
        this.tenLoaiSanPham = tenLoaiSanPham;
    }

    public String getHinhAnhLoaiSanPham() {
        return hinhAnhLoaiSanPham;
    }

    public void setHinhAnhLoaiSanPham(String hinhAnhLoaiSanPham) {
        this.hinhAnhLoaiSanPham = hinhAnhLoaiSanPham;
    }
}
