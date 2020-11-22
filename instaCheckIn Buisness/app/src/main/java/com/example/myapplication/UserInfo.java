package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;

import java.io.IOException;

public class UserInfo extends AppCompatActivity {
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        TextView fnameedit  = findViewById(R.id.fnameedit);
        TextView lnameedit = findViewById(R.id.lnameedit);
        TextView email3 = findViewById(R.id.emailedit);
        TextView phonenumber3 = findViewById(R.id.phoneedit);
        TextView address3 = findViewById(R.id.addressedit);
        TextView checkin3 = findViewById(R.id.checkinDate);
        TextView checkout3 = findViewById(R.id.checkoutdate);
        TextView gender3 = findViewById(R.id.genderedit);
        TextView adults   = findViewById(R.id.adults);
        TextView childrens   = findViewById(R.id.childrens);
        Button verify =  findViewById(R.id.verify);


       // TextView total3 = findViewById(R.id.total3);
       ImageView id3 = findViewById(R.id.id3);
        ImageView sign3 = findViewById(R.id.sign3);
        //getting info
        Log.d("name user",getIntent().getStringExtra("fname"));

        fnameedit.setText(getIntent().getStringExtra("fname"));
        lnameedit.setText(getIntent().getStringExtra("lname"));
        email3.setText(getIntent().getStringExtra("email"));
        phonenumber3.setText(getIntent().getStringExtra("phonenumber3"));
        address3.setText(getIntent().getStringExtra("address3"));
        checkin3.setText(getIntent().getStringExtra("checkin"));
        checkout3.setText(getIntent().getStringExtra("checkout"));
        gender3.setText(getIntent().getStringExtra("gender"));
        adults.setText(getIntent().getStringExtra("adults"));
        childrens.setText(getIntent().getStringExtra("childrens"));

      Glide.with(this)
                .asBitmap()
                .load( getIntent().getStringExtra("idurl"))
                .into(id3);

      Glide.with(this)
                .asBitmap()
                .load(getIntent().getStringExtra("sigurl"))
                .into(sign3);

      verify.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              int index = RecyclerViewAdapter.Key;
              ref= FirebaseDatabase.getInstance().getReference().child("submissionForm").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(LoginActivity.data_pos.get(index));
              ref.removeValue();
              String val = LoginActivity.ub.get(index).getToken();
              new asyntask().execute(val);
              startActivity(new Intent(UserInfo.this ,  LoginActivity.class));
              finish();
          }
      });
    }

    class asyntask extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... strings) {
            try {
                new sendNoti().send_postRequest(strings[0]);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }



    }








