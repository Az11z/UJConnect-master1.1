package com.example.customer.ujconnect;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class HomeScreen extends AppCompatActivity {


    private static final String TAG = "HomeScreen";
    static boolean admin = false;
    private Button button;
    private EditText email,emailReg;
    private EditText password,passwordReg;
    private EditText name;
    private EditText phone;
    private TextView Signup,fgtmypass;
    private AlertDialog closedialog1, closedialog2;
    private Context context;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView admin_login ;
    private CheckBox checkBox ;






    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        //Login
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.ujEmail);
        password = findViewById(R.id.ujPassword);
        button = findViewById(R.id.connect);
        fgtmypass =  findViewById(R.id.fgtmypass);
        context=this;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            System.out.println(currentUser.getEmail());
            updateUI(currentUser);
        }
    }





    public void fgtPass(View v){


        FirebaseAuth auth = FirebaseAuth.getInstance();

        final String emailreset = email.getText().toString().trim() ;
        String emailAddress = emailreset+"@uj.edu.sa";
if (emailreset.isEmpty()){
    Toast.makeText(HomeScreen.this, "enter your id", Toast.LENGTH_SHORT).show();

}
else
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(HomeScreen.this, "Email sent", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }






    private void createAccount(){
        final String Email = emailReg.getText().toString().trim()+"@uj.edu.sa";
        String Password = passwordReg.getText().toString().trim();
        final String Name = name.getText().toString().trim();
        final String PhoneNumber = phone.getText().toString().trim();





        name.requestFocus();

        if(Name.isEmpty()){
            name.setError("Please enter your name!");
            Toast.makeText(HomeScreen.this, "Please enter your name!", Toast.LENGTH_SHORT).show();
            name.requestFocus();
            return;
        }

        if(Email.isEmpty()){
            email.setError("Email is required!");
            Toast.makeText(HomeScreen.this, "Email is required!", Toast.LENGTH_SHORT).show();
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            email.setError("Please enter a valid email!");
            Toast.makeText(HomeScreen.this, "Please enter a valid email!", Toast.LENGTH_SHORT).show();
            email.requestFocus();
            return;
        }


        if(Password.isEmpty()){
            password.setError("Password is required!");
            Toast.makeText(HomeScreen.this, "Password is required!", Toast.LENGTH_SHORT).show();
            password.requestFocus();
            return;
        }
        if(Password.length()<6){
            password.setError("Minimum length of password should be 6!");
            Toast.makeText(HomeScreen.this, "Minimum length of password should be 6!", Toast.LENGTH_SHORT).show();
            password.requestFocus();
            return;
        }


        if(Name.isEmpty()){
            password.setError("You need to fill this!");
            password.requestFocus();
            return;
        }
        if(PhoneNumber.isEmpty()){
            phone.setError("You need to fill this!");
            Toast.makeText(HomeScreen.this, "You need to fill your phone number!", Toast.LENGTH_SHORT).show();

            phone.requestFocus();
            return;
        }
        if(PhoneNumber.length() < 10 || PhoneNumber.length() > 10){
            phone.setError("10 digits phone number!");
            Toast.makeText(HomeScreen.this, "10 digits phone number!", Toast.LENGTH_SHORT).show();

            phone.requestFocus();
            return;
        }


// create user

        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {



                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser Curruser = mAuth.getCurrentUser();
                            Toast.makeText(HomeScreen.this, "success! Please Check Your email", Toast.LENGTH_SHORT).show();
                            Curruser.sendEmailVerification();


                          //Store User info in DB
                            String uid = Curruser.getUid();

                            mDatabase = FirebaseDatabase.getInstance().getReference();
                           Date RegDate = Calendar.getInstance().getTime();


                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                            String formattedDate = df.format(RegDate);


                            User user = new User(Name, Email,PhoneNumber,formattedDate,false);
                            user.setFirebase_id(Curruser.getUid());

                            mDatabase.child("users").child(Email.split("@")[0]).setValue(user);




                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", );

                            Toast.makeText(HomeScreen.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //  updateUI(null);


                        }

                        // ...
                    }
                });


    }




    private void updateUI(FirebaseUser user) {

        if (user != null && user.isEmailVerified() && user.getEmail()!=null  ) {
            FirebaseDatabase.getInstance().getReference("users").child(user.getEmail().split("@")[0]).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child("admin").getValue(boolean.class)){
                        admin = true;
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            startActivity(new Intent(context,MainActivity.class));
            finish();
        }
    }


    public void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreen.this);
        LayoutInflater inflater = HomeScreen.this.getLayoutInflater();
        final View view1 = inflater.inflate(R.layout.sign_up_dialog, null);
        view1.setBackground(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final View view = inflater.inflate(R.layout.login_dialog, null);
        Signup = view.findViewById(R.id.signuplabel);


        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { }})
                .setPositiveButton("login", new DialogInterface.OnClickListener() {



                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String user =  email.getText().toString().trim();
                        String Password = password.getText().toString().trim();
                        String username =  user+"@uj.edu.sa";
                        FirebaseUser userid = FirebaseAuth.getInstance().getCurrentUser();
                        if (user.isEmpty()||Password.isEmpty()){
                            Toast.makeText(HomeScreen.this, "Enter your id & Password", Toast.LENGTH_SHORT).show();
                        }
                        else

                        mAuth.signInWithEmailAndPassword(username, Password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(TAG, "signInWithEmail:success");
                                            FirebaseUser user = mAuth.getCurrentUser();

                                           // String name = "Welcome "+user.getDisplayName();


                                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                                            DatabaseReference uidRef = rootRef.child("users").child(uid);
                                            ValueEventListener eventListener = new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    String Username = dataSnapshot.child("name").getValue(String.class);
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {}
                                            };
                                            uidRef.addListenerForSingleValueEvent(eventListener);








                                           // Toast.makeText(HomeScreen.this, Username, Toast.LENGTH_SHORT).show();
                                            if(user.isEmailVerified()){
                                                updateUI(user);

                                            }


                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                                            Toast.makeText(HomeScreen.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();



                                            updateUI(null);
                                        }

                                        // [START_EXCLUDE]
                                        if (!task.isSuccessful()) {



                                        }

                                    }
                                });

                    }});


        email = view.findViewById(R.id.ujEmail);
        password = view.findViewById(R.id.ujPassword);


        closedialog1 = builder.create();
        closedialog1.show();



        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder2 = new AlertDialog.Builder(HomeScreen.this);
                LayoutInflater inflater = HomeScreen.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.sign_up_dialog, null);
                name=  view.findViewById(R.id.studentName);
                phone= view.findViewById(R.id.editText3);
                emailReg= view.findViewById(R.id.ujcID);
                passwordReg = view.findViewById(R.id.ujPassword2);
                TextView Login = view.findViewById(R.id.inactiveLogin);









                builder2.setView(view)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }})
                        .setPositiveButton("sign up", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                createAccount();

                            }});



// sign up builder
                closedialog2 = builder2.create();
                closedialog2.show();
               closedialog1.dismiss();




                Login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closedialog1.show();
                        closedialog2.dismiss();

                    }
                });




            }
        });








    }


}