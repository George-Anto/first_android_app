package com.example.first_assignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.storage.StorageReference;


import java.util.UUID;

public class MainActivity3 extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LocationListener {
    private Spinner spinner;
    private static final String[] paths = {"Puddle", "Broken Traffic Light", "Damaged Building",
            "Fallen Tree", "Other"};

    private LocationManager manager;

    private FirebaseDatabase database;
    private DatabaseReference requestsTable;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    private ImageView imageToUploadView;
    public Uri imageUri;

    private EditText descriptionText;

    private String uid;
    private String category;
    private double latitude;
    private double longitude;
    private String description;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        uid = getIntent().getStringExtra("Uid");

        imageToUploadView = findViewById(R.id.newRequestImageView);
        imageToUploadView.setOnClickListener(v -> { chooseImage(); });

        descriptionText = findViewById(R.id.newRequestDescriptionText);

        useGPS();

        spinner = findViewById(R.id.categoriesSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity3.this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        database = FirebaseDatabase.getInstance();
        requestsTable = database.getReference("citizens_requests");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                category = paths[0];
                break;
            case 1:
                category = paths[1];
                break;
            case 2:
                category = paths[2];
                break;
            case 3:
                category = paths[3];
                break;
            case 4:
                category = paths[4];
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        manager.removeUpdates(this);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    private void useGPS() {
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},123);
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
            }
        } else {
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
        }
    }

    private void chooseImage() {
        startActivityForResult(new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageToUploadView.setImageURI(imageUri);
            uploadImage();
        }
    }

    private void uploadImage() {
        final String randomKey = UUID.randomUUID().toString();
        StorageReference imagesStorageRef = storageReference.child("images/" + randomKey);

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Processing Image...");
        pd.show();

        imagesStorageRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            pd.dismiss();
            StorageReference currentImagePath = storageReference.child("images/" + randomKey);
            currentImagePath.getDownloadUrl().addOnSuccessListener(uri -> imagePath = uri.toString());
            Snackbar.make(findViewById(android.R.id.content), "Image Processed", Snackbar.LENGTH_LONG).show();
        }).addOnFailureListener(e -> {
            pd.dismiss();
            Toast.makeText(getApplicationContext(), "Upload Failed", Toast.LENGTH_LONG).show();
        }).addOnProgressListener(snapshot -> {
            double progressPercent = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
            pd.setMessage((int) progressPercent + "%");
        });
    }

    public void onsSendRequest(View view) {
        description = descriptionText.getText().toString();
        if (description.trim().matches("")) {
            Toast.makeText(this, "Please provide a description.", Toast.LENGTH_LONG).show();
            return;
        }
        if (imagePath == null) {
            Toast.makeText(this, "Please upload an image of the incident.", Toast.LENGTH_LONG).show();
            return;
        }
        if (latitude == 0 || longitude == 0) {
            Toast.makeText(this, "Please enable GPS to retrieve your location.", Toast.LENGTH_LONG).show();
            return;
        }
        CitizenRequest currentRequest = new CitizenRequest(uid, latitude, longitude, category, description, imagePath);
        requestsTable.push().setValue(currentRequest);
        showMessage("Success", "Your Request has been uploaded.");
        imageToUploadView.setImageURI(Uri.parse(""));
        descriptionText.setText("");
    }

    private void showMessage(String title, String message){
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .show();
    }
}