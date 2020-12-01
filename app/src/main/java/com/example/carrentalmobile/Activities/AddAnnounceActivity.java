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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.carrentalmobile.Database.InterfaceServer;
import com.example.carrentalmobile.Database.RetroFitInstance;
import com.example.carrentalmobile.Model.AnnoucedCars;
import com.example.carrentalmobile.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddAnnounceActivity extends AppCompatActivity {

    EditText etTitle, etDescription, etSeatCount, etBrand, etCarName, etLocation, etPrice, etCateg;
    Switch cpAvailable;
    Button btnAdd;
    ImageView imgCar;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    String currentPhotoPath;

    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_announce);

        //todo: check edittext id
        etBrand = findViewById(R.id.etAnnounceBrand);
        etTitle = findViewById(R.id.etAnnounceTitle);
        etDescription = findViewById(R.id.etAnnounceDescription);
        etSeatCount = findViewById(R.id.etAnnounceSeatCount);
        etCarName = findViewById(R.id.etAnnounceCarName);
        etLocation = findViewById(R.id.etAnnounceLocation);
        etPrice = findViewById(R.id.etAnnouncePrice);
        etCateg = findViewById(R.id.etAnnounceCateg);
        btnAdd = findViewById(R.id.btnAnnounceAdd);
        cpAvailable = findViewById(R.id.cpAnnounceAvailable);
        imgCar = findViewById(R.id.imgAnnounceCar);

        imgCar.setVisibility(View.INVISIBLE);
        if (checkPermission()) {
            launchProgram();
        }

        btnAdd.setOnClickListener(v -> {
            Intent intentReturn = new Intent();
            if (etTitle.getText().toString().equals(""))
                etTitle.setError("Le champ titre ne peut pas être vide!");
            else if (etBrand.getText().toString().equals(""))
                etBrand.setError("Le champ marque ne peut pas être vide!");
            else if (etCarName.getText().toString().equals(""))
                etCarName.setError("Le champ modèle ne peut pas être vide!");
            else if (etCateg.getText().toString().equals(""))
                etCateg.setError("Le champ catégorie ne peut pas être vide!");
            else if (etLocation.getText().toString().equals(""))
                etLocation.setError("Le champ localisation ne peut pas être vide!");
            else if (etSeatCount.getText().toString().equals(""))
                etSeatCount.setError("Le champ nombre de passager ne peut pas être vide!");
            else if (Integer.parseInt(etSeatCount.getText().toString()) <= 0)
                etSeatCount.setError("Le champ nombre de passager doit être strictement positif!");
            else if (etPrice.getText().toString().equals(""))
                etPrice.setError("Le champ prix ne peut pas être vide!");
            else if (Double.parseDouble(etPrice.getText().toString()) <= 0)
                etCarName.setError("Le champ prix doit être strictement positif!");
            else {
                AnnoucedCars annoucedCars = new AnnoucedCars(
                        etTitle.getText().toString(),
                        etBrand.getText().toString(),
                        etCarName.getText().toString(),
                        etSeatCount.getText().toString(),
                        etCateg.getText().toString(),
                        etDescription.getText().toString(),
                        etPrice.getText().toString(),
                        "image",
                        cpAvailable.isChecked());
                intentReturn.putExtra("carAnnounce", annoucedCars);
                setResult(RESULT_OK, intentReturn);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.burger_menu, menu);
        return true;
    }

    public void launchProgram() {
        imgCar.setVisibility(View.VISIBLE);

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
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* dossier */
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

        MultipartBody.Part part_fichier = MultipartBody.Part.createFormData("photo",
                photoFile.getName(),
                fichier_requete);

        InterfaceServer serveur = RetroFitInstance.getInstance().create((InterfaceServer.class));
        Call<ResponseBody> call = serveur.upload(requete, part_fichier);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body() != null)
                        Toast.makeText(AddAnnounceActivity.this, response.body().string(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddAnnounceActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}