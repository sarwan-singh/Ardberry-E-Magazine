package com.example.e_bookardberrytechnologypvtltd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class Account extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolBar;
    private LinearLayout linearLayout;
    private Button Signout;
    private SignInButton SignIn;
    private TextView Name,Email,Please_click;
    private DatabaseReference reference;
    private ImageView imageView;
    private FirebaseAuth auth;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mDrawerLayout = findViewById(R.id.mainDrawer2);
        mToggle = new ActionBarDrawerToggle(this , mDrawerLayout , R.string.open , R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToolBar = findViewById(R.id.navigation_actionbar);
        setSupportActionBar(mToolBar);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        mToggle.syncState();
        mToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));




        linearLayout = findViewById(R.id.layoutAcount);
        SignIn = findViewById(R.id.google_button_login);
        Signout = findViewById(R.id.google_button_logout);
        Name = findViewById(R.id.user_name);
        Email = findViewById(R.id.user_email);
        Please_click = findViewById(R.id.please_click_text);
        imageView = findViewById(R.id.sign_up_image);
        Signout.setOnClickListener(this);
        SignIn.setOnClickListener(this);
        linearLayout.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions).build();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.google_button_login:
                signIn();
                break;
            case R.id.google_button_logout:
                signOut();
                break;
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn(){

        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);

        startActivityForResult(intent,REQ_CODE);
        LottieAnimationView lottieAnimationView = findViewById(R.id.loading_animation);
        lottieAnimationView.setVisibility(View.VISIBLE);
    }

    private void signOut(){

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUi(false);
            }
        });
        Toast.makeText(this,"Successfully Logged Out",Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleResult(GoogleSignInResult result){

        if (result.isSuccess()){
            LottieAnimationView lottieAnimationView = findViewById(R.id.loading_animation);
            lottieAnimationView.setVisibility(View.GONE);
            GoogleSignInAccount account = result.getSignInAccount();
            assert account != null;
            String name = account.getDisplayName();
            String email = account.getEmail();
            String userId = account.getId();
            Name.setText(name);
            Email.setText(email);
            reference = FirebaseDatabase.getInstance().getReference().child("USERS").child(userId);

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id", userId);
            hashMap.put("name",name);
            hashMap.put("email",email);

            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });

            if (null != account.getPhotoUrl()) {
                String img_url = account.getPhotoUrl().toString();
                imageView.setVisibility(View.VISIBLE);
                Glide.with(this).load(img_url).into(imageView);
            } else {
                imageView.setImageResource(R.drawable.unknown);

            }
            updateUi(true);

        }else {
            updateUi(false);
        }
    }

    private void updateUi(boolean isLogin){

        if (isLogin){
            LottieAnimationView lottieAnimationView = findViewById(R.id.loading_animation);
            lottieAnimationView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            SignIn.setVisibility(View.GONE);
            Please_click.setVisibility(View.GONE);

        }else {
            LottieAnimationView lottieAnimationView = findViewById(R.id.loading_animation);
            lottieAnimationView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
            SignIn.setVisibility(View.VISIBLE);
            Please_click.setVisibility(View.VISIBLE);

        }

    }
    public void AccountFromAccount(MenuItem item){
        Intent intent  = new Intent(this,Account.class);
        startActivity(intent);
    }
    public void HomeFromAccount(MenuItem item){
        Intent intent  = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void FeedbackFromAccount(MenuItem item){
        Intent intent = new Intent(this,Feedback.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==REQ_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }
}
