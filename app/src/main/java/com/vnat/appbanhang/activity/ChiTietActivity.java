package com.vnat.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vnat.appbanhang.R;
import com.vnat.appbanhang.adapter.GioHangAdapter;
import com.vnat.appbanhang.model.GioHang;
import com.vnat.appbanhang.model.SanPham;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietActivity extends AppCompatActivity {
    GioHangAdapter gioHangAdapter;
    Toolbar toolbarChiTiet;
    ImageView imgChiTiet;
    TextView tvTen,tvGia,tvMoTa;
    Spinner spinner;
    Button btnDatMua;
    int id=0, giachitiet=0, idsanpham=0;
    String tenchitiet="", motachitiet="", hinhanhchitiet="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);

        anhXa();
        eventactionBar();
        getInformation();
        eventSpinner();
        addEvent();
    }

    private void addEvent() {
        btnDatMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.mangGioHang.size()>0){
                    int sl= Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists=false;
                    for(int i=0;i<MainActivity.mangGioHang.size();i++){
                        if(MainActivity.mangGioHang.get(i).getIdsp()==id){
                            MainActivity.mangGioHang.get(i).setSoluongsp(MainActivity.mangGioHang.get(i).getSoluongsp()+sl);
                            if(MainActivity.mangGioHang.get(i).getSoluongsp()>=10){
                                MainActivity.mangGioHang.get(i).setSoluongsp(10);
                            }
                            MainActivity.mangGioHang.get(i).setGiasp(giachitiet*sl+MainActivity.mangGioHang.get(i).getGiasp());
                            exists=true;
                        }
                    }
                    if(exists==false){
                        int soluong= Integer.parseInt(spinner.getSelectedItem().toString());
                        long giamoi=soluong*giachitiet;
                        MainActivity.mangGioHang.add(new GioHang(id,tenchitiet,giamoi,hinhanhchitiet,soluong));
                        gioHangAdapter.notifyDataSetChanged();
                    }
                }else{
                    int soluong= Integer.parseInt(spinner.getSelectedItem().toString());
                    long giamoi=soluong*giachitiet;
                    MainActivity.mangGioHang.add(new GioHang(id,tenchitiet,giamoi,hinhanhchitiet,soluong));
                    gioHangAdapter.notifyDataSetChanged();
                }
                Intent intent=new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void eventSpinner() {
        ArrayList<Integer> arr = new ArrayList<>();
        for(int i=1;i<=10;i++){
            arr.add(i);
        }
        ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void getInformation() {
        SanPham sanPham= (SanPham) getIntent().getSerializableExtra("chitietsanpham");
        id=sanPham.getId();
        tenchitiet=sanPham.getTensanpham();
        giachitiet=sanPham.getGiasanpham();
        motachitiet=sanPham.getMotasanpham();
        hinhanhchitiet=sanPham.getHinhanhsanpham();
        idsanpham=sanPham.getIdsanpham();

        tvTen.setText(tenchitiet);
        tvMoTa.setText(motachitiet);
        DecimalFormat deci=new DecimalFormat("###,###,###");
        tvGia.setText("Giá "+deci.format(giachitiet)+" Đ");
        idsanpham=sanPham.getIdsanpham();
        Picasso.with(getApplicationContext()).load(hinhanhchitiet)
                .error(R.drawable.error)
                .into(imgChiTiet);
    }

    private void eventactionBar() {
        setSupportActionBar(toolbarChiTiet);
        getSupportActionBar().setTitle("Chi Tiết Sản Phẩm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void anhXa() {
        toolbarChiTiet= findViewById(R.id.toolbar_chitietsanpham);
        imgChiTiet= findViewById(R.id.img_chitietsanpham);
        tvTen= findViewById(R.id.tv_tenchitietsanpham);
        tvGia = findViewById(R.id.tv_giachitietsanpham);
        tvMoTa= findViewById(R.id.tv_motachitietsanpham);
        spinner= findViewById(R.id.spinner);
        btnDatMua= findViewById(R.id.btn_datmua);
        gioHangAdapter=new GioHangAdapter(ChiTietActivity.this,MainActivity.mangGioHang);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_giohang){
            Intent intent=new Intent(ChiTietActivity.this,GioHangActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
