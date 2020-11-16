package com.example.carrentalmobile.Database;

import com.example.carrentalmobile.Model.Cars;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InterfaceServer {
    @GET("/Php/CarsList.php")
    Call<List<Cars>> getAllCarsListToRent();
}
