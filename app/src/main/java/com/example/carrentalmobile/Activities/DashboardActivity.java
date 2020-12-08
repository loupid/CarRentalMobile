package com.example.carrentalmobile.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.example.carrentalmobile.R;

public class DashboardActivity extends AppCompatActivity {

    Button btnMyAccount, btnMyAnnounces, btnMyLocations, btnDisconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnMyAccount = findViewById(R.id.btnMyAccount);
        btnMyAnnounces = findViewById(R.id.btnMyAnnounces);
        btnMyLocations = findViewById(R.id.btnMyLocations);
        btnDisconnect = findViewById(R.id.btnDisconnect);

        btnMyAccount.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), EditUserActivity.class);
            //todo: intent put extra user info (to set the edit texts)
            startActivity(intent);
        });

        btnMyAnnounces.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), MyAnnouncesActivity.class);
            startActivity(intent);
        });

        btnMyLocations.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), MyRentsActivity.class);
            startActivity(intent);
        });

        btnDisconnect.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("stayConnected", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("userId", 0);
            editor.apply();
            finish();
        });
    }
}