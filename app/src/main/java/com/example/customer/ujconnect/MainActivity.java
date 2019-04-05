package com.example.customer.ujconnect;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.AuthProvider;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<ViewCardObject> t;
    Context context;
    RecyclerView re;
    NewsCardAdapter newsCardAdapter;
    CourseCardAdapter courseCardAdapter;
    FrameLayout frameLayout;
    FirebaseAuth firebaseAuth;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();




        context = this;



        Display display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);


        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Tweets").child("department").child("CCIT");

        t = new ArrayList<>();

        for(int i=0;i<10;i++){
            t.add(new ViewCardObject());
        }

        re = findViewById(R.id.rec);
        re.setLayoutManager(new LinearLayoutManager(this));



        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getLayoutParams().width = size.x;

        ImageView imageView=  findViewById(R.id.close_icon);



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });


        ImageView drop_down = findViewById(R.id.blue_arrow_drop_down);

        frameLayout = findViewById(R.id.user_account_frame_layout);

        drop_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout.setVisibility(View.VISIBLE);
            }
        });

        ImageView drop_down_blue = findViewById(R.id.drop_down_icon_blue);
        drop_down_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout.setVisibility(View.GONE);
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










        // MY Menu Start from here , got it :)
        ImageView DepartmentIcon = findViewById(R.id.DepartmentIcon);
        DepartmentIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,DepartmentsListActivity.class));
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        ImageView ujlogo =  findViewById(R.id.ujlogo);
        ujlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,MainActivity.class));
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        ImageView workshop = findViewById(R.id.workshops_menu_icon);
        workshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Course> courses = new ArrayList<>();
                for(int i=0;i<20;i++){
                    courses.add(new Course());
                }
                courseCardAdapter = new CourseCardAdapter(re.getContext(),courses);
                re.setAdapter(courseCardAdapter);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        ImageView ujMenuIcon = findViewById(R.id.uj_menu_icon);
        ujMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        ImageView newsMenuIcon = findViewById(R.id.news_menu_icon);
        newsMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                re.setAdapter(newsCardAdapter);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        //My Menu End here , now i get it :)


        Intent intent = getIntent();

        String x = intent.getStringExtra("type");




        if(x!=null && x.equals("courses")){
            ArrayList<Course> courses = new ArrayList<>();
            for(int i=0;i<20;i++){
                courses.add(new Course());
            }
            courseCardAdapter = new CourseCardAdapter(re.getContext(),courses);
            re.setAdapter(courseCardAdapter);
        }

        else{

            newsCardAdapter = new NewsCardAdapter(this,t);
            re.setAdapter(newsCardAdapter);

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        ViewCardObject x = snapshot.getValue(ViewCardObject.class);
                        t.add(x);
                        newsCardAdapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }

            });

        }







    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
            return;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;



    }


}
