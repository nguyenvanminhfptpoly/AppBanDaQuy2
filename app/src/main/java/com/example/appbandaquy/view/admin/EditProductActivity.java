package com.example.appbandaquy.view.admin;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.appbandaquy.R;
import com.example.appbandaquy.Service.APIUltil;
import com.example.appbandaquy.databinding.ActivityEditProductBinding;
import com.example.appbandaquy.model.home.SanPham;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProductActivity extends AppCompatActivity {
    ActivityEditProductBinding binding;
    private int id = 0;
    int Image = 123;
    String tensp,giasp,motasp,loaisp;
    String realpath = "";
    File file;
    String file_path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_product);
        binding.tlEdit.setTitle("Edit Products");
       setSupportActionBar(binding.tlEdit);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setDisplayShowHomeEnabled(true);
       binding.tlEdit.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
       binding.tlEdit.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
        GetInfomation();

        binding.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, Image);
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tensp = binding.edName.getText().toString();
                giasp = binding.edPrice.getText().toString();
                motasp = binding.edDetail.getText().toString();
                loaisp = binding.edType.getText().toString();
                    file = new File(realpath);
                    file_path = file.getAbsolutePath();
                    Log.d("///", file.getAbsolutePath());
                    String[] mangtenfile = file_path.split("\\.");
                if (tensp.length() < 6 && giasp.isEmpty() && motasp.length() < 10 && loaisp.length() < 5 && file.length()  == 0  ) {
                    Toast.makeText(getApplicationContext(), "Nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                } else if(mangtenfile != null) {
                    file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[0];
                    final RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);


                    APIUltil.getData().UploadPhot(part).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response != null) {
                                String message = response.body();
                                if (message.length() > 0) {
                                    APIUltil.getData().EditProduct(id,tensp,giasp, APIUltil.baseUrl + "image/" + message, motasp,loaisp).enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            String result = response.body();
                                            if (result.equals("Success")) {
                                                Toast.makeText(getApplicationContext(), "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(getApplicationContext(),AdminActivity.class);
                                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(i);
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                        Log.d("t", t.getMessage());
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                                Log.d("t2", t.getMessage());
                        }
                    });
                }
            }
        });

    }
    private void GetInfomation(){
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("editsanpham");
        id = sanPham.getId();
        binding.edDetail.setText(sanPham.getMotasp());
        binding.edPrice.setText(sanPham.getGiasp()+"");
        binding.edType.setText(sanPham.getIdsanpham());
        binding.edName.setText(sanPham.getTensp());
        Picasso.with(getApplicationContext()).load(sanPham.getHinhanhsp()).into(binding.imgProduct);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Image && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            realpath = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                binding.imgProduct.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
}
