package com.example.appbandaquy.model.signin;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class SignInModel implements Serializable, Parcelable {
    public String id;
    public String email;
    public String password;
    public String image;

    public SignInModel(String id, String email, String password, String image) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    protected SignInModel(Parcel in) {
        id = in.readString();
        email = in.readString();
        password = in.readString();
        image = in.readString();
    }

    public static final Creator<SignInModel> CREATOR = new Creator<SignInModel>() {
        @Override
        public SignInModel createFromParcel(Parcel in) {
            return new SignInModel(in);
        }

        @Override
        public SignInModel[] newArray(int size) {
            return new SignInModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(image);
    }
}
