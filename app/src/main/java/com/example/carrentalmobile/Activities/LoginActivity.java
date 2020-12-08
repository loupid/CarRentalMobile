package com.example.carrentalmobile.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    CheckBox cbStayConnected;
    Integer id;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;

        txtRegister = findViewById(R.id.txtRegister);
        etUsername = findViewById(R.id.etLoginUsername);
        etPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        cbStayConnected = findViewById(R.id.cbStayConnected);

        sharedPreferences = getSharedPreferences("stayConnected", MODE_PRIVATE);
        if(sharedPreferences.getBoolean("rememberMe", false)) {
            etUsername.setText(sharedPreferences.getString("username", ""));
            etPassword.setText(sharedPreferences.getString("password", ""));
            cbStayConnected.setChecked(true);
        }

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
                Call<Integer> call = server.login(
                        etUsername.getText().toString(),
                        etPassword.getText().toString()
                );
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        id = response.body();
                        sharedPreferences = getSharedPreferences("stayConnected", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        if (id > 0) {
                            editor.putInt("userId", id);
                            Toast.makeText(getApplicationContext(), "Connexion réussie!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            editor.putInt("userId", 0);
                            Toast.makeText(getApplicationContext(), "Erreur dans le mot de passe ou le nom d'utilisateur!", Toast.LENGTH_SHORT).show();
                        }
                        editor.apply();
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Erreur de connexion au serveur", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        cbStayConnected.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferences = getSharedPreferences("stayConnected", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("rememberMe", buttonView.isChecked());
            editor.putString("username", etUsername.getText().toString());
            editor.putString("password", etPassword.getText().toString());
            editor.apply();
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