package com.example.carrentalmobile.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.carrentalmobile.R;

public class DashboardActivity extends AppCompatActivity {

    Button btnMyAccount, btnMyAnnounces, btnMyLocations, btnDisconnect;
    int userConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnMyAccount = findViewById(R.id.btnMyAccount);
        btnMyAnnounces = findViewById(R.id.btnMyAnnounces);
        btnMyLocations = findViewById(R.id.btnMyLocations);
        btnDisconnect = findViewById(R.id.btnDisconnect);

        Intent getIntent = getIntent();
        userConnected = getIntent.getIntExtra("id", 0);

        btnMyAccount.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), EditUserActivity.class);
            //todo: intent put extra user info (to set the edit texts)
            startActivity(intent);
        });

        btnMyAnnounces.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), MyAnnouncesActivity.class);
            intent.putExtra("id", userConnected);
            startActivity(intent);
        });

        btnMyLocations.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), MyRentsActivity.class);
            intent.putExtra("id", userConnected);
            startActivity(intent);
        });

        btnDisconnect.setOnClickListener(v -> finish());
    }
}