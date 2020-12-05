package com.example.carrentalmobile.Database;

import com.example.carrentalmobile.Model.AnnoucedCars;
import com.example.carrentalmobile.Model.LoginResponse;
import com.example.carrentalmobile.Model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface Api {
    @GET("Php/CarsList.php")
    Call<List<AnnoucedCars>> getAllCarsListToRent();

    @GET
    Call<ResponseBody> download(@Url String url);

    @POST("Php/Login.php")
    @FormUrlEncoded
    Call<ResponseBody> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @Multipart
    @POST("Php/Upload.php")
    Call<ResponseBody> upload(
            @Part("requete") RequestBody requete,
            @Part MultipartBody.Part image
    );

    @POST("Php/AddAnnounce.php")
    @FormUrlEncoded
    Call<ResponseBody> addAnnounce(
            @Field("title") String title,
            @Field("brandname") String brand,
            @Field("carname") String car,
            @Field("description") String description,
            @Field("seatcount") String seatcount,
            @Field("typename") String typename,
            @Field("imgfilepath") String imgfilepath,
            @Field("price") String price,
            @Field("available") boolean available);

    @POST("Php/Register.php")
    @FormUrlEncoded
    Call<ResponseBody> register(
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("username") String username,
            @Field("email") String email,
            @Field("phonenumber") String phonenumber,
            @Field("password") String password
    );
}
