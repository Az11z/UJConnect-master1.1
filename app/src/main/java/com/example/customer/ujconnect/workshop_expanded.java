package com.example.customer.ujconnect;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class workshop_expanded extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_expanded);
        final ImageView imageView = findViewById(R.id.add_activity_icon);
        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                //imageView.animate().alpha(0).setDuration(3000).start();



                imageView.animate().withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(R.drawable.ic_check_green_24dp);

                        imageView.animate().alpha(1).setDuration(1000).start();
                    }
                }).alpha(0).setDuration(1000).start();


                final ImageView CloseB = findViewById(R.id.Settings_close);

            }
        });




    }
}
