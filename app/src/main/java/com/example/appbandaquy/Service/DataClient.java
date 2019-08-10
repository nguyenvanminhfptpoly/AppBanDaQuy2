package com.example.appbandaquy.Service;


import com.example.appbandaquy.model.admin.KhachHang;
import com.example.appbandaquy.model.home.Chitietdonhang;
import com.example.appbandaquy.model.home.Khachhang;
import com.example.appbandaquy.model.home.SanPham;
import com.example.appbandaquy.model.signin.SignInModel;
import com.example.appbandaquy.model.signup.SignUpModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DataClient {


    @Multipart
    @POST("uploadimage.php")
    Call<String> UploadPhot(@Part MultipartBody.Part photo);

    @FormUrlEncoded
    @POST("insert.php")
    Call<String> insertData(@Field("username") String username,
                            @Field("password") String password,
                            @Field("image") String image
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<List<SignInModel>> signinData(@Field("username") String user,
                                       @Field("password") String passwword
    );

    @POST("getsp.php")
    Call<List<SanPham>> getList();

    @FormUrlEncoded
    @POST("thongtinkh.php")
    Call<String> thongtinkh(@Field("tenkh") String tenkh,
                               @Field("sdt") String sdt,
                               @Field("mail") String email
                               );

    @FormUrlEncoded
    @POST("donhangchitiet.php")
    Call<String> donhangchitiet(@FieldMap Map<String,String> data);

    @FormUrlEncoded
    @POST("admin_addproduct.php")
    Call<String> insertProduct(@Field("tensp") String tensp,
                               @Field("giasp") String giasp,
                               @Field("hinhanhsp") String hinhanhsp,
                               @Field("motasp") String motasp,
                               @Field("idsanpham") String idsanpham
                               );
    @FormUrlEncoded
    @POST("admin_editproduct.php")
    Call<String> EditProduct(@Field("id") int id,
                             @Field("tensp") String tensp,
                             @Field("giasp") String giasp,
                             @Field("hinhanhsp") String hinhanhsp,
                             @Field("motasp") String motasp,
                             @Field("idsanpham") String idsanpham
    );

    @FormUrlEncoded
    @POST("admin_deleteproduct.php")
    Call<String> DeleteProduct(@FieldMap Map<String,String> data);



    @POST("danhsachkh.php")
    Call<List<KhachHang>> getListKH();
}
