package com.example.first_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Spinner spinner;
    private static final String[] paths = {"Puddle", "Broken Traffic Light", "Damaged Building",
            "Fallen Tree", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        spinner = findViewById(R.id.categoriesSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity3.this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Toast.makeText(this, "Puddle", Toast.LENGTH_LONG).show();
                break;
            case 1:
                Toast.makeText(this, "Broken Traffic Light", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(this, "Damaged Building", Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText(this, "Fallen Tree", Toast.LENGTH_LONG).show();
                break;
            case 4:
                Toast.makeText(this, "Other", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}