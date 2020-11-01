package com.application.customerapp.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.application.customerapp.MainActivity;
import com.application.customerapp.R;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class signaturePad extends AppCompatActivity {

    SignaturePad signaturePad;
    Button mClearButton,mSaveButton;
    static String sigImage="";

    String fname,lname,address,phone,email,checkin,checkout,gender,imageURL,counter1,counter2,childData,passport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_pad);

        signaturePad = (SignaturePad) findViewById(R.id.signature_pad);

         fname = getIntent().getStringExtra("fname");
         lname = getIntent().getStringExtra("lname");
         address = getIntent().getStringExtra("address");
         phone = getIntent().getStringExtra("phone");
         email = getIntent().getStringExtra("email");
         checkin = getIntent().getStringExtra("checkin");
         checkout = getIntent().getStringExtra("checkout");
         gender = getIntent().getStringExtra("gender");
         imageURL = getIntent().getStringExtra("imageURL");
         counter1 = getIntent().getStringExtra("counter1");
         counter2 = getIntent().getStringExtra("counter2");
        childData = getIntent().getStringExtra("childData");
        passport = getIntent().getStringExtra("passport");
        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                //Toast.makeText(getApplicationContext(), "OnStartSigning", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                mSaveButton.setEnabled(true);
                mClearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                mSaveButton.setEnabled(false);
                mClearButton.setEnabled(false);
            }
        });

        mClearButton = (Button) findViewById(R.id.clear_button);
        mSaveButton = (Button) findViewById(R.id.save_button);

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signaturePad.clear();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap signatureBitmap = signaturePad.getSignatureBitmap();

                ProgressDialog pd = new ProgressDialog(signaturePad.this);
                pd.setMessage("Uploading Signature");
                pd.setCancelable(false);
                pd.show();

                if(signatureBitmap!=null){
                    StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("uploads").child(System.currentTimeMillis()+".jpeg");

                    Uri uri = new heplers().getImageUri(getApplicationContext(),signatureBitmap);
                    fileRef.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    sigImage = url;
                                    Log.d("uploaded",url);
                                    pd.dismiss();
                                    Log.d("adad",childData);
                                    DatabaseReference dref = FirebaseDatabase.getInstance().getReference("submissionForm").child(childData);
                                    dref.push().setValue(new regisObj(fname,lname,email,address,gender,phone,counter1,counter2,checkin,checkout,imageURL,sigImage,passport, FirebaseInstanceId.getInstance().getToken()));
                                    new SweetAlertDialog(signaturePad.this,SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Form Submitted, please wait for a notification for your room key !")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);
                                                }
                                            })
                                            .show();
                                }
                            });
                        }
                    });
                    //


                }
            }
        });



    }
}