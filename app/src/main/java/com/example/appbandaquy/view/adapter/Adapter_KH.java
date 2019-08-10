package com.example.appbandaquy.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appbandaquy.R;
import com.example.appbandaquy.databinding.ItemKhBinding;
import com.example.appbandaquy.model.admin.KhachHang;

import java.util.List;

public class Adapter_KH extends RecyclerView.Adapter<Adapter_KH.Viewhodel> {
    List<KhachHang> khachHangs;
    private Context context;
    ItemKhBinding  binding;

    public Adapter_KH(List<KhachHang> khachHangs, Context context) {
        this.khachHangs = khachHangs;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewhodel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_kh,viewGroup,false);
        return new Viewhodel(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhodel viewhodel, int i) {
        KhachHang khachHang = khachHangs.get(i);
        viewhodel.binding.textviewTenKH.setText(khachHang.getTenkhachhang());
        viewhodel.binding.textviewEmail.setText(khachHang.getEmail());
        viewhodel.binding.textviewSDT.setText(khachHang.getSodienthoai()+"");

    }

    @Override
    public int getItemCount() {
        return khachHangs.size();
    }

    public static class Viewhodel extends RecyclerView.ViewHolder{
    ItemKhBinding binding;
        public Viewhodel(@NonNull ItemKhBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
