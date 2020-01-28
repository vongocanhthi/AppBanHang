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

    public class ViewHolder{
        TextView tvtendienthoai, tvGiadienthoai, tvMota;
        ImageView imgdienthoai;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.dong_dienthoai,null);
            viewHolder.tvtendienthoai = view.findViewById(R.id.tv_dienthoai);
            viewHolder.tvGiadienthoai=view.findViewById(R.id.tv_giadienthoai);
            viewHolder.tvMota=view.findViewById(R.id.tv_motadienthoai);
            viewHolder.imgdienthoai = view.findViewById(R.id.img_dienthoai);

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        SanPham sanPham = arrayDienThoai.get(i);
        viewHolder.tvtendienthoai.setText(sanPham.getTensp());
        viewHolder.tvGiadienthoai.setText("Giá :"+decimalFormat.format(sanPham.getGiasp())+" Đ");
        viewHolder.tvMota.setText(sanPham.getMotasp());
        Picasso.with(context).load(sanPham.getHinhanhsp())
                .centerCrop().resize(150,150)
                .placeholder(R.drawable.ic_no_image)
                .error(R.drawable.ic_error)
                .into(viewHolder.imgdienthoai);
        return view;
    }

    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
