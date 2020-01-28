package com.vnat.appbanhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vnat.appbanhang.R;
import com.vnat.appbanhang.activity.ChiTietActivity;
import com.vnat.appbanhang.model.SanPham;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.itemHolder>{
    Context context;
    ArrayList<SanPham> arrayListSanpham;

    public SanPhamAdapter(Context context, ArrayList<SanPham> arrayListSanpham) {
        this.context = context;
        this.arrayListSanpham = arrayListSanpham;
    }

    @Override
    public itemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat,null);
        itemHolder itemHolder=new itemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(itemHolder holder, int position) {
        SanPham sanPham = arrayListSanpham.get(position);
        holder.tvTensanpham.setText(sanPham.getTensp());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.tvGiasanpham.setText("Giá :"+decimalFormat.format(sanPham.getGiasp())+" Đ");
        Picasso.with(context).load(sanPham.getHinhanhsp()).centerCrop().resize(150,150).into(holder.imgHinhAnhSanpham);
    }

    @Override
    public int getItemCount() {
        return arrayListSanpham.size();
    }

    public class itemHolder extends RecyclerView.ViewHolder{
        public ImageView imgHinhAnhSanpham;
        public TextView tvTensanpham;
        public TextView tvGiasanpham;

        public itemHolder(View itemView) {
            super(itemView);
            imgHinhAnhSanpham=itemView.findViewById(R.id.img_sanpham);
            tvTensanpham=itemView.findViewById(R.id.tv_tensanpham);
            tvGiasanpham=itemView.findViewById(R.id.tv_giasanpham);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, ChiTietActivity.class);
                    intent.putExtra("chitietsanpham",arrayListSanpham.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
