package com.example.carrentalmobile;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrentalmobile.Database.InterfaceServer;
import com.example.carrentalmobile.Database.RetroFitInstance;
import com.example.carrentalmobile.Model.AnnoucedCars;
import com.example.carrentalmobile.recyclerview.AdapterList;
import com.example.carrentalmobile.recyclerview.CarCallback;
import com.example.carrentalmobile.recyclerview.ItemAnimator;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements CarCallback {
    RecyclerView recyclerView;
    AdapterList adapterList;
    Button btnAdd, btnDel;
    List<AnnoucedCars> carsList;
    int ctr = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.RecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //todo: delete the add/del button (just to test the animation)
        btnAdd = findViewById(R.id.btnAdd);
        btnDel = findViewById(R.id.btnDel);

        recyclerView.setItemAnimator(new ItemAnimator());

        btnAdd.setOnClickListener(v -> addCar());

        btnDel.setOnClickListener(v -> {
            removeAnnounce();
        });


        InterfaceServer server = RetroFitInstance.getInstance().create(InterfaceServer.class);

        Call<List<AnnoucedCars>> call = server.getAllCarsListToRent();


        call.enqueue(new Callback<List<AnnoucedCars>>() {
            @Override
            public void onResponse(Call<List<AnnoucedCars>> call, Response<List<AnnoucedCars>> response) {
                carsList = response.body();
                adapterList = new AdapterList(carsList);
                recyclerView.setAdapter(adapterList);
            }

            @Override
            public void onFailure(Call<List<AnnoucedCars>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeAnnounce() {
        carsList.remove(0);
        adapterList.notifyItemRemoved(0);
    }

    private void addCar() {
        ctr++;
        AnnoucedCars annoucedCars = new AnnoucedCars(ctr + "", "brand", "car", "5", "category", "description", "lol");
        carsList.add(0, annoucedCars);
        adapterList.notifyItemInserted(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.burger_menu, menu);
        return true;
    }

    @Override
    public void onCarItemClick(int pos, ImageView imgContainer, ImageView imgCar, TextView title, TextView brand, TextView name, TextView price, TextView seatCount, TextView town, TextView description) {
        
    }
}