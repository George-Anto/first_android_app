package com.example.first_assignment;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.first_assignment.databinding.ActivityMapsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private String category;
    private ArrayList<CitizenRequest> totalRequests = new ArrayList<>();

    private FirebaseDatabase database;
    private DatabaseReference requestsTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        category = getIntent().getStringExtra("Category");

        database = FirebaseDatabase.getInstance();
        requestsTable = database.getReference("citizens_requests");

        if (category.equals("All")) {
            requestsTable.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Iterable<DataSnapshot> children = snapshot.getChildren();
                    children.forEach(child -> {
                        CitizenRequest aRequest = child.getValue(CitizenRequest.class);
                        totalRequests.add(aRequest);
                    });
                    Log.d("My Requests", totalRequests.toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("Database Error", error.getMessage());
                }
            });
        } else {
            Query query = requestsTable.orderByChild("category").equalTo(category);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Iterable<DataSnapshot> children = snapshot.getChildren();
                    children.forEach(child -> {
                        CitizenRequest aRequest = child.getValue(CitizenRequest.class);
                        totalRequests.add(aRequest);
                    });
                    Log.d("My Requests", totalRequests.toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("Database Error", error.getMessage());
                }
            });
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}