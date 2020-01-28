package com.vnat.appbanhang.Service;

import com.vnat.appbanhang.model.LoaiSanPham;
import com.vnat.appbanhang.model.SanPham;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {
    @GET("getspmoinhat.php")
    Call<List<SanPham>> getSanPhamMoiNhat();

    @GET("getloaisp.php")
    Call<List<LoaiSanPham>> getLoaiSanPham();

    @GET("getdienthoaitheogiathap.php")
    Call<List<SanPham>> getDienThoaiGiaThap();

    @GET("getdienthoaitheogiacao.php")
    Call<List<SanPham>> getDienThoaiGiaCao();

    @GET("getlaptoptheogiathap.php")
    Call<List<SanPham>> getLapTopGiaThap();

    @GET("getlaptoptheogiacao.php")
    Call<List<SanPham>> getLapTopGiaCao();

    @FormUrlEncoded
    @POST("getsanphamtheohang.php")
    Call<List<SanPham>> getSanPhamTheoHang(@Field("idhang") Integer idHang);

}
