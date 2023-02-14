package com.example.drowsinessdetection;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://driverdrowsinessdetectio-76f85-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.password);
        final Button loginBtn = findViewById(R.id.loginBtn);
        final TextView RegisterBtn = findViewById(R.id.regiesterBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailTxt = email.getText().toString();
                final String passTxt = password.getText().toString();

                if (emailTxt.isEmpty()|| passTxt.isEmpty()){
                    Log.d(TAG,"Hello if email pass testing");
                    Toast.makeText(Login.this,"Please enter your EmailId and Password",Toast.LENGTH_SHORT).show();
                }else {
                    Log.d(TAG,"Hello else email pass testing");
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(emailTxt)){
                                Log.d(TAG,"Hello if child has value");
                               final String getPassword = snapshot.child(emailTxt).child("Password").getValue(String.class);
                                if (getPassword.equals(passTxt)){
                                    Log.d(TAG,"Hello if password matches");
                                    Toast.makeText(Login.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this,LoginActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(Login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });

    }
}