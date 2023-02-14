package com.example.drowsinessdetection;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class LoginActivity extends AppCompatActivity {

    boolean userbool=false;
    boolean platebool=false;
    ImageView userImageView;
    ImageView plateImageView;


    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userImageView = findViewById(R.id.user_imageView);
        plateImageView = findViewById(R.id.plate_imageView);
        final Button userBtn = findViewById(R.id.userBtn);
        final Button plateBtn = findViewById(R.id.plateBtn);
        final Button uplBtn = findViewById(R.id.uplBtn);

        //mStorageRef = FirebaseStorage.getInstance().getReference();


        userBtn.setOnClickListener(new View.OnClickListener() {
        @Override

            public void onClick(View view) {
                userbool=true;
                Toast.makeText(LoginActivity.this, "Camera btn is clicked", Toast.LENGTH_SHORT).show();
                askcameraPermissions();
                Log.d(TAG,"ASKCAMERAPERMISSIONS");
           }
        });

        plateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                platebool=true;
                Toast.makeText(LoginActivity.this, "plate btn is clicked", Toast.LENGTH_SHORT).show();
                askcameraPermissions();
            }
        });
    }

    private void askcameraPermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG,"PERMISSION_GRANTED");
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }else{
            openCamera();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERM_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d(TAG,"CHECK FOR PERMISSION");
                openCamera();
            }else{
                Toast.makeText(this, "Camera Permission is Required to use camera", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST_CODE);
        Log.d(TAG,"ACTIVITY RESULT LUNCH");
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            if(userbool){
                userImageView.setImageBitmap(bitmap);
                userbool=false;
            }
            else{
                plateImageView.setImageBitmap(bitmap);
            }
        }
    }
}