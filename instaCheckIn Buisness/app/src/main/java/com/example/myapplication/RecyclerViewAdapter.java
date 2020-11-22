package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<userObj> userObject = new ArrayList<>();

    private Context mContext;
    private DatabaseReference ref;
    static int Key;

    public RecyclerViewAdapter(Context context, ArrayList<userObj> obj ) {
      //  mImageNames = imageNames;
       // mImages = images;
        this.userObject=obj;
        mContext = context;
       // ref = FirebaseDatabase.getInstance().getReference().child("submissionForm").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {

        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(userObject.get(position).getIdurl())
                .into(holder.image);

        String first = userObject.get(position).getFname() + " " + userObject.get(position).getLname();
        holder.imageName.setText(first);
        holder.email_name.setText(userObject.get(position).getEmail());
       Log.e("date",userObject.get(position).getTime());
       String d = userObject.get(position).getTime();
       long currentDateTime = Long.parseLong(d);

        Date currentDate = new Date(currentDateTime);

        DateFormat df = new SimpleDateFormat("HH:mm");

        holder.date.setText(df.format(currentDate).toString());





        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: clicked on: " + userObject.get(position));
              //  Toast.makeText(mContext, userObject.get(position), Toast.LENGTH_SHORT).show();

                Key=position;
                //logging data
                Log.e("rv","data sent" );
                Log.e("rv", userObject.get(position).getFname().toString());
                Log.e("rv", userObject.get(position).getLname().toString());
                Log.e("rv", userObject.get(position).getEmail().toString());
                Log.e("rv",userObject.get(position).getIdurl().toString());
                Log.e("rv", userObject.get(position).getSigurl().toString());
                Log.e("rv", userObject.get(position).getPhone().toString());
                Log.e("rv", userObject.get(position).getAddress().toString());
                Log.e("rv", userObject.get(position).getCheckin().toString());
                Log.e("rv", userObject.get(position).getCheckout().toString());
                Log.e("rv", userObject.get(position).getChildren().toString());
                Log.e("rv", userObject.get(position).getAdults().toString());



                //passing intents with information
                Intent i  = new Intent(mContext,UserInfo.class);
                i.putExtra("fname",userObject.get(position).getFname().toString());
                i.putExtra("lname",userObject.get(position).getLname().toString());
                i.putExtra("email",userObject.get(position).getEmail().toString());
                i.putExtra("idurl",userObject.get(position).getIdurl().toString());
                i.putExtra("sigurl",userObject.get(position).getSigurl().toString());
                i.putExtra("phonenumber3",userObject.get(position).getPhone().toString());
                i.putExtra("address3",userObject.get(position).getAddress().toString());
                i.putExtra("checkin",userObject.get(position).getCheckin().toString());
                i.putExtra("checkout",userObject.get(position).getCheckout().toString());
                i.putExtra("gender",userObject.get(position).getGender().toString());
                i.putExtra("adults",userObject.get(position).getAdults().toString());
                i.putExtra("childrens",userObject.get(position).getChildren().toString());

                //start the sctivity
                mContext.startActivity(i);



            }
        });
    }

    @Override
    public int getItemCount() {
        return userObject.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

       CircleImageView image;
        TextView imageName,email_name,last_name,date;
        CardView parentLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            date=  itemView.findViewById(R.id.date);
            imageName = itemView.findViewById(R.id.image_name);
            email_name   = itemView.findViewById(R.id.email_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            parentLayout.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

        }
    }
}

