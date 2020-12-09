package com.example.carrentalmobile.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.example.carrentalmobile.R;

public class DashboardActivity extends AppCompatActivity {

    Button btnMyAccount, btnMyAnnounces, btnMyLocations, btnDisconnect;
    MenuItem menuAdd, menuHome;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.burger_menu, menu);
        menuAdd = menu.findItem(R.id.menuAdd);
        menuHome = menu.findItem(R.id.menuHome);

        menuAdd.setOnMenuItemClickListener(item -> {
                Intent intent = new Intent(getBaseContext(), AddAnnounceActivity.class);
                startActivity(intent);
            return false;
        });

        menuHome.setOnMenuItemClickListener(menuItem -> {
            finish();
            return false;
        });
        return true;
    }

}