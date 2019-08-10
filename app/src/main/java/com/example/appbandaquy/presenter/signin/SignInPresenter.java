package com.example.appbandaquy.presenter.signin;

import android.content.Context;
import android.databinding.ObservableField;

import com.example.appbandaquy.databinding.ActivityMainBinding;
import com.example.appbandaquy.presenter.signin.SignInPresenterInterface;
import com.example.appbandaquy.view.signin.SignInView;

public class SignInPresenter implements SignInPresenterInterface {
    SignInView signInView;


    public SignInPresenter(SignInView signInView) {

        this.signInView = signInView;

    }

    @Override
    public void SignIn(String email, String password) {
        if(email.equals("") && password.equals("")){
            signInView.OnFailed();
        }else {
            signInView.OnSucess();
        }
    }
}
