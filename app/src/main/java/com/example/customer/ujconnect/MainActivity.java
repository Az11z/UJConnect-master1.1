package com.example.customer.ujconnect;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.firebase.database.ChildEventListener;
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
    ArrayList<WorkShopDetails> workShopDetails;
    Context context;
    RecyclerView re;
    NewsCardAdapter newsCardAdapter;
    WorkshopsAdapter workshopsAdapter;
    FrameLayout frameLayout;
    FirebaseAuth firebaseAuth;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();




        context = this;



        Display display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getLayoutParams().width = size.x;



        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        t = new ArrayList<>();
        re = findViewById(R.id.rec);
        re.setLayoutManager(new LinearLayoutManager(this));
        newsCardAdapter = new NewsCardAdapter(this,t);
        re.setAdapter(newsCardAdapter);
        workShopDetails = new ArrayList<>();
        workshopsAdapter = new WorkshopsAdapter(this,workShopDetails);




        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Tweets").child("department");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot1, @Nullable String s) {
                    for(DataSnapshot snapshot : snapshot1.getChildren()) {
                        ViewCardObject viewCardObject = snapshot.getValue(ViewCardObject.class);
                        t.add(viewCardObject);
                        newsCardAdapter.notifyDataSetChanged();
                    }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef = database.getReference("Workshops").child("department");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String s) {
                    WorkShopDetails workShopDetails1 = snapshot.getValue(WorkShopDetails.class);
                System.out.println(workShopDetails1.getTitle());
                    workShopDetails.add(workShopDetails1);
                    workshopsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






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
                re.setAdapter(workshopsAdapter);
                workshopsAdapter.notifyDataSetChanged();
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












    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;



    }


}
