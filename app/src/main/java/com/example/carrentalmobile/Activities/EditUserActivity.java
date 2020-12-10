package com.example.carrentalmobile.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.carrentalmobile.R;

public class EditUserActivity extends AppCompatActivity {

    MenuItem menuAdd, menuProfile, menuHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.burger_menu, menu);
        menuAdd = menu.findItem(R.id.menuAdd);
        menuProfile = menu.findItem(R.id.menuProfile);

        menuHome = menu.findItem(R.id.menuHome);

        menuHome.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            return false;
        });

        menuProfile.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
            startActivity(intent);
            return false;
        });
        return true;
    }
}