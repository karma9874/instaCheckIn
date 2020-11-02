package com.application.customerapp.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.customerapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class scannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{


    ZXingScannerView scannerView;
    List<String> datalist;
    Button yes,no;
    LinearLayout content,ob;
    ImageView imageView;
    Animation fromback,fromnothing;
    TextView text,hotelname;
    ArrayList<hotelObj> obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        getWindow().setStatusBarColor(getResources().getColor(R.color.teal_200));
        scannerView = (ZXingScannerView) findViewById(R.id.scannerview);
        datalist = new ArrayList();

        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
        content = findViewById(R.id.content);
        ob = findViewById(R.id.ob);
        imageView = findViewById(R.id.logocontent);
        text = findViewById(R.id.text);
        hotelname  = findViewById(R.id.hotelname);
        obj = new ArrayList<>();
        content.setVisibility(View.INVISIBLE);
        ob.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.INVISIBLE);

        FirebaseDatabase.getInstance().getReference("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child : snapshot.getChildren()){
                    datalist.add(child.getKey());
                    hotelObj ub2 = child.getValue(hotelObj.class);
                    obj.add(ub2);
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
            int index = datalist.indexOf(result.getText());
            String hotelName = obj.get(index).getFullname();
            hotelname.setText(hotelName+" Hotel");
            scannerView.resumeCameraPreview(this);
            text.setAlpha(0);
            imageView.setVisibility(View.VISIBLE);
            ob.setVisibility(View.VISIBLE);
            content.setVisibility(View.VISIBLE);
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),registrationForm.class);
                    intent.putExtra("childData",result.getText());
                    startActivity(intent);
                }
            });
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    content.setVisibility(View.INVISIBLE);
                    ob.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                }
            });
        }else{
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