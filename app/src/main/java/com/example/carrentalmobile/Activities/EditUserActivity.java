package com.example.carrentalmobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carrentalmobile.Database.Api;
import com.example.carrentalmobile.Database.RetroFitInstance;
import com.example.carrentalmobile.Model.User;
import com.example.carrentalmobile.R;

public class EditUserActivity extends AppCompatActivity {

    MenuItem menuAdd, menuProfile, menuHome;
    EditText etFirstname, etLastname, etUsername, etEmail, etPhone, etPassword;
    Button btnRegister;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        etFirstname = findViewById(R.id.etEditFirstname);
        etLastname = findViewById(R.id.etEditLastname);
        etUsername = findViewById(R.id.etEditUsername);
        etEmail = findViewById(R.id.etEditEmail);
        etPhone = findViewById(R.id.etEditPhone);
        btnRegister = findViewById(R.id.btnEditUser);

        SharedPreferences sharedPreferences = getSharedPreferences("stayConnected", MODE_PRIVATE);
        int connectedUserId = sharedPreferences.getInt("userId", 0);

        Api serveur = RetroFitInstance.getInstance().create((Api.class));
        Call<User> userCallback = serveur.getUserInfo(connectedUserId+"");
        userCallback.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                etFirstname.setText(user.getFirstname());
                etLastname.setText(user.getLastname());
                etUsername.setText(user.getUsername());
                etEmail.setText(user.getEmail());
                etPhone.setText(user.getPhone());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        btnRegister.setOnClickListener(view -> {
            if (etFirstname.getText().toString().equals("")) {
                etFirstname.setError("Le champ prénom ne peut pas être vide!");
                etFirstname.requestFocus();
            }
            else if (etLastname.getText().toString().equals("")) {
                etLastname.setError("Le champ nom ne peut pas être vide!");
                etLastname.requestFocus();
            }
            else if (etUsername.getText().toString().equals("")) {
                etUsername.setError("Le champ nom d'utilisateur ne peut pas être vide!");
                etUsername.requestFocus();
            }
            else if (etEmail.getText().toString().equals("")) {
                etEmail.setError("Le champ email ne peut pas être vide!");
                etEmail.requestFocus();
            }
            else if (!isValidEmail(etEmail.getText().toString())) {
                etEmail.setError("L'email n'est pas valide");
                etEmail.requestFocus();
            }
            else if (etPhone.getText().toString().equals("")) {
                etPhone.setError("Le champ téléphone ne peut pas être vide!");
                etPhone.requestFocus();
            }
            else if (!isValidPhone(etPhone.getText().toString())) {
                etPhone.setError("Le numéro de téléphone n'est pas valide");
                etPhone.requestFocus();
            }
            else {
                final ProgressDialog progressDialog = new ProgressDialog(EditUserActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Veuillez patienter");
                progressDialog.show();
                Call<ResponseBody> call = serveur.editUser(connectedUserId+"",
                        etFirstname.getText().toString(),
                        etLastname.getText().toString(),
                        etPhone.getText().toString(),
                        etEmail.getText().toString(),
                        etUsername.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(EditUserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
                finish();
            }
        });
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

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private static boolean isValidPhone(String phone) {
        return !TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches();
    }
}