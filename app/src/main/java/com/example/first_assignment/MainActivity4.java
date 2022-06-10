package com.example.first_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity4 extends AppCompatActivity {
    private String uid;
    private final ArrayList<CitizenRequest> myRequests = new ArrayList<>();
    private TextView myRequestsView;

    private FirebaseDatabase database;
    private DatabaseReference requestsTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        uid = getIntent().getStringExtra("Uid");
        myRequestsView = findViewById(R.id.myRequestsView);
        myRequestsView.setText("Loading Requests...");

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
                });
//                Log.d("My Requests", aRequest.toString());
                if (myRequests.size() == 0) {
                    myRequestsView.setText("You have not made any Requests yet.\nGo to New Requests, to create one.");
                } else {
                    StringBuilder builder = new StringBuilder();
                    AtomicInteger i = new AtomicInteger();
                    myRequests.forEach(aRequest -> {
                        builder.append("Request").append(" ").append(i.incrementAndGet()).append("\n");
                        builder.append("Category: ").append(aRequest.getCategory()).append("\n");
                        builder.append("Description: ").append(aRequest.getDescription()).append("\n");
                        builder.append("Date: ").append(aRequest.getDate()).append("\n");
                        builder.append("Time: ").append(aRequest.getTime()).append("\n");
                        builder.append("Location: ").append(aRequest.getLocationAddress()).append("\n");
                        builder.append("\n");
                    });
                    myRequestsView.setText(builder.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                myRequestsView.setText("Unknown Error \n Please try again later.");
            }
        });
    }
}