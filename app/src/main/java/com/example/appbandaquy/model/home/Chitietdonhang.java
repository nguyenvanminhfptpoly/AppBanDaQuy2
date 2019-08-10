package com.example.appbandaquy.model.home;

public class Chitietdonhang {
    private String madonhang;
    private String masanpham;
    private String tensanpham;
    private int giasanpham;
    private int soluongsanpham;

    public Chitietdonhang(String madonhang, String masanpham, String tensanpham, int giasanpham, int soluongsanpham) {
        this.madonhang = madonhang;
        this.masanpham = masanpham;
        this.tensanpham = tensanpham;
        this.giasanpham = giasanpham;
        this.soluongsanpham = soluongsanpham;
    }

    public String getMadonhang() {
        return madonhang;
    }

    public void setMadonhang(String madonhang) {
        this.madonhang = madonhang;
    }

    public String getMasanpham() {
        return masanpham;
    }

    public void setMasanpham(String masanpham) {
        this.masanpham = masanpham;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public int getGiasanpham() {
        return giasanpham;
    }

    public void setGiasanpham(int giasanpham) {
        this.giasanpham = giasanpham;
    }

    public int getSoluongsanpham() {
        return soluongsanpham;
    }

    public void setSoluongsanpham(int soluongsanpham) {
        this.soluongsanpham = soluongsanpham;
    }
}
