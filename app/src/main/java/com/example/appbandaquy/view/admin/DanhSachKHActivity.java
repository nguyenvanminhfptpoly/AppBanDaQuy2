package com.example.appbandaquy.view.admin;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.example.appbandaquy.R;
import com.example.appbandaquy.Service.APIUltil;
import com.example.appbandaquy.databinding.ActivityDanhSachKhBinding;
import com.example.appbandaquy.model.admin.KhachHang;
import com.example.appbandaquy.view.adapter.Adapter_KH;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachKHActivity extends AppCompatActivity {
    ActivityDanhSachKhBinding binding;
    public List<KhachHang> khachHangs;
    private Adapter_KH adapter_kh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = DataBindingUtil.setContentView(this, R.layout.activity_danh_sach_kh);

        binding.toolbar7.setTitle("Danh Sách Khách Hàng");
        setSupportActionBar(binding.toolbar7);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolbar7.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        binding.toolbar7.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.recyclerviewKH.setHasFixedSize(true);
        binding.recyclerviewKH.setLayoutManager(new LinearLayoutManager(DanhSachKHActivity.this));
        GetData();
    }
    private void GetData(){
        APIUltil.getData().getListKH().enqueue(new Callback<List<KhachHang>>() {
            @Override
            public void onResponse(Call<List<KhachHang>> call, Response<List<KhachHang>> response) {
                khachHangs = response.body();
                adapter_kh = new Adapter_KH(khachHangs, DanhSachKHActivity.this);
                binding.recyclerviewKH.setAdapter(adapter_kh);
            }

            @Override
            public void onFailure(Call<List<KhachHang>> call, Throwable throwable) {
                Log.d("ff", "onFailure: "+ throwable.getMessage());
            }
        });
    }
}
