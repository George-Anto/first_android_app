package com.example.first_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity3 extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LocationListener {
    private Spinner spinner;
    private static final String[] paths = {"Puddle", "Broken Traffic Light", "Damaged Building",
            "Fallen Tree", "Other"};
    private LocationManager manager;
    private FirebaseDatabase database;
    private DatabaseReference requestsTable;
    private String uid;
    private String category;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        uid = getIntent().getStringExtra("Uid");

        useGPS();

        spinner = findViewById(R.id.categoriesSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity3.this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        database = FirebaseDatabase.getInstance();
        requestsTable = database.getReference("citizens_requests");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Toast.makeText(this, paths[0], Toast.LENGTH_LONG).show();
                category = paths[0];
                break;
            case 1:
                Toast.makeText(this, paths[1], Toast.LENGTH_LONG).show();
                category = paths[1];
                break;
            case 2:
                Toast.makeText(this, paths[2], Toast.LENGTH_LONG).show();
                category = paths[2];
                break;
            case 3:
                Toast.makeText(this, paths[3], Toast.LENGTH_LONG).show();
                category = paths[3];
                break;
            case 4:
                Toast.makeText(this, paths[4], Toast.LENGTH_LONG).show();
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
        //Toast.makeText(this, Double.toString(latitude), Toast.LENGTH_LONG).show();
        //manager.removeUpdates(this);
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

    public void onsSendRequest(View view) {

    }
}