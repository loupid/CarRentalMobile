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
                Call<ResponseBody> call = server.login(
                        etUsername.getText().toString(),
                        etPassword.getText().toString()
                );
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {

                        } else {
                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

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