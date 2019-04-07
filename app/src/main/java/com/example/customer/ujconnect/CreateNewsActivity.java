package com.example.customer.ujconnect;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateNewsActivity extends AppCompatActivity {

    EditText WriteNews;
    Button SubmitNews;
    TextView dep_name;
    ImageView dep_icon;
    String formattedDate;
    Context context;

    FirebaseDatabase database;
    DatabaseReference myRef;
    Intent intent;
    FrameLayout frameLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_news);
        context = this;
         intent  = getIntent();

        WriteNews = findViewById(R.id.WriteNews);
        if(WriteNews.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        SubmitNews = findViewById(R.id.SubminNews);
        dep_name = findViewById(R.id.nameD4);
        dep_icon = findViewById(R.id.iconD3);

        dep_icon.setImageResource(R.drawable.fcit_logo);


        dep_name.setText(intent.getStringExtra("dep"));


        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("HH:SS dd-MMM-yyyy");
        formattedDate = df.format(c);




        database = FirebaseDatabase.getInstance();




        SubmitNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef = database.getReference("Tweets").child("department").child("test").child(database.getReference().push().getKey());
                myRef.setValue(new ViewCardObject(WriteNews.getText().toString(),"test",formattedDate,"0",3));
                finish();
            }
        });


        ImageView drop_down = findViewById(R.id.blue_arrow_drop_down);

        frameLayout = findViewById(R.id.view_account);

        drop_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout.animate().alpha(1).setDuration(1000).withStartAction(new Runnable() {
                    @Override
                    public void run() {
                        frameLayout.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        ImageView drop_down_blue = findViewById(R.id.drop_down_icon_blue);
        drop_down_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout.animate().alpha(0).setDuration(1000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        frameLayout.setVisibility(View.GONE);
                    }
                });
            }
        });

        //profile items VVVVVVVV

        TextView profile = findViewById(R.id.profile_menu_item);
        TextView accountSettings = findViewById(R.id.account_settings_menu_item);
        TextView logout = findViewById(R.id.log_out_menu_item);


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,UserProfileActivity.class));
                frameLayout.setVisibility(View.GONE);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance().signOut(context).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(context,HomeScreen.class));
                        finish();
                    }
                });
            }

        });
        accountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,UserSettingsActivity.class));
            }
        });
        //end of profile items ^^^^^^







    }
}
