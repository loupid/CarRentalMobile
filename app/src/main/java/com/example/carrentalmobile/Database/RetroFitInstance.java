package com.example.carrentalmobile.Database;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitInstance {
    public static final String BASE_URL = "http://206.167.140.56:8080/A2020/420505RI/Equipe_1/";
    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
