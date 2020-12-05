package com.example.carrentalmobile.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrentalmobile.Database.Api;
import com.example.carrentalmobile.Database.RetroFitInstance;
import com.example.carrentalmobile.Model.AnnoucedCars;
import com.example.carrentalmobile.R;
import com.example.carrentalmobile.recyclerview.AdapterList;
import com.example.carrentalmobile.recyclerview.CarCallback;
import com.example.carrentalmobile.recyclerview.ItemAnimator;

import java.util.List;

import androidx.annotation.Nullable;
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
    MenuItem menuAdd, menuProfile;
    Context context;
    List<AnnoucedCars> carsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.RecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setItemAnimator(new ItemAnimator());

        context = this;

        adapterList = new AdapterList(carsList, this);

        Api server = RetroFitInstance.getInstance().create(Api.class);

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
                Toast.makeText(MainActivity.this, "Erreur de connexion au serveur", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.burger_menu, menu);
        menuAdd = menu.findItem(R.id.menuAdd);
        menuProfile = menu.findItem(R.id.menuProfile);

        menuAdd.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(context, AddAnnounceActivity.class);
            startActivityForResult(intent, 11);
            return false;
        });

        menuProfile.setOnMenuItemClickListener(menuItem -> {
            //todo: check if user is logged open profile (add, edit and del announce) else open login/register
            Intent intent = new Intent(context, LoginActivity.class);
            startActivityForResult(intent, 12);
            return false;
        });
        return true;
    }

    @Override
    public void onCarItemClick(int pos, ImageView imgContainer, ImageView imgCar, TextView title, TextView brand, TextView name, TextView price, TextView seatCount, TextView town, TextView description) {
        Intent intent = new Intent(getBaseContext(), AnnounceDetailsActivity.class);
        intent.putExtra("carAnnounce", carsList.get(pos));

        Pair<View, String> p1 = Pair.create(imgContainer, "containerTN");
        Pair<View, String> p2 = Pair.create(imgCar, "carTN");
        Pair<View, String> p3 = Pair.create(title, "carTitleTN");
        Pair<View, String> p4 = Pair.create(brand, "carBrandTN");
        Pair<View, String> p5 = Pair.create(name, "carNameTN");
        Pair<View, String> p6 = Pair.create(seatCount, "carSeatCoutTN");
        Pair<View, String> p7 = Pair.create(price, "carPriceTN");
        Pair<View, String> p8 = Pair.create(town, "carTownTN");
        Pair<View, String> p9 = Pair.create(town, "carCategTN");

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2, p3, p4, p5, p6, p7, p8, p9);

        startActivityForResult(intent, 10, optionsCompat.toBundle());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {
            if (resultCode == RESULT_OK) {
                AnnoucedCars annoucedCars = data.getParcelableExtra("carAnnounce");
                carsList.add(0, annoucedCars);
                adapterList.notifyItemInserted(1);
            }
        }
    }
}