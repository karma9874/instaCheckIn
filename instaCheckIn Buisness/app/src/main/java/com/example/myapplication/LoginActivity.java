package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    //object class having user info
    static ArrayList<userObj> ub ;
    static ArrayList<String> data_pos;
    RelativeLayout lay;
            //db reference
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        ub = new ArrayList<>();
        Log.d(TAG, "onCreate: started.");
        data_pos = new ArrayList<>();
         lay  =findViewById(R.id.lay);

        ref= FirebaseDatabase.getInstance().getReference().child("submissionForm").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            Log.e("user",FirebaseAuth.getInstance().getCurrentUser().getUid());


        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, ub);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ub.clear();
                for(DataSnapshot d  : snapshot.getChildren())
                {//putting data in  user object class having user info
                  data_pos.add(d.getKey());
                 userObj ub2 = d.getValue(userObj.class);
                 ub.add(new userObj(ub2.getFname(),ub2.getLname(),ub2.getEmail(),ub2.getAddress(),ub2.getGender(),ub2.getPhone(),ub2.getAdults(),ub2.getChildren(),ub2.getCheckin(),ub2.getCheckout(),ub2.getIdurl(),ub2.getSigurl(),ub2.getPassport(),ub2.getToken(),ub2.getTime()));
                    adapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FloatingActionButton fab = findViewById(R.id.QR);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QRCodeButton(FirebaseAuth.getInstance().getCurrentUser().getUid());
            }
        });



        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.qr)
        {

            QRCodeButton(FirebaseAuth.getInstance().getCurrentUser().getUid());
            //startActivity(new Intent(LoginActivity.this,qr.class));
        }
        else if (item.getItemId() == R.id.help)
        {
            Snackbar.make(lay,"contact us - www.apploqic.com", LENGTH_LONG).setAction("submit your query", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("https://forms.gle/X6KLSrnZ15uVxucF6"); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }).show();
        }
        else
        return super.onOptionsItemSelected(item);
        return true;
    }





    public void QRCodeButton(String data){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);
            Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.RGB_565);
            for (int x = 0; x<200; x++){
                for (int y=0; y<200; y++){
                    bitmap.setPixel(x,y,bitMatrix.get(x,y)? Color.BLACK : Color.WHITE);
                }
            }

            Uri uri = getImageUri(getApplicationContext(),bitmap,data);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "image/*");
            startActivity(intent);
            //imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage,String data) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, data, null);
        Toast.makeText(inContext, "Picture saved in "+path, Toast.LENGTH_SHORT).show();
        return Uri.parse(path);
    }

    }


