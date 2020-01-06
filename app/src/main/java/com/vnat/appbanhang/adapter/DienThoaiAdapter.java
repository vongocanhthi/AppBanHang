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

public class DienThoaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrayDienThoai;

    public DienThoaiAdapter(Context context, ArrayList<SanPham> arrayDienThoai) {
        this.context = context;
        this.arrayDienThoai = arrayDienThoai;
    }

    @Override
    public int getCount() {
        return arrayDienThoai.size();
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(R.layout.dong_dienthoai,null);
        TextView tvtendienthoai=view.findViewById(R.id.tv_dienthoai);
        TextView tvGiadienthoai=view.findViewById(R.id.tv_giadienthoai);
        TextView tvMota=view.findViewById(R.id.tv_motadienthoai);
        ImageView imgdienthoai=view.findViewById(R.id.img_dienthoai);
        tvtendienthoai.setText(arrayDienThoai.get(i).getTensanpham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        tvGiadienthoai.setText("Giá :"+decimalFormat.format(arrayDienThoai.get(i).getGiasanpham())+" Đ");
        tvMota.setMaxLines(2);
        tvMota.setText(arrayDienThoai.get(i).getMotasanpham());
       Picasso.with(context).load(arrayDienThoai.get(i).getHinhanhsanpham()).centerCrop().resize(150,150).into(imgdienthoai);
        return view;
    }

    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
