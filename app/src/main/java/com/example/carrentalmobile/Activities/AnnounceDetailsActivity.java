package com.example.carrentalmobile.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carrentalmobile.Model.AnnoucedCars;
import com.example.carrentalmobile.R;

public class AnnounceDetailsActivity extends AppCompatActivity {

    TextView carName, title, numberPlace, pricePerDay, location, brand, description;
    ImageView imageViewCar;


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
        description = findViewById(R.id.tvDescription);
        imageViewCar = findViewById(R.id.car_image);

        loadAnnounceData(annoucedCars);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.burger_menu, menu);
        return true;
    }

    private void loadAnnounceData(AnnoucedCars annoucedCars) {
        carName.setText(annoucedCars.getCarname());
        title.setText(annoucedCars.getTitle());
        numberPlace.setText(annoucedCars.getSeatcount());
        pricePerDay.setText(annoucedCars.getTypename());
        location.setText(annoucedCars.getIdannounce());
        brand.setText(annoucedCars.getBrandname());
        description.setText(annoucedCars.getDescription());

        //todo: bind image
    }
}