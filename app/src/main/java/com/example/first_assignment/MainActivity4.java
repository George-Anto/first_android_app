package com.example.first_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {
    private String uid;
    private ArrayList<CitizenRequest> myRequests = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference requestsTable;

    private FirebaseStorage storage;
    private StorageReference imageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        uid = getIntent().getStringExtra("Uid");

        database = FirebaseDatabase.getInstance();
        requestsTable = database.getReference("citizens_requests");
        Query query = requestsTable.orderByChild("uid").equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                children.forEach(child -> {
                    CitizenRequest aRequest = child.getValue(CitizenRequest.class);
                    myRequests.add(aRequest);
//                    Log.d("My Requests", aRequest.toString());
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        storage = FirebaseStorage.getInstance();
//        imageRef = storage.getReferenceFromUrl(myRequests.get(0).getImagePath());
//        imageRef.getBytes(10);
    }

    private void showMessage(String title, String message){
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .show();
    }
}