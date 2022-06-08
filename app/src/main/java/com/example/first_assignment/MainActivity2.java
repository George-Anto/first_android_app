package com.example.first_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity2 extends AppCompatActivity {
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        uid = getIntent().getStringExtra("Uid");
    }

    public void  onNewRequest(View view) {
        startActivity(new Intent(this, MainActivity3.class).putExtra("Uid", uid));
    }

    public void onViewMyRequests(View view) {
        startActivity(new Intent(this, MainActivity4.class).putExtra("Uid", uid));
    }

    public void onViewOnMap(View view) {
        startActivity(new Intent(this, MainActivity5.class));
    }

}