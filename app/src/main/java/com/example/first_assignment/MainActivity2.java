package com.example.first_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String uid;
    String category;

    private Spinner spinner;
    private static final String[] paths = {"All", "Puddle", "Broken Traffic Light", "Damaged Building",
            "Fallen Tree", "Other"};

    private Button goToMapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        uid = getIntent().getStringExtra("Uid");

        goToMapButton = findViewById(R.id.goToMapButton);

        spinner = findViewById(R.id.viewOnMapSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity2.this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
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
            case 5:
                category = paths[5];
                break;
            default:
                category = "All";
        }
        goToMapButton.setText("View " + category + " Requests On Map");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    public void  onNewRequest(View view) {
        startActivity(new Intent(this, MainActivity3.class).putExtra("Uid", uid));
    }

    public void onViewMyRequests(View view) {
        startActivity(new Intent(this, MainActivity4.class).putExtra("Uid", uid));
    }

    public void onViewOnMap(View view) {
        startActivity(new Intent(this, MapsActivity.class).putExtra("Category", category));
    }
}