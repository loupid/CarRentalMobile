package com.example.carrentalmobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.carrentalmobile.R;

public class RegisterActivity extends AppCompatActivity {

    EditText etFirstname, etLastname, etUsername, etEmail, etPassword, etConfirmPassword, etPhone;
    Button btnRegister;

    Context context;

    MenuItem menuAdd, menuProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFirstname = findViewById(R.id.etRegisterFirstname);
        etLastname = findViewById(R.id.etRegisterLastname);
        etUsername = findViewById(R.id.etRegisterUsername);
        etEmail = findViewById(R.id.etRegisterEmail);
        etPhone = findViewById(R.id.etRegisterPhone);
        etPassword = findViewById(R.id.etRegisterPassword);
        etConfirmPassword = findViewById(R.id.etRegisterConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

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
            else if (etPassword.getText().toString().equals("")) {
                etPassword.setError("Le champ mot de passe ne peut pas être vide!");
                etPassword.requestFocus();
            }
            else if (etConfirmPassword.getText().toString().equals("")) {
                etConfirmPassword.setError("Le champ confirmer mot de passe ne peut pas être vide!");
                etConfirmPassword.requestFocus();
            }
            else if (!etConfirmPassword.getText().toString().equals(etPassword.getText().toString())) {
                etConfirmPassword.setError("Les champs mot de passe et confirmer mot de passe ne sont pas identiques!");
                etConfirmPassword.requestFocus();
            }
            else {
                final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Veuillez patienter");
                progressDialog.show();
                Api serveur = RetroFitInstance.getInstance().create((Api.class));
                Call<ResponseBody> call = serveur.register(
                        etFirstname.getText().toString(),
                        etLastname.getText().toString(),
                        etUsername.getText().toString(),
                        etEmail.getText().toString(),
                        etPhone.getText().toString(),
                        etPassword.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
                finish();
            }
        });
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private static boolean isValidPhone(String phone) {
        return !TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.burger_menu, menu);
        menuAdd = menu.findItem(R.id.menuAdd);
        menuProfile = menu.findItem(R.id.menuProfile);

        menuAdd.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(context, AddAnnounceActivity.class);
            startActivityForResult(intent, 11);
            return false;
        });

        menuProfile.setOnMenuItemClickListener(menuItem -> {
            //todo: check if user is logged open profile (add, edit and del announce) else open login/register
            Intent intent = new Intent(context, LoginActivity.class);
            startActivityForResult(intent, 12);
            return false;
        });
        return true;
    }
}