package com.application.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.application.customerapp.utils.scannerActivity;

public class MainActivity extends AppCompatActivity {

    ImageButton scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(getResources().getColor(R.color.teal_200));
        scanner = findViewById(R.id.qr_scanner);
        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verfiycameraperms()){
                    startActivity(new Intent(getApplicationContext(), scannerActivity.class));
                }
            }
        });


    }
    public  boolean verfiycameraperms() {
        int perms = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (perms != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA},
                    1
            );
            return false;
        }else{
            return true;
        }
    }



}