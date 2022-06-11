package com.example.first_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ViewMyRequestsActivity extends AppCompatActivity {
    //User's id
    private String uid;
    //ArrayList with all the requests the current user has uploaded
    private final ArrayList<CitizenRequest> myRequests = new ArrayList<>();
    //View of the requests
    private TextView myRequestsView;

    //Db and db reference
    private FirebaseDatabase database;
    private DatabaseReference requestsTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_requests);

        //Retrieve user's id
        uid = getIntent().getStringExtra("Uid");

        myRequestsView = findViewById(R.id.myRequestsView);
        //Set the text to the view as "Loading..." while we obtain the requests from the remote db
        myRequestsView.setText("Loading Requests...");

        //Initialize db and reference
        database = FirebaseDatabase.getInstance();
        requestsTable = database.getReference("citizens_requests");

        //Create query, so we retrieve only the requests that have the same uid with our current user
        Query query = requestsTable.orderByChild("uid").equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Get the data and store the in the ArrayList we created
                Iterable<DataSnapshot> children = snapshot.getChildren();
                children.forEach(child -> {
                    CitizenRequest aRequest = child.getValue(CitizenRequest.class);
                    myRequests.add(aRequest);
                });
                //If the ArrayList remained with 0 size, then the current user has made no requests yet
                if (myRequests.size() == 0) {
                    myRequestsView.setText("You have not made any Requests yet.\nGo to New Requests, to create one.");
                //Else at least one request is present
                } else {
                    StringBuilder builder = new StringBuilder();
                    AtomicInteger i = new AtomicInteger();
                    //For each request, store its data to the StringBuilder we created
                    myRequests.forEach(aRequest -> {
                        builder.append("Request").append(" ").append(i.incrementAndGet()).append("\n");
                        builder.append("Category: ").append(aRequest.getCategory()).append("\n");
                        builder.append("Description: ").append(aRequest.getDescription()).append("\n");
                        builder.append("Date: ").append(aRequest.getDate()).append("\n");
                        builder.append("Time: ").append(aRequest.getTime()).append("\n");
                        builder.append("Location: ").append(aRequest.getLocationAddress()).append("\n");
                        builder.append("\n");
                    });
                    //Set the taxt to the requests view to the contents of the StringBuilder
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