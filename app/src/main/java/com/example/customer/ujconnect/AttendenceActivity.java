package com.example.customer.ujconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class AttendenceActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AttendenceAdapter attendenceAdapter;
    ArrayList<Attendence> attendenceArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);

        attendenceArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_student);
        attendenceAdapter = new AttendenceAdapter(this, attendenceArrayList);
        recyclerView.setAdapter(attendenceAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        for(int i=0;i<20;i++){
            attendenceArrayList.add(new Attendence());
        }
        attendenceAdapter.notifyDataSetChanged();






    }
}
