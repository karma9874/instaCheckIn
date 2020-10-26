package com.application.customerapp.utils;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission_group.CAMERA;

public class scannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{


    ZXingScannerView scannerView;
    List<String> datalist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        scannerView = (ZXingScannerView) findViewById(R.id.scannerview);

        datalist = new ArrayList();
        FirebaseDatabase.getInstance().getReference("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child : snapshot.getChildren()){
                    //Log.d("adad",child.getKey());
                    datalist.add(child.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void handleResult(Result result) {
        Log.d("QRCodeScanner", result.getText());
        if(datalist.contains(result.getText())){
            scannerView.stopCamera();
            Intent intent = new Intent(getApplicationContext(),registrationForm.class);
            intent.putExtra("childData",result.getText());
            startActivity(intent);
        }else{
            Toast.makeText(this, "Invalid QR Code", Toast.LENGTH_SHORT).show();
            scannerView.resumeCameraPreview(this);
        }
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