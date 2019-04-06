package com.example.customer.ujconnect;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

         userName = findViewById(R.id.user_name_settings);
         phoneNmuber = findViewById(R.id.user_phone_number_settings);
         email = findViewById(R.id.email_user_settings);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child("hiWCzzp1RVPobgW55FDuiSw9jgG2");


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




    }
}
