package com.example.carrentalmobile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InterfaceServer {
    @GET("/Php/CarsList.php")
    Call<List<Cars>> getAllCarsListToRent();
}
