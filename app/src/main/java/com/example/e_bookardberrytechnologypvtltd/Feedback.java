package com.example.e_bookardberrytechnologypvtltd;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Feedback extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private EditText emaildata,subjectdata,messagedata;
    private Button submitButton;
    private ActionBarDrawerToggle mToggle;
    private Firebase firebase;
    private Toolbar mToolBar;
    boolean connected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        mDrawerLayout = findViewById(R.id.mainDrawer3);
        mToggle = new ActionBarDrawerToggle(this , mDrawerLayout , R.string.open , R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToolBar = findViewById(R.id.navigation_actionbar);
        setSupportActionBar(mToolBar);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        mToggle.syncState();
        mToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        subjectdata = findViewById(R.id.subject_feedback_edittext);
        emaildata = findViewById(R.id.email_feedback_edittext);
        messagedata = findViewById(R.id.detail_feedback_edittext);
        submitButton = findViewById(R.id.submit_button_feedback);
        Firebase.setAndroidContext(this);

        String UniqueID = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                          Settings.Secure.ANDROID_ID);



        firebase = new Firebase("https://e-book-ardberry.firebaseio.com/USER_FEEDBACK_" + UniqueID);


        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connected == true) {
                    final String subject = subjectdata.getText().toString().trim();
                    final String email = emaildata.getText().toString().trim();
                    final String message = messagedata.getText().toString().trim();
                    Firebase child_email = firebase.child("Email");
                    child_email.setValue(email);
                    if (email.isEmpty()) {
                        emaildata.setError("This is a required field! ");
                    } else {
                        emaildata.setError(null);
                        submitButton.setEnabled(true);


                        Firebase child_subject = firebase.child("Subject");
                        child_subject.setValue(subject);
                        if (subject.isEmpty()) {
                            subjectdata.setError("This is a required field! ");
                        } else {
                            subjectdata.setError(null);
                            submitButton.setEnabled(true);


                            Firebase child_detail = firebase.child("Details");
                            child_detail.setValue(message);
                            if (message.isEmpty()) {
                                messagedata.setError("This is a required field! ");
                            } else {
                                messagedata.setError(null);


                                Toast.makeText(Feedback.this, "Your Message Has Been Successfully Sent To Developer!!", Toast.LENGTH_SHORT).show();
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(Feedback.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }, 2000);

                            }
                        }
                    }

                }else {
                    Toast.makeText(getApplicationContext(),"Please connect to internet",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void AccountFromFeedback(MenuItem item){
        Intent intent  = new Intent(this,Account.class);
        startActivity(intent);
    }
    public void HomeFromFeedback(MenuItem item){
        Intent intent  = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void FeedbackFromFeedback(MenuItem item){
        Intent intent = new Intent(this,Feedback.class);
        startActivity(intent);
    }
}
