package com.example.appbandaquy.presenter.signup;

import com.example.appbandaquy.view.signup.SignUpView;

public class SignUpPresenter implements SignUpPresenterInterface {
    SignUpView signUpView;
    public SignUpPresenter(SignUpView signUpView){
        this.signUpView = signUpView;
    }
    @Override
    public void SignUp(String email, String pass) {
        if(email.equals("") && pass.equals("")){
            signUpView.onFailed();
        }else {
            signUpView.onSuccess();
        }
    }
}
