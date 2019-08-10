package com.example.appbandaquy.Service;

public class APIUltil {
    public static final String baseUrl = "http://192.168.0.103/QLSV/";

    public static DataClient getData(){
        return RetrofitClient.getRetrofit(baseUrl).create(DataClient.class);
    }
}
