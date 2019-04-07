package com.example.customer.ujconnect;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateWorkshopActivity extends AppCompatActivity {

    Calendar myCalendar = Calendar.getInstance();
    public static final int PICK_IMAGE = 1;
    private Uri filePath;


    TextView date;
    TextView time;
    EditText title;
    EditText instructor;
    EditText description;
    EditText duration;
    EditText location;
    ImageView submit;
    ImageView workshopImage;
    DatePickerDialog.OnDateSetListener x;
    FrameLayout frameLayout;
    DatabaseReference databaseReference;
    WorkShopDetails workShopDetails;
    ImageView add_image;
    StorageReference storageReference;
    Context context;
    String deane;
    String departmente;
    String firebase_department_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workshop);
        date = findViewById(R.id.workshop_date);
        time = findViewById(R.id.workshop_time);
        title = findViewById(R.id.workshopTitle);
        instructor = findViewById(R.id.workshop_instructor);
        description = findViewById(R.id.workshop_description_admin);
        duration = findViewById(R.id.workshop_duration);
        location = findViewById(R.id.workshop_location_admin);
        submit = findViewById(R.id.workshop_submit_admin);
        add_image = findViewById(R.id.add_image_button);
        workshopImage = findViewById(R.id.workshop_image);
        workShopDetails = new WorkShopDetails();
        context = this;


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase.getInstance().getReference("users").child(user.getEmail().split("@")[0]).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    firebase_department_id = dataSnapshot.child("department_firebase_id").getValue(String.class);



                FirebaseDatabase.getInstance().getReference("Department").child(firebase_department_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            System.out.println(dataSnapshot1);
                            deane = dataSnapshot1.child("admin_name").getValue(String.class);
                            departmente = dataSnapshot1.child("department_name").getValue(String.class);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHourPicker();
            }
        });


        x = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CreateWorkshopActivity.this, x, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        final ImageView drop_down = findViewById(R.id.blue_arrow_drop_down);

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


        databaseReference = FirebaseDatabase.getInstance().getReference("Workshops").child("department").push();
        String x = databaseReference.getKey();
        storageReference = FirebaseStorage.getInstance().getReference("Workshops").child("department").child( x);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titlee = title.getText().toString();
                String descriptione = description.getText().toString();
                String datee = date.getText().toString();
                String locatione = location.getText().toString();
                String instructore = instructor.getText().toString();
                String timee = time.getText().toString();
                if (!titlee.isEmpty()      && !descriptione.isEmpty() &&
                    !datee.isEmpty()       && !locatione.isEmpty() &&
                    !instructore.isEmpty() && !timee.isEmpty()){
                    workShopDetails.setTitle(titlee);
                    workShopDetails.setDescription(descriptione);
                    workShopDetails.setDate(datee);
                    workShopDetails.setLocation(locatione);
                    workShopDetails.setInstructor(instructore);
                    workShopDetails.setDuration(timee);
                    workShopDetails.setTime(time.getText().toString());
                    workShopDetails.setDean(deane);
                    workShopDetails.setDepartment(departmente);
                    databaseReference.setValue(workShopDetails);
                    finish();
                }
                else{
                    Toast.makeText(CreateWorkshopActivity.this, "Please fill all the information", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // this code was copied from https://stackoverflow.com/a/38352844
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });


    }


    public void showHourPicker() {
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);


        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalender.set(Calendar.MINUTE, minute);
                    time.setText(hourOfDay + ":" + minute);

                }
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
        timePickerDialog.setTitle("Choose hour:");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();


    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date.setText(sdf.format(myCalendar.getTime()) + "");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            filePath = data.getData();
            try {
                final Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                storageReference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(CreateWorkshopActivity.this, "image uploaded", Toast.LENGTH_LONG).show();
                        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                workShopDetails.setImage(task.getResult().toString());
                                workshopImage.setImageBitmap(bitmap);
                            }
                        });
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
