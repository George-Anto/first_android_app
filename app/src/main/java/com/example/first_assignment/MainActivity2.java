package com.example.first_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity2 extends AppCompatActivity {
    EditText writeDataText;
    TextView viewDataText;
    FirebaseDatabase database;
    DatabaseReference myRef1;
    DatabaseReference myRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        writeDataText = findViewById(R.id.dataToWriteText);
        viewDataText = findViewById(R.id.dataText);

        database = FirebaseDatabase.getInstance();
        myRef1 = database.getReference("singleMessage");
        myRef2 = database.getReference("messageList");
        //Listener that listens for changes to the db ref
//        myRef1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                viewDataText.setText(snapshot.getValue().toString());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    public void onWriteData(View view) {
        if (writeDataText != null) {
            myRef2.push().setValue(writeDataText.getText().toString());
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
        }

    }

    public void onReadData(View view) {
        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                viewDataText.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}