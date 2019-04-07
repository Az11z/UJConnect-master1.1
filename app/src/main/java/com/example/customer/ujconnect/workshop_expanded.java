package com.example.customer.ujconnect;



import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;


import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;




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
    ImageView c_image;
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
                c_image     = findViewById(R.id.cer_image);


        workShopDetails = getIntent().getParcelableExtra("department");
        if(workShopDetails!=null){

                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        context = this;
                if(HomeScreen.admin){
                    imageView.setImageResource(R.drawable.ic_register_button);
                    FirebaseDatabase.getInstance().getReference("Workshops").child("department").child(workShopDetails.getFirebase_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String depart = dataSnapshot.child("department").getValue(String.class);
                        if(workShopDetails.getDepartment().equals(depart)){
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(context,AttendenceActivity.class);
                                    intent.putExtra("workshop",workShopDetails);
                                    startActivity(intent);
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });
                    imageView.setImageResource(R.drawable.ic_register_button);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "This is not your department", Toast.LENGTH_SHORT).show();
                        }
                    });



                }
                else {


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
                                            if (dataSnapshot.child("attendence").child(firebaseUser.getUid()).exists()) {
                                                imageView.setImageResource(R.drawable.ic_register_button);

                                                FirebaseDatabase.getInstance().getReference("Workshops").child("department").child(workShopDetails.getFirebase_id()).child("attendence").child(firebaseUser.getUid()).removeValue();
                                                imageView.animate().alpha(1).setDuration(500).start();
                                            } else {

                                                imageView.setImageResource(R.drawable.ic_registerd);
                                                FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@")[0]).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        User user = dataSnapshot.getValue(User.class);
                                                        System.out.println(user.getEmail());
                                                        FirebaseDatabase.getInstance().getReference("Workshops").child("department").child(workShopDetails.getFirebase_id()).child("attendence").child(firebaseUser.getUid()).setValue(user);

                                                        imageView.animate().alpha(1).setDuration(500).start();
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });

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

                }


        ImageView workshop_close = findViewById(R.id.Workshop_close);

        workshop_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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



//        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@")[0]).child("Certificates").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//                    String key = dataSnapshot1.getKey();
//                    System.out.println(key);
//                    System.out.println(workShopDetails.getFirebase_id());
//                    if(key.equals(workShopDetails.getFirebase_id())){
//                        c_image.setImageResource(R.drawable.ic_active_award);
//                        c_image.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                System.out.println(FirebaseStorage.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@")[0]).child(workShopDetails.getTitle()+".pdf").getDownloadUrl());
//
//                            }
//                        });
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });




       }
    }

}

