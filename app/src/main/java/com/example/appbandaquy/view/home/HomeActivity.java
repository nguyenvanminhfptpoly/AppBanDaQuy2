package com.example.appbandaquy.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.view.View;


import com.example.appbandaquy.R;
import com.example.appbandaquy.Service.APIUltil;
import com.example.appbandaquy.Service.DataClient;
import com.example.appbandaquy.model.home.Giohang;
import com.example.appbandaquy.model.home.SanPham;
import com.example.appbandaquy.presenter.home.OnItemListener;
import com.example.appbandaquy.view.adapter.AdapterSanPham;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView mRycSp;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterSanPham adapterSanPham;
    private BottomAppBar bottomAppBar;
    private NavigationView navigationView;
    private DataClient dataClient;
    private DrawerLayout drawerLayout;
    private FloatingActionButton fab;
    public static  ArrayList<Giohang> manggiohang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mRycSp = findViewById(R.id.rycSp);
        drawerLayout = findViewById(R.id.drawer_layout);

        mRycSp.setLayoutManager(new LinearLayoutManager(this));
        
        mRycSp.setHasFixedSize(true);
        getSp();
        navigationView = findViewById(R.id.nv);

        fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), GioHangActivity.class));
                }
            });
        if(manggiohang != null){

        }else {
            manggiohang = new ArrayList<>();
        }

    }

    public void getSp() {
        dataClient = APIUltil.getData();
        Call<List<SanPham>> listCall = dataClient.getList();
        listCall.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                final ArrayList<SanPham> sanPhams = (ArrayList<SanPham>) response.body();
                adapterSanPham = new AdapterSanPham(sanPhams, getApplicationContext(), new OnItemListener() {
                    @Override
                    public void OnItemListener(int position) {
                        Intent intent = new Intent(getApplicationContext(), ChitietActivity.class);
                        intent.putExtra("thongtinspp", sanPhams.get(position));
                        startActivity(intent);
                    }

                    @Override
                    public void OnLongClickItem(int position) {

                    }
                });
                mRycSp.setAdapter(adapterSanPham);
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
                Log.d("F", t.getMessage());
            }
        });
    }
}
