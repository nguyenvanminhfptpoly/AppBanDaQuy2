package com.example.appbandaquy.view.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appbandaquy.R;
import com.example.appbandaquy.view.adapter.Adapter_giohang;

public class GioHangActivity extends AppCompatActivity {

    private FloatingActionButton mFloatingActionButton;
    private ListView lv_giohang;
    private NavigationView mNv;
    private DrawerLayout mDrawerLayout;
    private BottomAppBar mBottomAppBar;
    private FloatingActionButton mFab;
    private Adapter_giohang adapter_giohang;
    private TextView tvTinhtien,tvThongbao;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
       Khaibao();
       EnventUltil();
       CatchOnItemListVew();
       ChechData();

    }
    public  void EnventUltil() {
        long tongtien = 0;
        for (int i = 0; i < HomeActivity.manggiohang.size(); i++){
            tongtien += HomeActivity.manggiohang.get(i).getGiasp();
        }
        tvTinhtien.setText(tongtien+"");
    }
    private void Khaibao(){
        mFloatingActionButton = findViewById(R.id.floatingActionButton);
        mNv = findViewById(R.id.nv);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar4);
        mFab = findViewById(R.id.fab_giohang);
        tvTinhtien = findViewById(R.id.tvTinhtien);
        tvThongbao = findViewById(R.id.tv_thongbao);
        lv_giohang = findViewById(R.id.lv_giohang);

        toolbar.setTitle(R.string.giohang);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ThongtinKHActivity.class));
            }
        });

        adapter_giohang = new Adapter_giohang(HomeActivity.manggiohang, getApplicationContext());
        lv_giohang.setAdapter(adapter_giohang);
    }
    private void CatchOnItemListVew() {
        lv_giohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Delete product");
                builder.setMessage(" Do you want delete this products");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(HomeActivity.manggiohang.size() <= 0){
                            tvThongbao.setVisibility(View.VISIBLE);
                        }else {
                            HomeActivity.manggiohang.remove(position);
                            adapter_giohang.notifyDataSetChanged();
                            EnventUltil();
                            if (HomeActivity.manggiohang.size() <= 0){
                                tvThongbao.setVisibility(View.VISIBLE);
                            }else {
                                tvThongbao.setVisibility(View.INVISIBLE);
                                adapter_giohang.notifyDataSetChanged();
                                EnventUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter_giohang.notifyDataSetChanged();
                        EnventUltil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }
    private void ChechData() {
        if(HomeActivity.manggiohang.size() <= 0){
            adapter_giohang.notifyDataSetChanged();
            tvThongbao.setVisibility(View.VISIBLE);
            lv_giohang.setVisibility(View.INVISIBLE);
        }else {
            adapter_giohang.notifyDataSetChanged();
            tvThongbao.setVisibility(View.INVISIBLE);
            lv_giohang.setVisibility(View.VISIBLE);
        }
    }
}
