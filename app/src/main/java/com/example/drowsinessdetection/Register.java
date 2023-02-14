package com.example.drowsinessdetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class Register extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://driverdrowsinessdetectio-76f85-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText fullname = findViewById(R.id.fullname);
        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.password);
        final EditText conpassword = findViewById(R.id.conPassword);
        final Button registerBtn = findViewById(R.id.regisBtn);
        final TextView logintxt = findViewById(R.id.loginNow);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullnameTxt = fullname.getText().toString();
                final String emailTxt = email.getText().toString();
                final String passwordtxt = password.getText().toString();
                final String conpasswordtxt = conpassword.getText().toString();

                if (fullnameTxt.isEmpty() || emailTxt.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches() || passwordtxt.isEmpty()){
                    Toast.makeText(Register.this,"Please fill all fields",Toast.LENGTH_SHORT).show();
                } else if (!passwordtxt.equals(conpasswordtxt)) {
                    Toast.makeText(Register.this,"Passwords are not matching",Toast.LENGTH_SHORT).show();
                } else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(emailTxt)){
                                Toast.makeText(Register.this, "Email Id is already registered", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                databaseReference.child("users").child(emailTxt).child("fullname").setValue(fullnameTxt);
                                databaseReference.child("users").child(emailTxt).child("email").setValue(emailTxt);
                                databaseReference.child("users").child(emailTxt).child("Password").setValue(passwordtxt);

                                Toast.makeText(Register.this, "User regiesterd successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

            }
        });

        logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}