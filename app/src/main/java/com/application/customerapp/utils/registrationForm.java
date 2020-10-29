package com.application.customerapp.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.application.customerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class registrationForm extends AppCompatActivity {


    Button submit,uploadbutton;
    EditText fname,lname,email,address,phone,ci,co,passpoeredit;
    RadioGroup sex;
    TextView adults,child;
    static int counter1 = 0;
    static int counter2 = 0;
    Activity activity = this;
    StorageReference fileRef;
    DatabaseReference dref;
    static String imageURL = "";
    String childData;
    RadioButton r1,r2;
    String sexString;
    ImageView done;
    private static final int CAMERA_REQUEST = 420;

    ImageButton plus1,plus2,minus1,minus2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);
        getWindow().setStatusBarColor(getResources().getColor(R.color.teal_200));
        adults = findViewById(R.id.counter1);
        child = findViewById(R.id.counter2);
        ci = findViewById(R.id.checkinDate);
        co = findViewById(R.id.checkoutdate);
        done = findViewById(R.id.doneupload);
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


        r1 = findViewById(R.id.maleRadioButton);
        r2 = findViewById(R.id.femaleRadioButton);

        uploadbutton = findViewById(R.id.uploadbutton);

        passpoeredit = findViewById(R.id.passpoeredit);

        done.setVisibility(View.INVISIBLE);

        final Calendar myCalendar = Calendar.getInstance();

        ci.setClickable(true);
        co.setClickable(true);
        DatePickerDialog.OnDateSetListener checkin = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(ci,myCalendar);
            }
        };

        DatePickerDialog.OnDateSetListener checkout = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(co,myCalendar);
            }
        };

        ci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(registrationForm.this, checkin, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(registrationForm.this, checkout, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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
                if(counter1>0){
                    counter1=counter1-1;
                }
                adults.setText(Integer.toString(counter1));
            }
        });

        plus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counter2>0){
                    counter2 = counter2-1;
                }
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

         childData = getIntent().getStringExtra("childData");
        dref = FirebaseDatabase.getInstance().getReference("submissionForm").child(childData);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fnameString = fname.getText().toString();
                String lnameString = lname.getText().toString();
                String addressString = address.getText().toString();
                String phoneString = phone.getText().toString();
                String emailString = email.getText().toString();
                String checkinDate = ci.getText().toString();
                String checkoutDate = co.getText().toString();
                Log.e("date", checkinDate);
                Log.e("date", checkoutDate);

                /* dates checker parameter uncomment it !
                String[] cin =  checkinDate.split("/");
                String[] cout =  checkoutDate.split("/");
                boolean dates= false;
                dateCheck(dates,Integer.parseInt(cin[0]),Integer.parseInt(cout[0]),Integer.parseInt(cin[1]),Integer.parseInt(cout[1]),Integer.parseInt(cin[2]),Integer.parseInt(cout[2]));
                if(!dates)
                {
                    ci.setError("enter valid checkin and checkout dates");
                    ci.requestFocus();
                }

                 */



                String passportString = passpoeredit.getText().toString();
                boolean checker = true;
                if(fnameString.isEmpty()){
                    fname.setError("First Name is Required");
                    fname.requestFocus();
                    checker = false;
                }

                if(passportString.isEmpty()){
                    passportString = "null";
                }

                if(lnameString.isEmpty()){
                    lname.setError("Last Name is Required");
                    lname.requestFocus();
                    checker = false;
                }

                if(addressString.isEmpty()){
                    address.setError("Address is Required");
                    address.requestFocus();
                    checker = false;
                }

                if(phoneString.isEmpty() || !Patterns.PHONE.matcher(phoneString).matches()){
                    phone.setError("Phone number is Required");
                    phone.requestFocus();
                    checker = false;
                }

                if(emailString.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailString).matches()){
                    email.setError("Email id is Required");
                    email.requestFocus();
                    checker = false;
                }


                if(r1.isChecked() || r2.isChecked()){
                    int selected =sex.getCheckedRadioButtonId();
                    RadioButton gender=(RadioButton) findViewById(selected);
                    sexString = gender.getText().toString();
                }else{
                    r1.setError("Gender is required");
                    r2.setError("Gender is required");
                    r1.requestFocus();
                    r2.requestFocus();
                }

                if(checker){
                    Intent intent = new Intent(getApplicationContext(),signaturePad.class);
//                    regisObj data = new regisObj(fnameString,lnameString,emailString,addressString,sexString,phoneString,Integer.toString(counter1),Integer.toString(counter2),checkinDate,checkoutDate,imageURL,"0");
                    intent.putExtra("fname",fnameString);
                    intent.putExtra("lname",lnameString);
                    intent.putExtra("address",addressString);
                    intent.putExtra("phone",phoneString);
                    intent.putExtra("email",emailString);
                    intent.putExtra("checkin",checkinDate);
                    intent.putExtra("checkout",checkoutDate);
                    intent.putExtra("gender",sexString);
                    intent.putExtra("imageURL",imageURL);
                    intent.putExtra("counter1",Integer.toString(counter1));
                    intent.putExtra("counter2",Integer.toString(counter2));
                    intent.putExtra("passport",passportString);

  //                  intent.putExtra("class",data);
                    intent.putExtra("childData",childData);
                    startActivity(intent);
                }
//                else{
//                    Toast.makeText(activity, "Some filed is empty", Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }

    /*   dates checker functio
    private  static  boolean dateCheck(boolean result,int d1,int d2, int m1,int m2,int y1,int y2)
    {
        Log.e("date",d1+"") ;
        Log.e("date",d2+"") ;
        Log.e("date",m1+"") ;
        Log.e("date",m2+"") ;
        Log.e("date",y1+"") ;
        Log.e("date",y2+"") ;

        if(y2<y1)
     {
         return false;
     }
     else if(y1<=y2 && m1>m2)
         return false;
     else if(y1<=y2 && m1<=m2 && d1>d2)
         return false;
     return true;
    }

     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            if(photo!=null){

                ProgressDialog pd = new ProgressDialog(this);
                pd.setMessage("Uploading Image");
                pd.show();
                fileRef = FirebaseStorage.getInstance().getReference().child("uploads").child(System.currentTimeMillis()+".jpeg");
                Uri uri = new heplers().getImageUri(getApplicationContext(),photo);
                fileRef.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = uri.toString();
                                imageURL = url;
                                Log.d("uploaded",url);
                                pd.dismiss();
                                done.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
            }
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

    private void updateLabel(EditText ed,Calendar calendar) {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        ed.setText(sdf.format(calendar.getTime()));
    }


}