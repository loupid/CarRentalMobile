package com.example.carrentalmobile.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class MyRentsActivity extends AppCompatActivity implements CarCallback {

    RecyclerView recyclerView;
    AdapterList adapterList;
    List<AnnoucedCars> carsList;
    Context context;
    TextView tvMyRents;
    int indexCarReturned;

    MenuItem menuAdd, menuHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rents);

        recyclerView = findViewById(R.id.RecycleViewMyRents);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setItemAnimator(new ItemAnimator());

        tvMyRents = findViewById(R.id.tvMyRents);
        tvMyRents.setTextSize(32);
        tvMyRents.setText("Mes locations");

        context = this;

        adapterList = new AdapterList(carsList, this);

        Api server = RetroFitInstance.getInstance().create(Api.class);
        SharedPreferences sharedPreferences = getSharedPreferences("stayConnected", MODE_PRIVATE);
        int connectedUserId = sharedPreferences.getInt("userId", 0);
        Call<List<AnnoucedCars>> call = server.getMyRents(connectedUserId + "");

        call.enqueue(new Callback<List<AnnoucedCars>>() {
            @Override
            public void onResponse(Call<List<AnnoucedCars>> call, Response<List<AnnoucedCars>> response) {
                carsList = response.body();
                if (carsList != null) {
                    adapterList.setAnnoucedCarsList(carsList);
                    adapterList.notifyDataSetChanged();
                    recyclerView.setAdapter(adapterList);
                } else {
                    tvMyRents.setTextSize(20);
                    tvMyRents.setText("Vous n'avez pas de locations!");
                }
            }

            @Override
            public void onFailure(Call<List<AnnoucedCars>> call, Throwable t) {
                Toast.makeText(MyRentsActivity.this, "Erreur de connexion au serveur", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.burger_menu, menu);
        menuAdd = menu.findItem(R.id.menuAdd);
        menuHome = menu.findItem(R.id.menuHome);

        menuHome.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            return false;
        });

        menuAdd.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(getApplicationContext(), AddAnnounceActivity.class);
            startActivity(intent);
            return false;
        });
        return true;
    }

    @Override
    public void onCarItemClick(int pos, ImageView imgContainer, ImageView imgCar, TextView title, TextView brand, TextView name, TextView price, TextView seatCount, TextView town, TextView description) {
        Intent intent = new Intent(getBaseContext(), MyRentDetailsActivity.class);
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
        startActivityForResult(intent, 1221, optionsCompat.toBundle());
    }

    @Override
    public void onCarLongPressClick(int pos) {
        new AlertDialog.Builder(context).setTitle("Annuler une location")
                .setMessage("Voulez-vous vraiement annuler la location?")
                .setPositiveButton("Oui", (dialog, which) -> {
                    Api server = RetroFitInstance.getInstance().create((Api.class));
                    Call<ResponseBody> call = server.cancelRent(carsList.get(pos).getIdannounce());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            indexCarReturned = pos;
                            carsList.remove(pos);
                            adapterList.notifyItemRemoved(pos);
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }).setNegativeButton("Non", null).setIcon(android.R.drawable.ic_menu_delete).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1221 && resultCode == RESULT_OK && carsList != null) {
            carsList.remove(indexCarReturned);
            adapterList.notifyItemRemoved(indexCarReturned);
            if (carsList.isEmpty()) {
                tvMyRents.setTextSize(20);
                tvMyRents.setText("Vous n'avez pas de locations!");
            }
        }
    }
}