package com.vnat.appbanhang.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vnat.appbanhang.R;
import com.vnat.appbanhang.adapter.GioHangAdapter;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
    ListView lvGiohang;
    public static TextView tvThongbao,tvTongtien;
    Button btnThanhToan,btnTieptucmua;
    Toolbar toolbarGiohang;
    public static GioHangAdapter gioHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        anhXa();
        actionToolbar();
        checkData();
        eventUtil();
        eventButton();
    }

    private void eventButton() {
        btnTieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GioHangActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.gioHangArrayList.size()>0){
                    Intent intent=new Intent(GioHangActivity.this,KhachHangActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(GioHangActivity.this, "Bạn chưa có giỏ hàng nào cả", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void eventUtil() {
        long tongtien=0;
        for(int i=0;i<MainActivity.gioHangArrayList.size();i++){
            tongtien+=MainActivity.gioHangArrayList.get(i).getGiasp();
        }
        DecimalFormat decimalFomat=new DecimalFormat("###,###,###");
        tvTongtien.setText(decimalFomat.format(tongtien)+" Đ");
    }

    private void checkData() {
        if(MainActivity.gioHangArrayList.size()<=0){
            gioHangAdapter.notifyDataSetChanged();
            tvThongbao.setVisibility(View.VISIBLE);
            lvGiohang.setVisibility(View.INVISIBLE);
        }
        else{
            gioHangAdapter.notifyDataSetChanged();
            tvThongbao.setVisibility(View.INVISIBLE);
            lvGiohang.setVisibility(View.VISIBLE);
        }
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarGiohang);
        getSupportActionBar().setTitle("Giỏ Hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void anhXa() {
        lvGiohang= findViewById(R.id.lv_giohang);
        tvThongbao= findViewById(R.id.tv_thongbao);
        tvTongtien= findViewById(R.id.tv_tongtien);
        btnThanhToan= findViewById(R.id.btn_thanhtoangiohang);
        btnTieptucmua= findViewById(R.id.btn_tieptucmuahang);
        toolbarGiohang= findViewById(R.id.toolbar_giohang);
        gioHangAdapter=new GioHangAdapter(GioHangActivity.this,MainActivity.gioHangArrayList);
        lvGiohang.setAdapter(gioHangAdapter);
    }
}
