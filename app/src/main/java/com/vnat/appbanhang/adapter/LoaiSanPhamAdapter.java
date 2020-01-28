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
import com.vnat.appbanhang.model.LoaiSanPham;

import java.util.ArrayList;

/**
 * Created by Admin on 4/14/2018.
 */

public class LoaiSanPhamAdapter extends BaseAdapter {
    Context context;
    ArrayList<LoaiSanPham> arrLoaiSp;

    public LoaiSanPhamAdapter(Context context, ArrayList<LoaiSanPham> arrLoaiSp) {
        this.context = context;
        this.arrLoaiSp = arrLoaiSp;
    }

    @Override
    public int getCount() {
        return arrLoaiSp.size();
    }

    @Override
    public Object getItem(int i) {
        return arrLoaiSp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(R.layout.dong_listview_loaisp,null);
        TextView tvTenloaisp=view.findViewById(R.id.tv_loaisp);
        ImageView imgloaisp=view.findViewById(R.id.img_loaisp);
        tvTenloaisp.setText(arrLoaiSp.get(i).getTenloaisp());
        Picasso.with(context).load(arrLoaiSp.get(i).getHinhanhloaisp()).centerCrop().resize(150,150).into(imgloaisp);

        return view;
    }

    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
