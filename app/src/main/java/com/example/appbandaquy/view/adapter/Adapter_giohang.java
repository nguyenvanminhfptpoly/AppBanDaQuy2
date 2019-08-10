package com.example.appbandaquy.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbandaquy.R;
import com.example.appbandaquy.model.home.Giohang;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_giohang extends BaseAdapter {
    public List<Giohang> gioHangs;
    Context context;

    public Adapter_giohang(List<Giohang> gioHangs, Context context) {
        this.gioHangs = gioHangs;
        this.context = context;
    }

    @Override
    public int getCount() {
        return gioHangs.size();
    }

    @Override
    public Object getItem(int position) {
        return gioHangs.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public static class Viewhodel{
        public ImageView img_giohang;
        public TextView tvTen,tvGia,tvSoluong;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewhodel viewhodel;
        if(convertView == null){
            viewhodel = new Viewhodel();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_giohang,null);
            viewhodel.img_giohang = (ImageView) convertView.findViewById(R.id.imgGH);
            viewhodel.tvTen = (TextView) convertView.findViewById(R.id.tvTenGiohang);
            viewhodel.tvGia = (TextView) convertView.findViewById(R.id.tvGiaGH);
            viewhodel.tvSoluong = (TextView) convertView.findViewById(R.id.tvSoluongGH2);
            convertView.setTag(viewhodel);
        }else {
            viewhodel = (Viewhodel) convertView.getTag();
        }
        Giohang giohang = gioHangs.get(position);
        viewhodel.tvGia.setText(giohang.getGiasp()+"");
        viewhodel.tvTen.setText(giohang.getTensp());
        viewhodel.tvSoluong.setText(giohang.getSoluongsp()+"");
        Picasso.with(context).load(giohang.getHinhanhsp()).into(viewhodel.img_giohang);
        Log.d("image", viewhodel.img_giohang.toString());
        return  convertView;
    }
}
