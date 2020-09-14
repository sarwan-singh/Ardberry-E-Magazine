package com.example.e_bookardberrytechnologypvtltd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static java.lang.Integer.parseInt;

public class PopUpLayout extends Activity {

    private int like_status ;
    private int bookmark_status;
    private String book_url;
    private String book_name;
    private String bookmark;
    private String like;
    private String book_like_number;
    private int first, second;
    private String book_id;
    private ProgressBar progressBar;

    DatabaseReference rootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_layout);
        bookmark_status=0;
        like_status=0;
        book_id = "gallery";

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width*.8),(int)(height*.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        final GoogleSignInAccount googleSignIn = GoogleSignIn.getLastSignedInAccount(this);

        rootRef = FirebaseDatabase.getInstance().getReference();


        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (googleSignIn!=null) {
                    book_url = dataSnapshot.child(googleSignIn.getId()).child("Pop Up Instance Image Url").getValue().toString();

                    book_name = dataSnapshot.child(googleSignIn.getId()).child("Pop Up Instance Book Name").getValue().toString();

                    bookmark = dataSnapshot.child(googleSignIn.getId()).child("Pop Up Instance Bookmark Status").getValue().toString();

                    like =  dataSnapshot.child(googleSignIn.getId()).child("Pop Up Instance Like Status").getValue().toString();

                    book_like_number = dataSnapshot.child(googleSignIn.getId()).child("Pop Up Instance Book Like number").getValue().toString();

                    first = parseInt(dataSnapshot.child(googleSignIn.getId()).child("First Book Index").getValue().toString());

                    second= parseInt(dataSnapshot.child(googleSignIn.getId()).child("Second Book Index").getValue().toString());

                }else {
                    book_url = dataSnapshot.child("Unknown User").child("Pop Up Instance Image Url").getValue().toString();

                    book_name = dataSnapshot.child("Unknown User").child("Pop Up Instance Book Name").getValue().toString();

                    bookmark = dataSnapshot.child("Unknown User").child("Pop Up Instance Bookmark Status").getValue().toString();

                    like =  dataSnapshot.child("Unknown User").child("Pop Up Instance Like Status").getValue().toString();

                    book_like_number = dataSnapshot.child("Unknown User").child("Pop Up Instance Book Like number").getValue().toString();

                    first = parseInt(dataSnapshot.child("Unknown User").child("First Book Index").getValue().toString());

                    second= parseInt(dataSnapshot.child("Unknown User").child("Second Book Index").getValue().toString());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        progressBar = findViewById(R.id.progress_circular_pop_up);
        progressBar.setVisibility(View.VISIBLE);

        final Handler handler5 = new Handler();
        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {

                bookmark_status = parseInt(bookmark);

                progressBar.setVisibility(View.GONE);

                LottieAnimationView lottieAnimationView = findViewById(R.id.bookmark_pop_up);

                if (bookmark_status == 0){
                    lottieAnimationView.setProgress(0);
                }else {
                    lottieAnimationView.setProgress(1);
                }

                like_status = parseInt(like);

                LottieAnimationView lottieAnimationView1 = findViewById(R.id.like_pop_up);


                if (like_status == 0){
                    lottieAnimationView1.setProgress(0);
                }else {
                    lottieAnimationView1.setProgress(0.14f);
                }

                ImageView imageView = findViewById(R.id.book_id);
                Glide.with(getApplicationContext()).load(book_url).into(imageView);

                TextView textViewBookName =findViewById(R.id.book_name_pop_up);
                textViewBookName.setText(book_name);

                TextView textViewLikeNumber =  findViewById(R.id.book_number_of_likes_pop_up);
                textViewLikeNumber.setText(book_like_number);

                book_id = "gallery" + first + "_" + second;


                Log.v("book_id","book_id"+book_id);

                TextView textView = findViewById(R.id.book_number_of_likes_pop_up);
                nrlikes(textView,book_id);



            }
        },2000);



    }




    public void bookmark(View view){

        final GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(PopUpLayout.this);
        if (googleSignInAccount!=null) {

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                    .child("Bookmarks")
                    .child(book_id);

            final LottieAnimationView lottieAnimationView = findViewById(view.getId());
            if (bookmark_status == 0) {
                bookmark_status = 1;
                lottieAnimationView.setProgress(0);
                lottieAnimationView.setMinAndMaxFrame(0, 50);
                lottieAnimationView.setSpeed(1);
                lottieAnimationView.playAnimation();
                lottieAnimationView.setTag("Bookmarked");
                Log.v("bookmark status","bookmarked ");
            } else if (bookmark_status == 1) {
                bookmark_status = 0;
                lottieAnimationView.setProgress(0.14f);
                lottieAnimationView.setSpeed(-1);
                lottieAnimationView.setMinAndMaxFrame(0, 7);
                lottieAnimationView.playAnimation();
                lottieAnimationView.setTag("Bookmark");
                Log.v("bookmark status","disbookmarked");
            }


            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (lottieAnimationView.getTag()=="Bookmarked"){
                        FirebaseDatabase.getInstance().getReference().child("Bookmarks").child(book_id)
                                .child(googleSignInAccount.getId()).setValue(1);
                    }else if (lottieAnimationView.getTag()=="Bookmark"){
                        FirebaseDatabase.getInstance().getReference().child("Bookmarks").child(book_id)
                                .child(googleSignInAccount.getId()).removeValue();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{


            Toast.makeText(PopUpLayout.this,"Please Sign In To Perform This Action",Toast.LENGTH_SHORT).show();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(PopUpLayout.this,Account.class);
                    startActivity(intent);
                }
            }, 2500);

        }
    }

    public void like(View view){


        final LottieAnimationView lottieAnimationView = findViewById(view.getId());

        final GoogleSignInAccount googleSignIn = GoogleSignIn.getLastSignedInAccount(PopUpLayout.this);
        if (googleSignIn!=null) {

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                    .child("Likes")
                    .child(book_id);

            if (like_status == 0) {
                like_status = 1;
                lottieAnimationView.setProgress(0);
                lottieAnimationView.setMinAndMaxFrame(0, 29);
                lottieAnimationView.playAnimation();
                lottieAnimationView.setTag("Liked");
                Log.v("like status","liked ");

            } else if (like_status == 1) {
                like_status = 0;
                lottieAnimationView.setProgress(0.4142f);
                lottieAnimationView.setMinAndMaxFrame(29, 70);
                lottieAnimationView.playAnimation();
                lottieAnimationView.setTag("Like");
                Log.v("like status","disliked ");
            }

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (lottieAnimationView.getTag()=="Liked"){
                        FirebaseDatabase.getInstance().getReference().child("Likes").child(book_id)
                                .child(googleSignIn.getId()).setValue(1);
                    }else if (lottieAnimationView.getTag()=="Like"){
                        FirebaseDatabase.getInstance().getReference().child("Likes").child(book_id)
                                .child(googleSignIn.getId()).removeValue();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            TextView textView = findViewById(R.id.book_number_of_likes_pop_up);
            nrlikes(textView,book_id);

        }else{


            Toast.makeText(PopUpLayout.this,"Please Sign In To Perform This Action",Toast.LENGTH_SHORT).show();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(PopUpLayout.this,Account.class);
                    startActivity(intent);
                }
            }, 2500);

        }
    }

    private void nrlikes(final TextView likes,String bookid){
        DatabaseReference reference =FirebaseDatabase.getInstance().getReference().child("Likes")
                .child(bookid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                likes.setText(dataSnapshot.getChildrenCount()+" likes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readPop(View view){
        Intent intent = new Intent(PopUpLayout.this,bookPdf.class);
        intent.putExtra(Constant.BOOK,""+(first+1));
        startActivity(intent);


    }

    @Override
    protected void onStop() {
        finish();
        super.onStop();
    }
}
