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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText  hotel_email,hotel_name,hotel_password,address;
    private Button hotel_register;
    private ProgressBar reg_bar ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mAuth = FirebaseAuth.getInstance();
        //initialising vars
        hotel_email=findViewById(R.id.hotel_email);
        hotel_name  = findViewById(R.id.hotel_name);
        hotel_password = findViewById(R.id.hotel_password);
        address = findViewById(R.id.address);
        hotel_register=findViewById(R.id.register_hotel);
        reg_bar = findViewById(R.id.reg_bar);


        hotel_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(RegisterActivity.this,"register clicked",Toast.LENGTH_SHORT).show();
                String hotel_mail = hotel_email.getText().toString().trim();
                String hotel_nametext = hotel_name.getText().toString().trim();
                String pass = hotel_password.getText().toString().trim();
                String hotel_address = address.getText().toString().trim();
                reg_bar.setVisibility(View.VISIBLE);

                //checks
                if (hotel_nametext.isEmpty()) {
                    hotel_name.setError("Name is required");
                    hotel_name.requestFocus();
                }

                if (hotel_mail.isEmpty()) {
                    hotel_email.setError("Email is Required");
                    hotel_email.requestFocus();
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(hotel_mail).matches()) {
                    hotel_email.setError("Enter a valid email");
                    hotel_email.requestFocus();
                }
                if (pass.isEmpty()) {
                    hotel_password.setError("Enter Password");
                    hotel_password.requestFocus();

                }
                if (pass.length() < 6) {
                    hotel_password.setError("Minimum password length is 6");
                    hotel_password.requestFocus();
                }
                boolean checker = (!hotel_nametext.isEmpty() && !hotel_mail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(hotel_mail).matches() && !pass.isEmpty() && pass.length() >= 6);
                Log.e("reg", checker + "");
                if (checker) {
                    mAuth.createUserWithEmailAndPassword(hotel_mail, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        User user = new User(hotel_nametext, hotel_mail, hotel_address);
                                        FirebaseDatabase.getInstance().getReference("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(RegisterActivity.this, "Hotel Registered, you can login now", Toast.LENGTH_SHORT).show();
                                                    reg_bar.setVisibility(View.GONE);
                                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));

                                                } else {
                                                    Toast.makeText(RegisterActivity.this, "error,try again", Toast.LENGTH_SHORT).show();
                                                    reg_bar.setVisibility(View.GONE);
                                                }
                                            }
                                        });

                                    } else {
                                        Toast.makeText(RegisterActivity.this, "error", Toast.LENGTH_SHORT).show();
                                        reg_bar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "enter valid details", Toast.LENGTH_SHORT).show();
                    reg_bar.setVisibility(View.GONE);
                }
            }
        });




    }
}
