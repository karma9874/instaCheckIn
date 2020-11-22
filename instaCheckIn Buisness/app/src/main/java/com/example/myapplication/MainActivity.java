 package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

 public class MainActivity extends AppCompatActivity {
     private FirebaseAuth mAuth;
     private EditText email, password;
     private Button login, register, help;
     private ProgressBar progress_login;



     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         //initialising views
         mAuth = FirebaseAuth.getInstance();
         email = findViewById(R.id.username);
         password = findViewById(R.id.password);
         login = findViewById(R.id.login);
         register = findViewById(R.id.register);
         progress_login=findViewById(R.id.progress_login);


         mAuth = FirebaseAuth.getInstance();
         //handling login feature
         login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 progress_login.setVisibility(View.VISIBLE);
                // Toast.makeText(MainActivity.this, "login clicked", Toast.LENGTH_SHORT).show();
                 String email_id = email.getText().toString().trim();
                 String password_id = password.getText().toString().trim();
                 if (checks(email_id, password_id)) {
                     login.setVisibility(View.VISIBLE);
                     mAuth.signInWithEmailAndPassword(email_id, password_id).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if (task.isSuccessful()) {
                            progress_login.setVisibility(View.GONE);
                                 startActivity(new Intent(MainActivity.this, LoginActivity.class));
                             } else {
                            progress_login.setVisibility(View.GONE);
                                 Toast.makeText(MainActivity.this, "login error", Toast.LENGTH_SHORT).show();
                             }
                         }
                     });
                 }
                 else {
                     progress_login.setVisibility(View.GONE);
                     Toast.makeText(MainActivity.this, "Enter valid details", Toast.LENGTH_LONG).show();
                 }


             }
         });


//handling register feature
         register.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
               //  Toast.makeText(MainActivity.this, "register clicked", Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
             }
         });

//handling help


     }


     //checks
     private boolean checks(String email_id, String password_id) {
         if (email_id.isEmpty()) {
             email.setError("Email is Required");
             email.requestFocus();
             return false;
         }
         if (!Patterns.EMAIL_ADDRESS.matcher(email_id).matches()) {
             email.setError("Enter a valid email");
             email.requestFocus();
             return false;

         }
         if (password_id.isEmpty()) {
             password.setError("Enter Password");
             password.requestFocus();
             return false;
         }
         if (password_id.length() < 6) {
             password.setError("Minimum password length is 6");
             password.requestFocus();
             return false;
         }
         return true;

     }
 }