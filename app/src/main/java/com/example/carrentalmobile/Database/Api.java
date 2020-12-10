package com.example.carrentalmobile.Database;

import com.example.carrentalmobile.Model.AnnoucedCars;
import com.example.carrentalmobile.Model.User;

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
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Api {
    @GET("Php/CarsList.php")
    Call<List<AnnoucedCars>> getAllCarsListToRent();

    @GET
    Call<ResponseBody> download(@Url String url);

    @GET("Php/GetAnnounceInfo.php")
    Call<AnnoucedCars> getAnnounceInfo(
            @Query("idannounce") String idannounce
    );

    @POST("Php/Login.php")
    @FormUrlEncoded
    Call<Integer> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @POST("Php/RemoveRent.php")
    @FormUrlEncoded
    Call<ResponseBody> cancelRent(
            @Field("idannounce") String idannounce
    );

    @POST("Php/RemoveAnnounce.php")
    @FormUrlEncoded
    Call<ResponseBody> removeAnnounce(
            @Field("idannounce") String idannounce
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
            @Field("iduserowner") String IdUserOwner,
            @Field("title") String title,
            @Field("brandname") String brand,
            @Field("carname") String car,
            @Field("description") String description,
            @Field("seatcount") String seatcount,
            @Field("category") String category,
            @Field("location") String location,
            @Field("imgfilepath") String imgfilepath,
            @Field("price") String price,
            @Field("available") int available);

    @POST("Php/Register.php")
    @FormUrlEncoded
    Call<ResponseBody> register(
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("phonenumber") String phonenumber,
            @Field("email") String email,
            @Field("password") String password,
            @Field("username") String username
    );

    @POST("Php/AddRent.php")
    @FormUrlEncoded
    Call<ResponseBody> rent(
            @Field("iduserowner") String iduserowner,
            @Field("idannounce") String idannounce
    );

    //todo
    @POST("Php/EditUser.php")
    @FormUrlEncoded
    Call<ResponseBody> editUser(
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("phonenumber") String phonenumber,
            @Field("email") String email,
            @Field("password") String password,
            @Field("username") String username
    );

    @GET("Php/GetMyRents.php")
    Call<List<AnnoucedCars>> getMyRents(
            @Query("iduserclient") String iduserclient
    );

    //todo
    @GET("Php/GetUserInfo.php")
    Call<List<AnnoucedCars>> getUserInfo(
            @Query("iduser") String iduser
    );

    @POST("Php/EditAnnounce.php")
    @FormUrlEncoded
    Call<ResponseBody> editAnnounce(
            @Field("idannounce") String idAnnounce,
            @Field("title") String title,
            @Field("brandname") String brand,
            @Field("carname") String car,
            @Field("description") String description,
            @Field("seatcount") String seatcount,
            @Field("category") String category,
            @Field("location") String location,
            @Field("imgfilepath") String imgfilepath,
            @Field("price") String price,
            @Field("available") int available);

    //todo
    @GET("Php/GetMyAnnounces.php")
    Call<List<AnnoucedCars>> getMyAnnounces(
            @Query("iduserowner") String iduserowner
    );
}
