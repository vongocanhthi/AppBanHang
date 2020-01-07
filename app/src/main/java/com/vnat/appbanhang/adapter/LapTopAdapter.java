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

    public class ViewHolder{
        TextView tvTen, tvMoTa, tvGia;
        ImageView imgHinhanh;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.dong_laptop,null);
            viewHolder.tvTen = view.findViewById(R.id.tv_laptop);
            viewHolder.tvGia=view.findViewById(R.id.tv_gialaptop);
            viewHolder.tvMoTa=view.findViewById(R.id.tv_motalaptop);
            viewHolder.imgHinhanh = view.findViewById(R.id.img_laptop);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        SanPham sanPham = arrayLapTop.get(i);
        viewHolder.tvTen.setText(sanPham.getTensanpham());
        viewHolder.tvGia.setText("Giá "+decimalFormat.format(sanPham.getGiasanpham())+" Đ");
        viewHolder.tvMoTa.setText(sanPham.getMotasanpham());
        Picasso.with(context).load(sanPham.getHinhanhsanpham()).centerCrop().resize(150,150).into(viewHolder.imgHinhanh);
        return view;
    }

    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
