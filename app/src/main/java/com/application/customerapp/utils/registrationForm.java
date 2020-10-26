package com.application.customerapp.utils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.application.customerapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registrationForm extends AppCompatActivity {


    Button submit,uploadbutton;
    EditText fname,lname,email,address,phone;
    RadioGroup sex;
    TextView adults,child;
    static int counter1 = 0;
    static int counter2 = 0;
    Activity activity = this;

    private static final int CAMERA_REQUEST = 420;

    ImageButton plus1,plus2,minus1,minus2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

        adults = findViewById(R.id.counter1);
        child = findViewById(R.id.counter2);

        submit = findViewById(R.id.savebutton);
        fname = findViewById(R.id.fnameedit);
        lname = findViewById(R.id.lnameedit);
        address = findViewById(R.id.addressedit);
        phone = findViewById(R.id.phoneedit);
        email = findViewById(R.id.emailedit);
        sex = findViewById(R.id.genderRadioGroup);

        plus1 = findViewById(R.id.plus1);
        plus2 = findViewById(R.id.plus2);
        minus1 = findViewById(R.id.minus1);
        minus2 = findViewById(R.id.minus2);

        uploadbutton = findViewById(R.id.uploadbutton);

        uploadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verfiycameraperms()){
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter1=counter1-1;
                adults.setText(Integer.toString(counter1));
            }
        });

        plus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter2 = counter2-1;
                child.setText(Integer.toString(counter2));
            }
        });

        minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter1 = counter1+1;
                adults.setText(Integer.toString(counter1));
            }
        });

        minus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter2 = counter2+1;
                child.setText(Integer.toString(counter2));
            }
        });

        String childData = getIntent().getStringExtra("childData");
        DatabaseReference dref = FirebaseDatabase.getInstance().getReference("submissionForm").child(childData);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(!fname.getText().toString().isEmpty() && !lname.getText().toString().isEmpty() && !address.getText().toString().isEmpty()
//                && !phone.getText().toString().isEmpty()){

                String fnameString = fname.getText().toString();
                String lnameString = lname.getText().toString();
                String addressString = address.getText().toString();
                String phoneString = phone.getText().toString();
                String emailString = email.getText().toString();

                int selected=sex.getCheckedRadioButtonId();
                RadioButton gender=(RadioButton) findViewById(selected);
                String sexString = gender.getText().toString();

                Log.d("adadad",fnameString+lnameString+addressString+phoneString);


                startActivity(new Intent(getApplicationContext(),signaturePad.class));
                //dref.push().setValue(new regisObj(fnameString,lnameString,emailString,addressString,sexString,phoneString,Integer.toString(counter1),Integer.toString(counter2),"0","0"));


                //Toast.makeText(registrationForm.this, "Submitted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            if(photo!=null){
                Toast.makeText(activity, "noice", Toast.LENGTH_SHORT).show();
            }
            //imageView.setImageBitmap(photo);
        }
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