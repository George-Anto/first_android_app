package com.example.first_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
    }

    public void onAllCategories(View view) {
        startActivity(new Intent(this, MapsActivity.class).putExtra("Category", "All"));
    }

    public void onPuddles(View view) {
        startActivity(new Intent(this, MapsActivity.class).putExtra("Category", "Puddle"));
    }

    public void onBrokenTrafficLight(View view) {
        startActivity(new Intent(this, MapsActivity.class).putExtra("Category", "Broken Traffic Light"));
    }

    public void onDamagedBuilding(View view) {
        startActivity(new Intent(this, MapsActivity.class).putExtra("Category", "Damaged Building"));
    }

    public void onFallenTree(View view) {
        startActivity(new Intent(this, MapsActivity.class).putExtra("Category", "Fallen Tree"));
    }

    public void onOther(View view) {
        startActivity(new Intent(this, MapsActivity.class).putExtra("Category", "Other"));
    }
}