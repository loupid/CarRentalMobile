package com.example.carrentalmobile.Database;

import com.example.carrentalmobile.Model.AnnoucedCars;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InterfaceServer {
    @GET("/Php/CarsList.php")
    Call<List<AnnoucedCars>> getAllCarsListToRent();
}
