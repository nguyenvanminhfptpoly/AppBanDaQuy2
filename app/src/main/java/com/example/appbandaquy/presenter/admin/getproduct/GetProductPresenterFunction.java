package com.example.appbandaquy.presenter.admin.getproduct;


import android.app.Activity;

import android.content.Context;

import android.databinding.DataBindingUtil;

import com.example.appbandaquy.R;

import com.example.appbandaquy.Service.DataClient;
import com.example.appbandaquy.databinding.ActivityAdminBinding;


import com.example.appbandaquy.view.adapter.AdapterSanPham;


public class GetProductPresenterFunction implements GetProductPresenter {
    public Context context;
    public ActivityAdminBinding binding;
    public DataClient dataClient;
    public AdapterSanPham adapterSanPham;


    public GetProductPresenterFunction(Context context) {
        this.context = context;
    }


    @Override
    public void GetProduct() {

        binding = DataBindingUtil.setContentView((Activity)context, R.layout.activity_admin);






    }




}
