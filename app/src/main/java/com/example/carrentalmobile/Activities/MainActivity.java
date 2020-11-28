package com.example.carrentalmobile.Activities;

import android.content.Intent;
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
import com.example.carrentalmobile.R;
import com.example.carrentalmobile.recyclerview.AdapterList;
import com.example.carrentalmobile.recyclerview.CarCallback;
import com.example.carrentalmobile.recyclerview.ItemAnimator;

import java.util.List;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

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

        //todo: replace with a long press button
        btnDel.setOnClickListener(v -> {
            removeAnnounce();
        });

        adapterList = new AdapterList(carsList, this);

        InterfaceServer server = RetroFitInstance.getInstance().create(InterfaceServer.class);

        Call<List<AnnoucedCars>> call = server.getAllCarsListToRent();


        call.enqueue(new Callback<List<AnnoucedCars>>() {
            @Override
            public void onResponse(Call<List<AnnoucedCars>> call, Response<List<AnnoucedCars>> response) {
                carsList = response.body();
                adapterList.setAnnoucedCarsList(carsList);
                adapterList.notifyDataSetChanged();
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

    //todo: to be removed only to test add animation
    private void addCar() {
        ctr++;
        AnnoucedCars annoucedCars = new AnnoucedCars(ctr + "", "brand", "car", "5", "category", "description", "lol", true);
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
        Intent intent = new Intent(getBaseContext(), AnnounceDetailsActivity.class);
        intent.putExtra("carAnnounce", carsList.get(pos));

        Pair<View, String> p1 = Pair.create((View)imgContainer, "containerTN");
        Pair<View, String> p2 = Pair.create((View)imgCar, "carTN");
        Pair<View, String> p3 = Pair.create((View)title, "carTitleTN");
        Pair<View, String> p4 = Pair.create((View)brand, "carBrandTN");
        Pair<View, String> p5 = Pair.create((View)name, "carNameTN");
        Pair<View, String> p6 = Pair.create((View)seatCount, "carSeatCoutTN");
        Pair<View, String> p7 = Pair.create((View)price, "carPriceTN");
        Pair<View, String> p8 = Pair.create((View)town, "carTownTN");

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2, p3, p4, p5, p6, p7, p8);

        startActivityForResult(intent, 100, optionsCompat.toBundle());

    }
}