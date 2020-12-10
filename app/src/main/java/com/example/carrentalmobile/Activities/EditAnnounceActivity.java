package com.example.carrentalmobile.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.carrentalmobile.Database.Api;
import com.example.carrentalmobile.Database.RetroFitInstance;
import com.example.carrentalmobile.Model.AnnoucedCars;
import com.example.carrentalmobile.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditAnnounceActivity extends AppCompatActivity {

    EditText etTitle, etDescription, etSeatCount, etBrand, etCarName, etLocation, etPrice, etCateg;
    Switch cpAvailable;
    Button btnEdit;
    ImageView imgCar;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    String currentPhotoPath;
    MenuItem menuAdd, menuProfile, menuHome;
    AnnoucedCars annoucedCars;

    Context context;

    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_announce);

        etBrand = findViewById(R.id.etAnnounceBrandEdit);
        etTitle = findViewById(R.id.etAnnounceTitleEdit);
        etDescription = findViewById(R.id.etAnnounceDescriptionEdit);
        etSeatCount = findViewById(R.id.etAnnounceSeatCountEdit);
        etCarName = findViewById(R.id.etAnnounceCarNameEdit);
        etLocation = findViewById(R.id.etAnnounceLocationEdit);
        etPrice = findViewById(R.id.etAnnouncePriceEdit);
        etCateg = findViewById(R.id.etAnnounceCategEdit);
        btnEdit = findViewById(R.id.btnAnnounceEdit);
        cpAvailable = findViewById(R.id.cpAnnounceAvailableEdit);
        imgCar = findViewById(R.id.imgAnnounceCarEdit);

        context = this;

        Api server = RetroFitInstance.getInstance().create((Api.class));
        Intent intent = getIntent();
        String idAnnounce = intent.getStringExtra("carAnnounceId");
        Call<AnnoucedCars> callGetAnnounceInfo = server.getAnnounceInfo(idAnnounce);
        callGetAnnounceInfo.enqueue(new Callback<AnnoucedCars>() {
            @Override
            public void onResponse(Call<AnnoucedCars> call, Response<AnnoucedCars> response) {
                annoucedCars = response.body();
                etBrand.setText(annoucedCars.getBrandname());
                etTitle.setText(annoucedCars.getTitle());
                etDescription.setText(annoucedCars.getDescription());
                etSeatCount.setText(annoucedCars.getSeatcount());
                etCarName.setText(annoucedCars.getCarname());
                etLocation.setText(annoucedCars.getLocation());
                etPrice.setText(annoucedCars.getPrice().replace(" $/jour", ""));
                etCateg.setText(annoucedCars.getBrandname());

                Call<ResponseBody> callDownloadImg = server.download(annoucedCars.getFilepath());
                callDownloadImg.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                                imgCar.setImageBitmap(bmp);
                            } else {
                                imgCar.setImageResource(R.drawable.default_car);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        imgCar.setImageResource(R.drawable.default_car);
                    }
                });
            }

            @Override
            public void onFailure(Call<AnnoucedCars> call, Throwable t) {
                Toast.makeText(EditAnnounceActivity.this, "Erreur de connexion au serveur", Toast.LENGTH_SHORT).show();
            }
        });

        imgCar.setVisibility(View.INVISIBLE);
        if (checkPermission()) {
            launchProgram();
        }

        btnEdit.setOnClickListener(v -> {

            new AlertDialog.Builder(context).setTitle("Modifier une annonce")
                    .setMessage("Voulez-vous vraiement modifier l'annonce?")
                    .setPositiveButton("Oui", (dialog, which) -> {

                        Intent intentReturn = new Intent();
                        if (etTitle.getText().toString().equals("")) {
                            etTitle.setError("Le champ titre ne peut pas être vide!");
                            etTitle.requestFocus();
                        } else if (etBrand.getText().toString().equals("")) {
                            etBrand.setError("Le champ marque ne peut pas être vide!");
                            etBrand.requestFocus();
                        } else if (etCarName.getText().toString().equals("")) {
                            etCarName.setError("Le champ modèle ne peut pas être vide!");
                            etCarName.requestFocus();
                        } else if (etCateg.getText().toString().equals("")) {
                            etCateg.setError("Le champ catégorie ne peut pas être vide!");
                            etCateg.requestFocus();
                        } else if (etLocation.getText().toString().equals("")) {
                            etLocation.setError("Le champ localisation ne peut pas être vide!");
                            etLocation.requestFocus();
                        } else if (etSeatCount.getText().toString().equals("")) {
                            etSeatCount.setError("Le champ nombre de passager ne peut pas être vide!");
                            etSeatCount.requestFocus();
                        } else if (Integer.parseInt(etSeatCount.getText().toString()) <= 0) {
                            etSeatCount.setError("Le champ nombre de passager doit être strictement positif!");
                            etSeatCount.requestFocus();
                        } else if (etPrice.getText().toString().equals("")) {
                            etPrice.setError("Le champ prix ne peut pas être vide!");
                            etPrice.requestFocus();
                        } else if (Double.parseDouble(etPrice.getText().toString()) <= 0) {
                            etCarName.setError("Le champ prix doit être strictement positif!");
                            etCarName.requestFocus();
                        } else {
                            final ProgressDialog progressDialog = new ProgressDialog(EditAnnounceActivity.this);
                            progressDialog.setCancelable(false);
                            progressDialog.setMessage("Veuillez patienter");
                            progressDialog.show();
                            AnnoucedCars newAnnounce = new AnnoucedCars(
                                    etTitle.getText().toString(),
                                    etBrand.getText().toString(),
                                    etLocation.getText().toString(),
                                    etCarName.getText().toString(),
                                    etSeatCount.getText().toString(),
                                    etCateg.getText().toString(),
                                    etDescription.getText().toString(),
                                    photoFile.getName(),
                                    cpAvailable.isChecked(),
                                    etPrice.getText().toString());
                            newAnnounce.setIdannounce(annoucedCars.getIdannounce());
                            intentReturn.putExtra("newAnnounce", newAnnounce);
                            setResult(RESULT_OK, intentReturn);
                            Call<ResponseBody> callEdit = server.editAnnounce(
                                    annoucedCars.getIdannounce(),
                                    etTitle.getText().toString(),
                                    etBrand.getText().toString(),
                                    etCarName.getText().toString(),
                                    etDescription.getText().toString(),
                                    etSeatCount.getText().toString(),
                                    etCateg.getText().toString(),
                                    etLocation.getText().toString(),
                                    photoFile.getName(),
                                    etPrice.getText().toString(),
                                    (cpAvailable.isChecked()) ? 1 : 0);
                            callEdit.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Modification réussie!", Toast.LENGTH_SHORT).show();
                                    } else
                                        Toast.makeText(getApplicationContext(), "Erreur d'ajout", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(EditAnnounceActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });
                            finish();
                        }

                    }).setNegativeButton("Non", null).setIcon(android.R.drawable.ic_menu_delete).show();
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
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            return false;
        });

        menuProfile.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(context, DashboardActivity.class);
            startActivity(intent);
            return false;
        });
        return true;
    }

    public void launchProgram() {
        imgCar.setVisibility(View.VISIBLE);
        try {
            photoFile = createPhotoFile();
        } catch (IOException exception) {
            Toast.makeText(EditAnnounceActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
        imgCar.setOnClickListener(view -> launchCamera());
    }


    private void launchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // on s'assure que l'activité de la camera existe bel et bien
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // on crée le fichier devant recevoir la photo
            photoFile = null;
            try {
                photoFile = createPhotoFile();
            } catch (IOException ex) {
                // gestion d'erreur eventuelle lors de la création du fichier
            }
            // on continue si le fichier est créé correctement
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.carrentalmobile.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createPhotoFile() throws IOException {
        // un nom aléatoire sera créé en utilisant la date du système
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            imgCar.setImageBitmap(bitmap);

            saveImage();
        }
    }

    public boolean checkPermission() {
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        List<String> listePermissionsADemander = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listePermissionsADemander.add(permission);
            }
        }

        if (listePermissionsADemander.isEmpty())
            return true;
        else {
            ActivityCompat.requestPermissions(this, listePermissionsADemander.toArray(new String[listePermissionsADemander.size()]), 1111);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        int permissionRefusedCount = 0;

        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                permissionRefusedCount++;
            }
        }

        //s'il y a des persmissions qui ne sont pas accordées on l'indique à l'utilisateur
        //sinon si toutes les permissions sont accordées, on peut rouler le programme
        if (permissionRefusedCount > 0)
            Toast.makeText(this, "Veuillez accepter les permissions", Toast.LENGTH_LONG).show();
        else
            launchProgram();

    }

    public void saveImage() {
        //le paramètre requête
        RequestBody requete = RequestBody.create(MediaType.parse("text/plain"), "upload");

        //on doit indiquer le type de fichier. Pour une image mettre "image/*"
        MediaType mediaType = MediaType.parse("image/*");
        RequestBody fichier_requete = RequestBody.create(mediaType, photoFile);

        MultipartBody.Part part_fichier = MultipartBody.Part.createFormData("file",
                photoFile.getName(),
                fichier_requete);

        Api serveur = RetroFitInstance.getInstance().create((Api.class));
        Call<ResponseBody> call = serveur.upload(requete, part_fichier);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(EditAnnounceActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}