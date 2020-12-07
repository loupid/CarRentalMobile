package com.example.carrentalmobile.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrentalmobile.Database.Api;
import com.example.carrentalmobile.Database.RetroFitInstance;
import com.example.carrentalmobile.Model.AnnoucedCars;
import com.example.carrentalmobile.Model.LoginResponse;
import com.example.carrentalmobile.R;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    TextView txtRegister;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;

        txtRegister = findViewById(R.id.txtRegister);
        etUsername = findViewById(R.id.etLoginUsername);
        etPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(view -> {
            if (etUsername.getText().toString().equals("")) {
                etUsername.setError("Le champ nom d'utilisateur ne peut pas être vide!");
                etUsername.requestFocus();
            } else if (etPassword.getText().toString().equals("")) {
                etPassword.setError("Le champ mot de passe ne peut pas être vide!");
                etPassword.requestFocus();
            }
            else {
                Api server = RetroFitInstance.getInstance().create((Api.class));
                Intent intentReturn = new Intent();
                Call<Integer> call = server.login(
                        etUsername.getText().toString(),
                        etPassword.getText().toString()
                );
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Integer id = response.body();
                        if (id > 0) {
                            Toast.makeText(getApplicationContext(), "Connexion réussie!", Toast.LENGTH_SHORT).show();
                            intentReturn.putExtra("username", etUsername.getText().toString());
                            intentReturn.putExtra("id", id);
                            setResult(RESULT_OK, intentReturn);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erreur dans le mot de passe ou le nom d'utilisateur!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Erreur de connexion au serveur", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        txtRegister.setOnClickListener(view -> {
            Intent intent = new Intent(context, RegisterActivity.class);
            startActivityForResult(intent, 13);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 13) {
            if (resultCode == RESULT_OK) {
                //todo: get username and set it to username
            }
        }
    }
}