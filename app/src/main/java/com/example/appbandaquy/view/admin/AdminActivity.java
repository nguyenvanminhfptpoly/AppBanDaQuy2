package com.example.appbandaquy.view.admin;

import android.Manifest;


import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.databinding.DataBindingUtil;

import android.os.Build;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.appbandaquy.R;
import com.example.appbandaquy.Service.APIUltil;
import com.example.appbandaquy.Service.DataClient;
import com.example.appbandaquy.databinding.ActivityAdminBinding;


import com.example.appbandaquy.databinding.DialogDeleteProductBinding;
import com.example.appbandaquy.model.home.SanPham;


import com.example.appbandaquy.presenter.home.OnItemListener;
import com.example.appbandaquy.view.adapter.AdapterSanPham;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminActivity extends AppCompatActivity {
    ActivityAdminBinding binding;

    DataClient dataClient;
    AdapterSanPham adapterSanPham;
    ArrayList<SanPham> sanPhams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin);
        GetProduct();
        initPermission();
        initPermission2();
        initAdapter();
        AddProduct();

        binding.toolbar2.setTitle(R.string.danhsach);
        setSupportActionBar(binding.toolbar2);



    }



    public void initAdapter() {
        binding.rycyclerview.setLayoutManager(new LinearLayoutManager(AdminActivity.this));
        binding.rycyclerview.setHasFixedSize(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permision Write File is Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Permision Write File is Denied", Toast.LENGTH_SHORT).show();

            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(getApplicationContext(), "Permission isn't granted ", Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(getApplicationContext(), "Permisson don't granted and dont show dialog again ", Toast.LENGTH_SHORT).show();
                }
                //Register permission
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            }
        }
    }

    public void initPermission2() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(getApplicationContext(), "Permission isn't granted ", Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(getApplicationContext(), "Permisson don't granted and dont show dialog again ", Toast.LENGTH_SHORT).show();
                }
                //Register permission
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

            }
        }
    }

    public void GetProduct() {


        dataClient = APIUltil.getData();
        Call<List<SanPham>> listCall = dataClient.getList();
        listCall.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                sanPhams = (ArrayList<SanPham>) response.body();
                adapterSanPham = new AdapterSanPham(sanPhams, AdminActivity.this, new OnItemListener() {
                    @Override
                    public void OnItemListener(int position) {
                        Intent intent = new Intent(getApplicationContext(), EditProductActivity.class);
                        intent.putExtra("editsanpham", sanPhams.get(position));
                        startActivity(intent);
                    }

                    @Override
                    public void OnLongClickItem(int position) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                        DialogDeleteProductBinding binding1 = DataBindingUtil.inflate(LayoutInflater.from(getApplicationContext()), R.layout.dialog_delete_product,null,false);
                        builder.setView(binding1.getRoot());
                        final Dialog dialog = builder.create();
                        dialog.show();

                        final SanPham sanPham = sanPhams.get(position);

                        binding1.button6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Map<String,String> map =  new HashMap<>();
                                map.put("id", String.valueOf(sanPham.getId()));

                                APIUltil.getData().DeleteProduct(map).enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        String result = response.body();
                                        if(result.equals("success")){
                                            Toast.makeText(getApplicationContext(), "Xóa Thành công", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            adapterSanPham.notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {

                                    }
                                });
                            }
                        });
                        binding1.button7.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
                binding.rycyclerview.setAdapter(adapterSanPham);
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
                Log.d("F", t.getMessage());
            }
        });

    }

    public void AddProduct() {
        binding.floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AddProductActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.receipt:
                startActivity(new Intent(AdminActivity.this, DanhSachKHActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
