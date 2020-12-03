package com.example.carrentalmobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carrentalmobile.Database.InterfaceServer;
import com.example.carrentalmobile.Database.RetroFitInstance;
import com.example.carrentalmobile.Model.AnnoucedCars;
import com.example.carrentalmobile.R;

import java.util.List;

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
        imageViewCar = findViewById(R.id.details_car_image);

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

        InterfaceServer server = RetroFitInstance.getInstance().create(InterfaceServer.class);

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