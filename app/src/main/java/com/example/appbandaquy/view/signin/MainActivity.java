package com.example.appbandaquy.view.signin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbandaquy.R;
import com.example.appbandaquy.Service.APIUltil;
import com.example.appbandaquy.Service.DataClient;
import com.example.appbandaquy.databinding.ActivityMainBinding;
import com.example.appbandaquy.model.signin.SignInModel;
import com.example.appbandaquy.presenter.signin.SignInPresenter;
import com.example.appbandaquy.view.admin.AdminActivity;
import com.example.appbandaquy.view.home.HomeActivity;
import com.example.appbandaquy.view.signup.SignUpActivity;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SignInView {
    private TextView textView;
    private ImageView imgSignIn;

    private TextView textView2;
    private EditText edPass;
    private TextView textView3;
    private TextView tvSignUp;
    private TextView textView5;
    private Button btnSignIn;
    private Button btnSignInFB;
    private TextView textView6;
    private EditText edEmail;
    public String email, password;


    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Khaibao();
        initPermission();
        initPermission2();
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

    public void initPermission(){
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
    public void initPermission2(){
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
    private void Khaibao() {
        textView = (TextView) findViewById(R.id.textView);
        imgSignIn = (ImageView) findViewById(R.id.imgSignIn);
        edEmail = findViewById(R.id.edEmail);
        textView2 = (TextView) findViewById(R.id.textView2);
        edPass = (EditText) findViewById(R.id.edPass);
        textView3 = (TextView) findViewById(R.id.textView3);
        tvSignUp = (TextView) findViewById(R.id.tvSignUp);
        textView5 = (TextView) findViewById(R.id.textView5);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignInFB = (Button) findViewById(R.id.btnSignInFB);
        textView6 = (TextView) findViewById(R.id.textView6);

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });

        final SignInPresenter signInPresenter = new SignInPresenter(this);

        btnSignInFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edEmail.getText().toString();
                password = edPass.getText().toString();
                if(email.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")){
                    startActivity(new Intent(MainActivity.this, AdminActivity.class));
                }else {
                    signInPresenter.SignIn(email, password);

                }
            }
        });
    }

    @Override
    public void OnSucess() {
        SignIn();
    }

    private void SignIn() {
        if (email.length() > 0 && password.length() > 0) {
            DataClient dataClient = APIUltil.getData();
            Call<List<SignInModel>> callb = dataClient.signinData(email, password);
            callb.enqueue(new Callback<List<SignInModel>>() {
                @Override
                public void onResponse(Call<List<SignInModel>> call, Response<List<SignInModel>> response) {
                    ArrayList<SignInModel> manguser = (ArrayList<SignInModel>) response.body();
                    if (manguser.size() > 0) {
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("manguser", manguser);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<List<SignInModel>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            Toast.makeText(this, "Nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void OnFailed() {
        Toast.makeText(this, "Nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
    }

}
