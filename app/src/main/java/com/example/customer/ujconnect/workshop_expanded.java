package com.example.customer.ujconnect;



import android.content.Context;

import android.os.Build;


import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class workshop_expanded extends AppCompatActivity {
    Context context;

    WorkShopDetails workShopDetails;
    ImageView imageView;
    ImageView workshop_background;
    TextView title;
    TextView post_date;
    TextView description;
    TextView instructor;
    TextView date_icon;
    TextView time_icon;
    TextView location;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_expanded);
        imageView = findViewById(R.id.add_activity_icon);
        workshop_background = findViewById(R.id.workshop_background);
                      title = findViewById(R.id.workshop_post_title);
                  post_date = findViewById(R.id.workshop_post_date);
                description = findViewById(R.id.workshop_Description);
                instructor  = findViewById(R.id.workshop_presenter_text);
                date_icon   = findViewById(R.id.workshop_date_text);
                time_icon   = findViewById(R.id.workshop_time_text);
                location    = findViewById(R.id.workshop_location_text);


                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();





        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                imageView.animate().withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        FirebaseDatabase.getInstance().getReference("Workshops").child("department").child(workShopDetails.getFirebase_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.child("attendence").child(firebaseUser.getUid()).exists()){
                                    imageView.setImageResource(R.drawable.ic_register_button);

                                    FirebaseDatabase.getInstance().getReference("Workshops").child("department").child(workShopDetails.getFirebase_id()).child("attendence").child(firebaseUser.getUid()).removeValue();
                                    imageView.animate().alpha(1).setDuration(500).start();
                                }
                                else{

                                    imageView.setImageResource(R.drawable.ic_registerd);
                                    User user = new User();
                                    user.setFirebase_id(firebaseUser.getUid());
                                    FirebaseDatabase.getInstance().getReference("Workshops").child("department").child(workShopDetails.getFirebase_id()).child("attendence").child(firebaseUser.getUid()).setValue(user);

                                    imageView.animate().alpha(1).setDuration(500).start();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }).alpha(0).setDuration(500).start();
            }
        });


        context = this;

        ImageView workshop_close = findViewById(R.id.Workshop_close);

        workshop_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        workShopDetails = getIntent().getParcelableExtra("department");

        FirebaseDatabase.getInstance().getReference("Workshops").child("department").child(workShopDetails.getFirebase_id()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("attendence").child(firebaseUser.getUid()).exists()){
                    imageView.setImageResource(R.drawable.ic_registerd);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Glide.with(context).load(workShopDetails.getImage()).into(workshop_background);
        title.setText(workShopDetails.getTitle());
        post_date.setText("Posted "+workShopDetails.getDate());
        description.setText(workShopDetails.getDescription());
        instructor.setText(workShopDetails.getInstructor());
        date_icon.setText(workShopDetails.getDate());
        time_icon.setText(workShopDetails.getTime());
        location.setText(workShopDetails.getLocation());

    }


}

