package com.example.appbandaquy.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.appbandaquy.R;
import com.example.appbandaquy.model.home.Giohang;
import com.example.appbandaquy.model.home.SanPham;
import com.squareup.picasso.Picasso;

public class ChitietActivity extends AppCompatActivity {

    private ImageView mImgHinhAnhSP;
    private TextView mTvTenSP;
    private TextView mTvGiaSp;
    private TextView mTvDetailSP;
    private Spinner mSpSoLuong;
    private BottomAppBar bta_chitietsp;
    private FloatingActionButton fab_chitietsp;
    private NavigationView mNv;
    private DrawerLayout mDrawerLayout;
    private BottomAppBar mBottomAppBarChitietsp;
    private FloatingActionButton mFabChitietsp;
    private Toolbar toolbar;
    private int giasp, id = 0;
    private String Tensp, Motasp, HinhanhSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet);
        Khaibao();

        GetInfomation();
        SpinnerOnclick();
        EventButton();

    }
    private void Khaibao(){
        mImgHinhAnhSP = findViewById(R.id.imgHinhAnhSP);
        mTvTenSP = findViewById(R.id.tvTenGiohang);
        mTvGiaSp = findViewById(R.id.tvGiaSp);
        mTvDetailSP = findViewById(R.id.tvSoluongSPChitiet);
        mSpSoLuong = findViewById(R.id.spSoLuong);
        mNv = findViewById(R.id.nv);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mFabChitietsp = findViewById(R.id.fab_chitietsp);
        toolbar = findViewById(R.id.toolbar3);


        toolbar.setTitle(R.string.chitiet);
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
    }
    private void SpinnerOnclick() {
        Integer[] soluong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        mSpSoLuong.setAdapter(arrayAdapter);

    }

    private void GetInfomation() {
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongtinspp");
        id = sanPham.getId();
        Tensp =  sanPham.getTensp();
        giasp = sanPham.getGiasp();
        HinhanhSP = sanPham.getHinhanhsp();
        Picasso.with(getApplicationContext()).load(sanPham.getHinhanhsp()).into(mImgHinhAnhSP);
        mTvDetailSP.setText(sanPham.getMotasp());
        mTvGiaSp.setText(sanPham.getGiasp() + "");
        mTvTenSP.setText(sanPham.getTensp());
    }

    private void EventButton(){
        mFabChitietsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HomeActivity.manggiohang.size() > 0){
                    int sl = Integer.parseInt(mSpSoLuong.getSelectedItem().toString());
                    boolean exite = false;
                    for (int i = 0;i < HomeActivity.manggiohang.size(); i++){
                        if(HomeActivity.manggiohang.get(i).getIdsp() == id){
                            HomeActivity.manggiohang.get(i).setSoluongsp(HomeActivity.manggiohang.get(i).getSoluongsp() + sl);
                            if(HomeActivity.manggiohang.get(i).getSoluongsp() > 0){
                                HomeActivity.manggiohang.get(i).setSoluongsp(10);
                            }
                            HomeActivity.manggiohang.get(i).setGiasp(giasp + HomeActivity.manggiohang.get(i).getSoluongsp());
                            exite = true;
                        }
                    }
                    if (exite == false){
                        int soluong = Integer.parseInt(mSpSoLuong.getSelectedItem().toString());
                        long Giamoi = soluong * giasp;
                        HomeActivity.manggiohang.add(new Giohang(id,soluong,Tensp,HinhanhSP,Giamoi ));
                    }
                }else {
                    int soluong = Integer.parseInt(mSpSoLuong.getSelectedItem().toString());
                    long Giamoi = soluong * giasp;
                    HomeActivity.manggiohang.add(new Giohang(id,soluong,Tensp,HinhanhSP,Giamoi ));
                }
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });
    }
}
