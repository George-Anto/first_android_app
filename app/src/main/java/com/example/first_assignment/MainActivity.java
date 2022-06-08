package com.example.first_assignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    EditText emailText, passwordText;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailText = findViewById(R.id.loginEmail);
        passwordText = findViewById(R.id.loginPassword);
        auth = FirebaseAuth.getInstance();
    }

    public void onLogin(View view) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (!(email.matches("") || password.matches(""))) {
            auth.signInWithEmailAndPassword(email, password).
                    addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            userAuthenticated(auth.getUid());
                        } else {
                            try {
                                showMessage("Login Error", Objects.requireNonNull(task.getException()).getLocalizedMessage());
                            } catch (Exception e) {
                                showMessage("Error", "Unknown Error, please try again!");
                            }
                        }
                    });
        } else
            errorToast(email, password);
    }

    public void onSignup(View view) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (!(email.matches("") || password.matches(""))) {
            auth.createUserWithEmailAndPassword(email, password).
                    addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            userAuthenticated(auth.getUid());
                        } else {
                            try {
                                showMessage("Sign Up Error", Objects.requireNonNull(task.getException()).getLocalizedMessage());
                            } catch (Exception e) {
                                showMessage("Error", "Unknown Error, please try again!");
                            }
                        }
                    });
        } else
            errorToast(email, password);
    }

    private void showMessage(String title, String message){
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .show();
    }

    private void errorToast(String email, String password) {
        if (email.matches("") && password.matches(""))
            Toast.makeText(this, "Please provide your Email and Password.", Toast.LENGTH_LONG).show();
        else if (email.matches(""))
            Toast.makeText(this, "Please provide your Email as well.", Toast.LENGTH_LONG).show();
        else if (password.matches(""))
            Toast.makeText(this, "Please provide your Password as well.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Unknown Error\nPlease try again.", Toast.LENGTH_LONG).show();
    }

    private void userAuthenticated(String uid) {
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("Uid", uid);
        startActivity(intent);
    }
}