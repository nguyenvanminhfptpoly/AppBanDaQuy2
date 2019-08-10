package com.example.appbandaquy.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbandaquy.R;
import com.example.appbandaquy.model.home.SanPham;
import com.example.appbandaquy.presenter.home.OnItemListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterSanPham extends RecyclerView.Adapter<AdapterSanPham.Viewhodel> {
    List<SanPham> sanPhams;
    public Context context;
    private OnItemListener onItemListener;
    public AdapterSanPham(List<SanPham> sanPhams, Context context,OnItemListener onItemListener) {
        this.sanPhams = sanPhams;
        this.context = context;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public Viewhodel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_rycsp,viewGroup,false);
        return new Viewhodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhodel viewhodel,final int i) {
        SanPham sanPham = sanPhams.get(i);
        viewhodel.tvTenSP.setText(sanPham.getTensp());
        viewhodel.tvGiasp.setText(sanPham.getGiasp()+"");
        viewhodel.tvDetailSP.setText(sanPham.getMotasp());
        Picasso.with(context).load(sanPham.getHinhanhsp()).into(viewhodel.imgSP);

        viewhodel.contr_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemListener.OnItemListener(i);
            }
        });

        viewhodel.contr_sp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemListener.OnLongClickItem(i);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return sanPhams.size();
    }

    public static class Viewhodel extends RecyclerView.ViewHolder {
    public ImageView imgSP;
    public TextView tvGiasp,tvTenSP,tvDetailSP;
    public ConstraintLayout contr_sp;

        public Viewhodel(@NonNull View itemView) {
            super(itemView);
            imgSP = itemView.findViewById(R.id.imgGH);
            tvTenSP = itemView.findViewById(R.id.tvTenSP);
            tvGiasp = itemView.findViewById(R.id.tvGiasp);
            tvDetailSP = itemView.findViewById(R.id.tvDetailSP);
            contr_sp = itemView.findViewById(R.id.contr_sp);
        }


    }

}
