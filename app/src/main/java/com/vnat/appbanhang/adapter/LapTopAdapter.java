package com.vnat.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vnat.appbanhang.R;
import com.vnat.appbanhang.model.SanPham;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Admin on 4/15/2018.
 */

public class LapTopAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrayLapTop;

    public LapTopAdapter(Context context, ArrayList<SanPham> arrayLaptop) {
        this.context = context;
        this.arrayLapTop = arrayLaptop;
    }

    @Override
    public int getCount() {
        return arrayLapTop.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayLapTop.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(R.layout.dong_laptop,null);
        TextView tvTen=view.findViewById(R.id.tv_laptop);
        TextView tvMoTa=view.findViewById(R.id.tv_motalaptop);
        TextView tvGia=view.findViewById(R.id.tv_gialaptop);
        ImageView imgHinhanh=view.findViewById(R.id.img_laptop);
        tvTen.setText(arrayLapTop.get(i).getTensanpham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        tvGia.setText("Giá "+decimalFormat.format(arrayLapTop.get(i).getGiasanpham())+" Đ");
        tvMoTa.setMaxLines(2);
        tvMoTa.setText(arrayLapTop.get(i).getMotasanpham());
        Picasso.with(context).load(arrayLapTop.get(i).getHinhanhsanpham()).centerCrop().resize(150,150).into(imgHinhanh);
        return view;
    }

    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
