package com.example.carrentalmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InterfaceServer server = RetroFitInstance.getInstance().create(InterfaceServer.class);

                Call<List<Cars>> call = server.getAllCarsListToRent();

                call.enqueue(new Callback<List<Cars>>() {
                    @Override
                    public void onResponse(Call<List<Cars>> call, Response<List<Cars>> response) {
                        Log.d("RetroFit", response.body().get(0).toString());
                    }

                    @Override
                    public void onFailure(Call<List<Cars>> call, Throwable t) {
                        Log.d("RetroFitError", t.getMessage());
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.burger_menu, menu);
        return true;
    }
}