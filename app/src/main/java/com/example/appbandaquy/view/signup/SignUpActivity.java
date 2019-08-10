package com.example.appbandaquy.view.signup;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbandaquy.R;
import com.example.appbandaquy.Service.APIUltil;
import com.example.appbandaquy.Service.DataClient;
import com.example.appbandaquy.presenter.signup.SignUpPresenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements SignUpView {
    private TextView textView7;
    private ImageView imgUser;
    private EditText edEmail;
    private TextView textView8;
    private EditText edPass;
    private TextView textView9;
    private EditText edConfirmPass;
    private Button btnSignUp;
    int Image = 123;

    String realpath = "";
    String user,pass,confirmpass;
    File file;
    String file_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Khaibao();

    }

    private void Khaibao() {
        textView7 = (TextView) findViewById(R.id.textView7);
        imgUser = (ImageView) findViewById(R.id.imgUser);
        edEmail = (EditText) findViewById(R.id.edEmail);
        textView8 = (TextView) findViewById(R.id.textView8);
        edPass = (EditText) findViewById(R.id.edPass);
        textView9 = (TextView) findViewById(R.id.textView9);
        edConfirmPass = (EditText) findViewById(R.id.edConfirmPass);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        final SignUpPresenter signUpPresenter = new SignUpPresenter(this);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = edEmail.getText().toString();
                pass = edPass.getText().toString();
                confirmpass = edConfirmPass.getText().toString();
                signUpPresenter.SignUp(user,pass);
            }
        });
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, Image);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == Image && resultCode == RESULT_OK && data != null ){
            Uri uri = data.getData();
            realpath = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgUser.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    @Override
    public void onSuccess() {
        Signup();
    }

    @Override
    public void onFailed() {
        Toast.makeText(this, "Nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
    }

    public void Signup(){
        file = new File(realpath);
        file_path = file.getAbsolutePath();
        String[] mangtenfile = file_path.split("\\.");
        if(user.length() < 8 && pass.length() < 8 && confirmpass.length() < 8 && file.length() == 0 ) {
            Toast.makeText(this, "Nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
        }else if(mangtenfile.length != 0){
            file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[0];
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);

            DataClient dataClient = APIUltil.getData();

            Call<String> call = dataClient.UploadPhot(body);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response != null) {
                        String message = response.body();
                        if (message.length() > 0) {
                            DataClient insertData = APIUltil.getData();
                            retrofit2.Call<String> callb = insertData.insertData(user,pass,APIUltil.baseUrl + "image/" +message);
                            callb.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String result = response.body();
                                    if(result.equals("Success")){
                                        Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                        finish();
                                        Log.d("sinh", response.message());
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("failed", t.getMessage());
                }

            });
        }
    }
}
