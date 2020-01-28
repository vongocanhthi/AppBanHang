package com.vnat.appbanhang.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.squareup.picasso.Picasso;
import com.vnat.appbanhang.R;
import com.vnat.appbanhang.activity.GioHangActivity;
import com.vnat.appbanhang.activity.MainActivity;
import com.vnat.appbanhang.model.GioHang;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> arryGioHang;

    public GioHangAdapter(Context context, ArrayList<GioHang> arryGioHang) {
        this.context = context;
        this.arryGioHang = arryGioHang;
    }

    @Override
    public int getCount() {
        return arryGioHang.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(R.layout.dong_giohang,null);
        final TextView tvtengiohang,tvgiagiohang;
        ImageView imggiohang;
        final Button btnminus,btnvalues,btnplus;
        tvtengiohang=view.findViewById(R.id.tv_tengiohang);
        tvgiagiohang=view.findViewById(R.id.tv_giagiohang);
        imggiohang=view.findViewById(R.id.img_giohang);
        btnminus=view.findViewById(R.id.btn_minus);
        btnplus=view.findViewById(R.id.btn_plus);
        btnvalues=view.findViewById(R.id.btn_values);

        tvtengiohang.setText(arryGioHang.get(i).getTensp());
        final DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        tvgiagiohang.setText(decimalFormat.format(arryGioHang.get(i).getGiasp())+" Đ");
        Picasso.with(context).load(arryGioHang.get(i).getHinhsp()).centerCrop().resize(150,150).into(imggiohang);
        btnvalues.setText(arryGioHang.get(i).getSoluongsp()+"");
        final int sl= Integer.parseInt(btnvalues.getText().toString());
        final int slmoi=sl;
        if(sl<1){
            btnminus.setVisibility(View.INVISIBLE);
            btnplus.setVisibility(View.INVISIBLE);
        }
        else if(sl==1){
            btnminus.setVisibility(View.INVISIBLE);
            btnplus.setVisibility(View.VISIBLE);
        }
        else if(sl>=2&&sl<10){
            btnminus.setVisibility(View.VISIBLE);
            btnplus.setVisibility(View.VISIBLE);
        }
        else{
            btnminus.setVisibility(View.VISIBLE);
            btnplus.setVisibility(View.INVISIBLE);
        }
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("test11","chan doi");
                final AlertDialog.Builder buidlder=new AlertDialog.Builder(context);
                buidlder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này không ?");
                buidlder.setIcon(android.R.drawable.ic_delete);
                buidlder.setTitle("Xác nhận xóa");
                buidlder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                buidlder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        MainActivity.gioHangArrayList.remove(i);
                        GioHangActivity.gioHangAdapter.notifyDataSetChanged();
                        if(MainActivity.gioHangArrayList.size()==0){
                            GioHangActivity.tvThongbao.setVisibility(View.VISIBLE);
                        }
                        long tong=0;
                        for(int i=0;i<MainActivity.gioHangArrayList.size();i++){
                            tong+=MainActivity.gioHangArrayList.get(i).getGiasp();
                        }
                        GioHangActivity.tvTongtien.setText(tong+"");
                    }
                });
                AlertDialog alertDialog=buidlder.create();
                alertDialog.show();
                return false;
            }
        });
        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sl1= Integer.parseInt(btnvalues.getText().toString());
                int slmoi= Integer.parseInt(btnvalues.getText().toString());
                slmoi+=1;
                btnvalues.setText(slmoi+"");
                MainActivity.gioHangArrayList.get(i).setSoluongsp(slmoi);
                MainActivity.gioHangArrayList.get(i).setGiasp(MainActivity.gioHangArrayList.get(i).getGiasp()*slmoi/(sl1));
                tvgiagiohang.setText(MainActivity.gioHangArrayList.get(i).getGiasp()+"");
                long tongtien=0;
                for(int i=0;i<MainActivity.gioHangArrayList.size();i++){
                    tongtien+=MainActivity.gioHangArrayList.get(i).getGiasp();
                }
                DecimalFormat decimalFormat1=new DecimalFormat("###,###,###");
                GioHangActivity.tvTongtien.setText(decimalFormat1.format(tongtien)+" Đ");
                tvgiagiohang.setText(decimalFormat1.format(tongtien)+" Đ");
                if(slmoi>9){
                    btnplus.setVisibility(View.INVISIBLE);
                    btnminus.setVisibility(View.VISIBLE);
                    btnvalues.setText(slmoi+"");
                }
                else{
                    btnplus.setVisibility(View.VISIBLE);
                    btnminus.setVisibility(View.VISIBLE);
                    btnvalues.setText(slmoi+"");
                }
            }
        });
        btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sl1= Integer.parseInt(btnvalues.getText().toString());
                int slmoi= Integer.parseInt(btnvalues.getText().toString());
                slmoi-=1;
                btnvalues.setText(slmoi+"");
                MainActivity.gioHangArrayList.get(i).setSoluongsp(slmoi);
                MainActivity.gioHangArrayList.get(i).setGiasp(MainActivity.gioHangArrayList.get(i).getGiasp()*slmoi/(sl1));
                tvgiagiohang.setText(MainActivity.gioHangArrayList.get(i).getGiasp()+"");
                long tongtien=0;
                for(int i=0;i<MainActivity.gioHangArrayList.size();i++){
                    tongtien+=MainActivity.gioHangArrayList.get(i).getGiasp();
                }
                DecimalFormat decimalFormat1=new DecimalFormat("###,###,###");
                GioHangActivity.tvTongtien.setText(decimalFormat1.format(tongtien)+" Đ");
                tvgiagiohang.setText(decimalFormat1.format(tongtien)+" Đ");
                if(slmoi<2){
                    btnplus.setVisibility(View.VISIBLE);
                    btnminus.setVisibility(View.INVISIBLE);
                    btnvalues.setText(slmoi+"");
                }
                else{
                    btnplus.setVisibility(View.VISIBLE);
                    btnminus.setVisibility(View.VISIBLE);
                    btnvalues.setText(slmoi+"");
                }
            }
        });

        return view;
    }

    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
