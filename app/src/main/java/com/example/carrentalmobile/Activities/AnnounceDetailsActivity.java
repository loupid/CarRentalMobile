package com.example.carrentalmobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrentalmobile.Database.Api;
import com.example.carrentalmobile.Database.RetroFitInstance;
import com.example.carrentalmobile.Model.AnnoucedCars;
import com.example.carrentalmobile.R;

public class AnnounceDetailsActivity extends AppCompatActivity {

    TextView carName, title, numberPlace, pricePerDay, location, brand, description, category;
    ImageView imageViewCar;
    AnnoucedCars details;
    Button btnRent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce_details);
        AnnoucedCars annoucedCars = getIntent().getExtras().getParcelable("carAnnounce");

        carName = findViewById(R.id.tvCarName);
        title = findViewById(R.id.tvTitle);
        numberPlace = findViewById(R.id.tvSeatCount);
        pricePerDay = findViewById(R.id.tvPrice);
        location = findViewById(R.id.tvTown);
        brand = findViewById(R.id.tvBrand);
        category = findViewById(R.id.tvCategory);
        description = findViewById(R.id.tvDescription);
        imageViewCar = findViewById(R.id.details_car_image);
        btnRent = findViewById(R.id.btnRent);

        loadAnnounceData(annoucedCars);

        location.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), MapsActivity2.class);
            intent.putExtra("address", location.getText().toString());
            startActivity(intent);
        });

        btnRent.setOnClickListener(v -> {
            Api server = RetroFitInstance.getInstance().create((Api.class));
            SharedPreferences sharedPreferences = getSharedPreferences("stayConnected", MODE_PRIVATE);
            int connectedUserId = sharedPreferences.getInt("userId", 0);
            if (connectedUserId > 0){
                Call<ResponseBody> rentCall = server.rent(connectedUserId + "", annoucedCars.getIdannounce());
                rentCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Intent intentReturn = new Intent();
                            intentReturn.putExtra("rented", true);

                            setResult(RESULT_OK, intentReturn);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.burger_menu, menu);
        return true;
    }

    private void loadAnnounceData(AnnoucedCars annoucedCars) {
        //todo: fix description
        carName.setText(annoucedCars.getCarname());
        title.setText(annoucedCars.getTitle());
        numberPlace.setText(annoucedCars.getSeatcount());
        pricePerDay.setText(annoucedCars.getPrice());
        location.setText(annoucedCars.getLocation());
        brand.setText(annoucedCars.getBrandname());
        Api server = RetroFitInstance.getInstance().create(Api.class);
        Call<AnnoucedCars> callGetAnnounceInfo = server.getAnnounceInfo(annoucedCars.getIdannounce());
        callGetAnnounceInfo.enqueue(new Callback<AnnoucedCars>() {
            @Override
            public void onResponse(Call<AnnoucedCars> call, Response<AnnoucedCars> response) {
                details = response.body();
                if (response.isSuccessful()) {
                    category.setText(details.getCategory());
                    description.setText(details.getDescription());
                } else {
                    category.setText("Catégorie");
                    description.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
                }
            }

            @Override
            public void onFailure(Call<AnnoucedCars> call, Throwable t) {
                Toast.makeText(AnnounceDetailsActivity.this, "Erreur de connexion au serveur", Toast.LENGTH_SHORT).show();
            }
        });

        Call<ResponseBody> call = server.download(annoucedCars.getFilepath());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                        imageViewCar.setImageBitmap(bmp);
                    } else {
                        imageViewCar.setImageResource(R.drawable.default_car);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                imageViewCar.setImageResource(R.drawable.default_car);
            }
        });
    }
}