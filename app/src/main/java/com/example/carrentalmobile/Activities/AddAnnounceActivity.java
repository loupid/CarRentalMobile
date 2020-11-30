package com.example.carrentalmobile.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.carrentalmobile.Model.AnnoucedCars;
import com.example.carrentalmobile.R;
import com.google.android.material.chip.Chip;

public class AddAnnounceActivity extends AppCompatActivity {

    EditText etTitle, etDescription, etSeatCount, etBrand, etCarName, etLocation, etPrice, etCateg;
    Switch cpAvailable;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_announce);

        etBrand = findViewById(R.id.etBrand);
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etSeatCount = findViewById(R.id.etSeatCount);
        etCarName = findViewById(R.id.etCarName);
        etLocation = findViewById(R.id.etLocation);
        etPrice = findViewById(R.id.etPrice);
        etCateg = findViewById(R.id.etCateg);
        btnAdd = findViewById(R.id.btnAdd);
        cpAvailable = findViewById(R.id.cpAvailable);

        btnAdd.setOnClickListener(v -> {
            Intent intentReturn = new Intent();
            if (etTitle.getText().toString().equals(""))
                etTitle.setError("Le champ titre ne peut pas être vide!");
            else if (etBrand.getText().toString().equals(""))
                etBrand.setError("Le champ marque ne peut pas être vide!");
            else if (etCarName.getText().toString().equals(""))
                etCarName.setError("Le champ modèle ne peut pas être vide!");
            else if (etCateg.getText().toString().equals(""))
                etCateg.setError("Le champ catégorie ne peut pas être vide!");
            else if (etLocation.getText().toString().equals(""))
                etLocation.setError("Le champ localisation ne peut pas être vide!");
            else if (etSeatCount.getText().toString().equals(""))
                etSeatCount.setError("Le champ nombre de passager ne peut pas être vide!");
            else if (Integer.parseInt(etSeatCount.getText().toString()) <= 0)
                etSeatCount.setError("Le champ nombre de passager doit être strictement positif!");
            else if (etPrice.getText().toString().equals(""))
                etPrice.setError("Le champ prix ne peut pas être vide!");
            else if (Double.parseDouble(etPrice.getText().toString()) <= 0)
                etCarName.setError("Le champ prix doit être strictement positif!");
            else {
                AnnoucedCars annoucedCars = new AnnoucedCars(
                        etTitle.getText().toString(),
                        etBrand.getText().toString(),
                        etCarName.getText().toString(),
                        etSeatCount.getText().toString(),
                        etCateg.getText().toString(),
                        etDescription.getText().toString(),
                        etPrice.getText().toString(),
                        "image",
                        cpAvailable.isChecked());
                intentReturn.putExtra("carAnnounce", annoucedCars);
                setResult(RESULT_OK, intentReturn);
                finish();
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