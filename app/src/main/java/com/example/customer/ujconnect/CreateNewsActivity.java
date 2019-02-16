package com.example.customer.ujconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

    FirebaseDatabase database;
    DatabaseReference myRef;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_news);

         intent  = getIntent();

        WriteNews = findViewById(R.id.WriteNews);
        SubmitNews = findViewById(R.id.SubminNews);
        dep_name = findViewById(R.id.nameD4);
        dep_icon = findViewById(R.id.iconD3);

        dep_icon.setImageResource(R.drawable.fcit_logo);


        dep_name.setText(intent.getStringExtra("dep"));


        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c);




        database = FirebaseDatabase.getInstance();




        SubmitNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef = database.getReference("Tweets").child("department").child(intent.getStringExtra("dep")).child(database.getReference().push().getKey());
                myRef.setValue(new ViewCardObject(WriteNews.getText().toString(),intent.getStringExtra("dep"),formattedDate,"0",3));
                finish();
            }
        });






    }
}
