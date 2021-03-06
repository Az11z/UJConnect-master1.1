package com.example.customer.ujconnect;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserSettingsActivity extends AppCompatActivity {

    TextView userName ;
    TextView phoneNmuber;
    TextView email;

    FirebaseAuth firebaseAuth;
    String userId;
    TextView textViewName;
    TextView textViewPhone;
    TextView textViewEmail;
    FirebaseDatabase database;
    DatabaseReference myRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

         userName = findViewById(R.id.user_name_settings);
         phoneNmuber = findViewById(R.id.user_phone_number_settings);
         email = findViewById(R.id.email_user_settings);


        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String firebaseUserUid = firebaseUser.getUid();

        //find current user uid
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUserUid);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userName.setText(dataSnapshot.child("name").getValue(String.class));
                phoneNmuber.setText(dataSnapshot.child("phoneNumber").getValue(String.class));
                email.setText(dataSnapshot.child("email").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        userId = firebaseAuth.getInstance().getCurrentUser().getUid();
        textViewName = findViewById(R.id.user_name_settings);
        textViewPhone = findViewById(R.id.user_phone_number_settings);
        textViewEmail = findViewById(R.id.email_user_settings);
        ImageView Close = findViewById(R.id.setting_exit);

        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    //here is your every post
                    String key = snapshot.getKey();
                    String userId1 = String.valueOf(dataSnapshot.child(key).child("firebase_id").getValue());



                    if (userId1.equals(userId)){
                        //name
                        String username = String.valueOf(dataSnapshot.child(key).child("name").getValue());
                        textViewName.setHint(username);

                        //PhoneNumber
                        String PhoneNumber = String.valueOf(dataSnapshot.child(key).child("phoneNumber").getValue());
                        textViewPhone.setHint(PhoneNumber);

                        //Email
                        String Email = String.valueOf(dataSnapshot.child(key).child("email").getValue());
                        textViewEmail.setHint(Email);



                    }

                    Log.d("KEY HERE", key);
                    Log.d("VALUE HERE", userId);
                    Log.d("VALUE FIREBASE", userId1);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
