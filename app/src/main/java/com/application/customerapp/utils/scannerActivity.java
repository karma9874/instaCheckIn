package com.application.customerapp.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.application.customerapp.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission_group.CAMERA;

public class scannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{


    ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        scannerView = (ZXingScannerView) findViewById(R.id.scannerview);
    }

    @Override
    public void handleResult(Result result) {
        Log.d("QRCodeScanner", result.getText());
        Toast.makeText(this, result.getText(), Toast.LENGTH_SHORT).show();
        if(result.getText().equals("data")){
            scannerView.stopCamera();
            startActivity(new Intent(getApplicationContext(),registrationForm.class));
        }else{
            scannerView.resumeCameraPreview(this);
        }
//        scannerView.resumeCameraPreview(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();

            if (scannerView == null) {
                scannerView = (ZXingScannerView) findViewById(R.id.scannerview);
                setContentView(R.layout.activity_scanner);
            }
            scannerView.setResultHandler(this);
            scannerView.startCamera();

    }



}