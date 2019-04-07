package com.example.customer.ujconnect;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DepartmentPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<ViewCardObject> t;
    NewsCardAdapter newsCardAdapter;
    FrameLayout frameLayout;

    FirebaseAuth firebaseAuth;
    String userId;
    TextView textView;

    ImageView add_button;
    ImageView add_news ;
    ImageView add_event ;
    ImageView add_workshops;
    Context context;





    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_department_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        context = this;
        final Intent intent  = getIntent();



        Display display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

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




        database = FirebaseDatabase.getInstance();

//        myRef = database.getReference("Tweets").child("department").child(intent.getStringExtra("dep"));

        t = new ArrayList<>();


        RecyclerView re = findViewById(R.id.dep_cardview);
        newsCardAdapter = new NewsCardAdapter(this,t);
        re.setLayoutManager(new LinearLayoutManager(this));
        re.setAdapter(newsCardAdapter);




//        myRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                    ViewCardObject x = dataSnapshot.getValue(ViewCardObject.class);
//                    t.add(0,x);
//                    newsCardAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        ImageView drop_down = findViewById(R.id.blue_arrow_drop_down);

        frameLayout = findViewById(R.id.view_account);

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
                Intent intent = new Intent(context,MainActivity.class);
                intent.putExtra("type","courses");
                context.startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        ImageView news = findViewById(R.id.news_menu_icon);
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MainActivity.class);
                context.startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        //My Menu End here , now i get it :)


        FloatingActionButton floatingActionButton = findViewById(R.id.follow_icon);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context,CreateNewsActivity.class);
                intent1.putExtra("dep",intent.getStringExtra("dep"));
                intent1.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(intent1);
            }
        });




        userId = firebaseAuth.getInstance().getCurrentUser().getUid();
        textView = findViewById(R.id.user_profile_name);
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
                        String username = String.valueOf(dataSnapshot.child(key).child("name").getValue());
                        textView.setText(username);

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

         add_button = findViewById(R.id.add_button);
         add_news = findViewById(R.id.add_news_button);
         add_event = findViewById(R.id.add_event_button);
         add_workshops = findViewById(R.id.add_workshops_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    add_news.animate().translationX(-250).start();
                    add_event.animate().translationX(-500).start();
                    add_workshops.animate().translationX(-750).start();
                    add_button.setImageResource(R.drawable.ic_cancel_button);
                    count++;
                }
                else{
                    add_news.animate().translationX(0).start();
                    add_event.animate().translationX(0).start();
                    add_workshops.animate().translationX(0).start();
                    add_button.setImageResource(R.drawable.ic_add_button);
                    count=0;
                }
            }
        });

        add_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_news.animate().translationX(0).start();
                add_event.animate().translationX(0).start();
                add_workshops.animate().translationX(0).withStartAction(new Runnable() {
                    @Override
                    public void run() {
                        add_button.setImageResource(R.drawable.ic_add_button);
                        count=0;
                        startActivity(new Intent(context,CreateNewsActivity.class));
                    }
                });


            }
        });


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
