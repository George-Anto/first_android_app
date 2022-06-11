package com.example.first_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainMenuActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String uid;
    String category;

    //Dropdown list with all the categories of requests the user can select and view on map
    private Spinner spinner;
    //The categories we will use
    private static final String[] paths = {"All", "Puddle", "Broken Traffic Light", "Damaged Building",
            "Fallen Tree", "Other"};

    private Button goToMapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Retrieve user's id
        uid = getIntent().getStringExtra("Uid");

        goToMapButton = findViewById(R.id.goToMapButton);

        //Create the dropdown list with it's options
        spinner = findViewById(R.id.viewOnMapSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainMenuActivity.this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    //Get the category of requests the user selected
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
            case 5:
                category = paths[5];
                break;
            default:
                category = "All";
        }
        //Change the goToMap button's text according to the category the user's selected
        goToMapButton.setText("View " + category + " Requests On Map");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    //Navigate user to the new request activity
    public void  onNewRequest(View view) {
        //Send the user's id to the next activity as well
        startActivity(new Intent(this, CreateNewRequestActivity.class).putExtra("Uid", uid));
    }

    //Navigate user to view their past requests activity
    public void onViewMyRequests(View view) {
        //Send the id too
        startActivity(new Intent(this, ViewMyRequestsActivity.class).putExtra("Uid", uid));
    }

    //Navigate user to view requests on map activity
    public void onViewOnMap(View view) {
        //Send the category the user selected to the next activity
        startActivity(new Intent(this, MapsActivity.class).putExtra("Category", category));
    }
}