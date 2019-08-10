package com.example.appbandaquy.model.home;

public class Khachhang {
    private String sdt;
    private String name;
    private String email;


    public Khachhang(String name, String sdt, String email) {
        this.sdt = sdt;
        this.name = name;
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
