package com.example.carrentalmobile.Database;

import com.example.carrentalmobile.Model.AnnoucedCars;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface InterfaceServer {
    @GET("Php/CarsList.php")
    Call<List<AnnoucedCars>> getAllCarsListToRent();

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
            @Field("seatcount") String caseatcountr,
            @Field("typename") String typename,
            @Field("imgFileName") String imgFileName,
            @Field("price") String price,
            @Field("available") String available);
}
