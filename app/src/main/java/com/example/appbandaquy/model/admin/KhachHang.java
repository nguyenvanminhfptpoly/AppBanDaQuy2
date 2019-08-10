
package com.example.appbandaquy.model.admin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KhachHang {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tenkhachhang")
    @Expose
    private String tenkhachhang;
    @SerializedName("sodienthoai")
    @Expose
    private String sodienthoai;
    @SerializedName("email")
    @Expose
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenkhachhang() {
        return tenkhachhang;
    }

    public void setTenkhachhang(String tenkhachhang) {
        this.tenkhachhang = tenkhachhang;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
