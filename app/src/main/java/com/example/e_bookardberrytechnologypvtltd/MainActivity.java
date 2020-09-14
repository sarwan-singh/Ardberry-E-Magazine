package com.example.e_bookardberrytechnologypvtltd;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Objects;

import static java.lang.Boolean.valueOf;
import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {



    private int[][] number_of_likes_of_each_book = new int[3][4];
    private String[][] book_Id = new String[3][4];
    private String Book_id_highest,Book_id_second_highest,Book_id_third_highest,Book_id_forth_highest;
    private ImageView imageView1_most_liked, imageView2_most_liked, imageView3_most_liked, imageView4_most_liked;
    private TextView bookName1_most_liked, bookName2_most_liked, bookName3_most_liked, bookName4_most_liked;
    private TextView number_of_likes_most_liked_book1,number_of_likes_most_liked_book2,number_of_likes_most_liked_book3,number_of_likes_most_liked_book4;
    private LottieAnimationView like1_most_liked,like2_most_liked,like3_most_liked,like4_most_liked,bookmark1_most_liked,bookmark2_most_liked,bookmark3_most_liked,bookmark4_most_liked;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolBar;
    public int[][] likes = new int[3][4];
    public int[][] bookmarks = new int[3][4];
    public String[][] urls = new String[3][4];
    public int read_more_status;
    public int first,second;
    private TextView textView_like_number[][] = new TextView[3][4];
    public String[][] book_id = new String[3][4];
    public String[][] book_names = new String[3][4];
    public String[][] url_of_book = new String[3][4];
    private SliderLayout sliderLayout;

    public MainActivity(){
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.mainDrawer);
        mToggle = new ActionBarDrawerToggle(this , mDrawerLayout , R.string.open , R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);


        read_more_status = 0;


//        likes[0][0] = 0;        //first index of array = gallery_number
//        likes[0][1] = 0;        //second index of array = child_gallery_number
//        likes[0][2] = 0;        //0 means not liked
//        likes[0][3] = 0;        //1 means liked


        for (int i = 0;i<3;i++){
            for (int j=0;j<4;j++){
                likes[i][j] = 0;
            }
        }
        mToolBar = findViewById(R.id.navigation_actionbar);
        setSupportActionBar(mToolBar);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        mToggle.syncState();
        mToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));


        for (int i = 0;i<3;i++){
            for (int j=0;j<4;j++){
                book_id[i][j] = "gallery" + i + "_" + j;
            }
        }

        book_names = new String[][]{
                {"Houses", "Concept","Architecture","Inside Outside"},
                {"De Zeen", "Architect","GQ LUXE","Architect B.T.B"},
                {"LUXE.", "Architect B.I.C","House Architect","Architect Concept"}

        };



        urls = new String[][]{
                {"https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/MagazinesImages%2Fmagazine1.jpg?alt=media&token=c1c28940-087e-40b3-a603-252b1163be33",
                 "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/MagazinesImages%2Fmagazine2.jpg?alt=media&token=7ddc790b-bb73-45f0-9c7b-9fd567f220dd",
                 "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/MagazinesImages%2Fmagazine3.jpg?alt=media&token=ce87dcae-769b-4f30-ae29-909b0d985a74",
                 "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/MagazinesImages%2Fmagazine4.jpg?alt=media&token=3476571b-976b-4a47-89e1-02195fa33631"},
                {"https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/MagazinesImages%2Fmagazine5.jpg?alt=media&token=d33ca4aa-80b2-4c93-aa4b-633fa3419ba0",
                 "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/MagazinesImages%2Fmagazine6.jpg?alt=media&token=5e894e34-413e-46c3-8538-adf4ad073df4",
                 "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/MagazinesImages%2Fmagazine7.jpg?alt=media&token=afa74f0b-481c-458f-b4fd-a3d288000733",
                 "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/MagazinesImages%2Fmagazine8.jpg?alt=media&token=e800fd60-f4dc-4f78-a537-5d3cd12fedb8"},
                {"https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/MagazinesImages%2Fmagazine9.jpg?alt=media&token=c387a789-243d-4e33-bbbb-d4741c646670",
                 "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/MagazinesImages%2Fmagazine10.jpg?alt=media&token=f1c2ada1-a96f-4a2f-acf3-c216f641a9f5",
                 "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/MagazinesImages%2Fmagazine11.jpg?alt=media&token=65b75ed4-934e-4469-b8e1-298c4cf1395e",
                 "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/MagazinesImages%2Fmagazine12.jpg?alt=media&token=3db273ac-62d8-43b9-8840-6cb5a7f69946"}
        };



        final LottieAnimationView loading = findViewById(R.id.loadingmainlottie);
        loading.setVisibility(View.VISIBLE);
        loading.playAnimation();
        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {

                loading.pauseAnimation();
                loading.setVisibility(View.GONE);
            }
        },3000);


        ImageView img1 = findViewById(R.id.gallery_image_0_0);
        ImageView img2 = findViewById(R.id.gallery_image_0_1);
        ImageView img3 = findViewById(R.id.gallery_image_0_2);
        ImageView img4 = findViewById(R.id.gallery_image_0_3);

        ImageView img5 = findViewById(R.id.gallery_image_1_0);
        ImageView img6 = findViewById(R.id.gallery_image_1_1);
        ImageView img7 = findViewById(R.id.gallery_image_1_2);
        ImageView img8 = findViewById(R.id.gallery_image_1_3);

        ImageView img9 = findViewById(R.id.gallery_image_2_0);
        ImageView img10 = findViewById(R.id.gallery_image_2_1);
        ImageView img11 = findViewById(R.id.gallery_image_2_2);
        ImageView img12 = findViewById(R.id.gallery_image_2_3);

        Glide.with(this).load(urls[0][0]).into(img1);
        Glide.with(this).load(urls[0][1]).into(img2);
        Glide.with(this).load(urls[0][2]).into(img3);
        Glide.with(this).load(urls[0][3]).into(img4);

        Glide.with(this).load(urls[1][0]).into(img5);
        Glide.with(this).load(urls[1][1]).into(img6);
        Glide.with(this).load(urls[1][2]).into(img7);
        Glide.with(this).load(urls[1][3]).into(img8);

        Glide.with(this).load(urls[2][0]).into(img9);
        Glide.with(this).load(urls[2][1]).into(img10);
        Glide.with(this).load(urls[2][2]).into(img11);
        Glide.with(this).load(urls[2][3]).into(img12);

        TextView textView1 = findViewById(R.id.bookname_gallery0_0);
        TextView textView2 = findViewById(R.id.bookname_gallery0_1);
        TextView textView3 = findViewById(R.id.bookname_gallery0_2);
        TextView textView4 = findViewById(R.id.bookname_gallery0_3);
        TextView textView5 = findViewById(R.id.bookname_gallery1_0);
        TextView textView6 = findViewById(R.id.bookname_gallery1_1);
        TextView textView7 = findViewById(R.id.bookname_gallery1_2);
        TextView textView8 = findViewById(R.id.bookname_gallery1_3);
        TextView textView9 = findViewById(R.id.bookname_gallery2_0);
        TextView textView10 = findViewById(R.id.bookname_gallery2_1);
        TextView textView11 = findViewById(R.id.bookname_gallery2_2);
        TextView textView12 = findViewById(R.id.bookname_gallery2_3);


        textView1.setText(book_names[0][0]);
        textView2.setText(book_names[0][1]);
        textView3.setText(book_names[0][2]);
        textView4.setText(book_names[0][3]);
        textView5.setText(book_names[1][0]);
        textView6.setText(book_names[1][1]);
        textView7.setText(book_names[1][2]);
        textView8.setText(book_names[1][3]);
        textView9.setText(book_names[2][0]);
        textView10.setText(book_names[2][1]);
        textView11.setText(book_names[2][2]);
        textView12.setText(book_names[2][3]);

        textView_like_number[0][0] = findViewById(R.id.no_of_likes_0_0);
        textView_like_number[0][1] = findViewById(R.id.no_of_likes_0_1);
        textView_like_number[0][2] = findViewById(R.id.no_of_likes_0_2);
        textView_like_number[0][3] = findViewById(R.id.no_of_likes_0_3);

        textView_like_number[1][0] = findViewById(R.id.no_of_likes_1_0);
        textView_like_number[1][1] = findViewById(R.id.no_of_likes_1_1);
        textView_like_number[1][2] = findViewById(R.id.no_of_likes_1_2);
        textView_like_number[1][3] = findViewById(R.id.no_of_likes_1_3);

        textView_like_number[2][0] = findViewById(R.id.no_of_likes_2_0);
        textView_like_number[2][1] = findViewById(R.id.no_of_likes_2_1);
        textView_like_number[2][2] = findViewById(R.id.no_of_likes_2_2);
        textView_like_number[2][3] = findViewById(R.id.no_of_likes_2_3);

        bookName1_most_liked = findViewById(R.id.bookname_gallery_most_liked_0);
        bookName2_most_liked = findViewById(R.id.bookname_gallery_most_liked_1);
        bookName3_most_liked = findViewById(R.id.bookname_gallery_most_liked_2);
        bookName4_most_liked = findViewById(R.id.bookname_gallery_most_liked_3);

        imageView1_most_liked = findViewById(R.id.gallery_image_most_liked_0);
        imageView2_most_liked = findViewById(R.id.gallery_image_most_liked_1);
        imageView3_most_liked = findViewById(R.id.gallery_image_most_liked_2);
        imageView4_most_liked = findViewById(R.id.gallery_image_most_liked_3);

        number_of_likes_most_liked_book1 = findViewById(R.id.no_of_likes__most_liked_0);
        number_of_likes_most_liked_book2 = findViewById(R.id.no_of_likes__most_liked_1);
        number_of_likes_most_liked_book3 = findViewById(R.id.no_of_likes__most_liked_2);
        number_of_likes_most_liked_book4 = findViewById(R.id.no_of_likes__most_liked_3);
        like1_most_liked  = findViewById(R.id.like_gallery_most_liked_0);
        like2_most_liked  = findViewById(R.id.like_gallery_most_liked_1);
        like3_most_liked  = findViewById(R.id.like_gallery_most_liked_2);
        like4_most_liked  = findViewById(R.id.like_gallery_most_liked_3);
        bookmark1_most_liked  = findViewById(R.id.watchlater_gallery_most_liked_0);
        bookmark2_most_liked  = findViewById(R.id.watchlater_gallery_most_liked_1);
        bookmark3_most_liked  = findViewById(R.id.watchlater_gallery_most_liked_2);
        bookmark4_most_liked  = findViewById(R.id.watchlater_gallery_most_liked_3);

        url_of_book = new String[][]{
                {       "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/Books%2Fbook0_0.pdf?alt=media&token=1fabdc6f-b856-4720-ba67-b56c1fa01467",
                        "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/Books%2Fbook0_0.pdf?alt=media&token=1fabdc6f-b856-4720-ba67-b56c1fa01467",
                        "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/Books%2Fbook0_0.pdf?alt=media&token=1fabdc6f-b856-4720-ba67-b56c1fa01467",
                        "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/Books%2Fbook0_0.pdf?alt=media&token=1fabdc6f-b856-4720-ba67-b56c1fa01467"
                },
                {       "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/Books%2Fbook0_0.pdf?alt=media&token=1fabdc6f-b856-4720-ba67-b56c1fa01467",
                        "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/Books%2Fbook0_0.pdf?alt=media&token=1fabdc6f-b856-4720-ba67-b56c1fa01467",
                        "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/Books%2Fbook0_0.pdf?alt=media&token=1fabdc6f-b856-4720-ba67-b56c1fa01467",
                        "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/Books%2Fbook0_0.pdf?alt=media&token=1fabdc6f-b856-4720-ba67-b56c1fa01467"
                },
                {       "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/Books%2Fbook0_0.pdf?alt=media&token=1fabdc6f-b856-4720-ba67-b56c1fa01467",
                        "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/Books%2Fbook0_0.pdf?alt=media&token=1fabdc6f-b856-4720-ba67-b56c1fa01467",
                        "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/Books%2Fbook0_0.pdf?alt=media&token=1fabdc6f-b856-4720-ba67-b56c1fa01467",
                        "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/Books%2Fbook0_0.pdf?alt=media&token=1fabdc6f-b856-4720-ba67-b56c1fa01467"
                }
        };


        sliderLayout = findViewById(R.id.slider);

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Best Architectural Magazines", "https://www.lionop.com/img/productimages/b/41024BK.jpg");
        url_maps.put("Bookmark Your Favourites", "https://i.etsystatic.com/11009241/r/il/c9034d/881913685/il_794xN.881913685_h033.jpg");
        url_maps.put("No Pages or Plastic", "https://i.pinimg.com/564x/d9/95/0d/d9950d5c800893f77c02945c6678255a.jpg");
        url_maps.put("Understand Architecture with Us","https://i.pinimg.com/564x/6e/6c/1f/6e6c1f22964b136ea80823e5377dcca5.jpg");

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            sliderLayout.addSlider(textSliderView);
        }

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Foreground2Background);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);



        findViewById(R.id.facebookiconmain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/ardberry.ml/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        findViewById(R.id.instagramiconmain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.instagram.com/ardberrytechnology/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        findViewById(R.id.twittericonmain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.twitter.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        findViewById(R.id.linkediniconmain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.linkedin.com/company/ardberrytechnology/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });




    }


    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onResume() {


        final LottieAnimationView[][] lottieAnimationViewlike = new LottieAnimationView[3][4];
        lottieAnimationViewlike[0][0] = findViewById(R.id.like_gallery0_0);
        lottieAnimationViewlike[0][1] = findViewById(R.id.like_gallery0_1);
        lottieAnimationViewlike[0][2] = findViewById(R.id.like_gallery0_2);
        lottieAnimationViewlike[0][3] = findViewById(R.id.like_gallery0_3);

        lottieAnimationViewlike[1][0] = findViewById(R.id.like_gallery1_0);
        lottieAnimationViewlike[1][1] = findViewById(R.id.like_gallery1_1);
        lottieAnimationViewlike[1][2] = findViewById(R.id.like_gallery1_2);
        lottieAnimationViewlike[1][3] = findViewById(R.id.like_gallery1_3);

        lottieAnimationViewlike[2][0] = findViewById(R.id.like_gallery2_0);
        lottieAnimationViewlike[2][1] = findViewById(R.id.like_gallery2_1);
        lottieAnimationViewlike[2][2] = findViewById(R.id.like_gallery2_2);
        lottieAnimationViewlike[2][3] = findViewById(R.id.like_gallery2_3);



        final LottieAnimationView[][] lottieAnimationViewbookmark = new LottieAnimationView[3][4];
        lottieAnimationViewbookmark[0][0] = findViewById(R.id.watchlater_gallery0_0);
        lottieAnimationViewbookmark[0][1] = findViewById(R.id.watchlater_gallery0_1);
        lottieAnimationViewbookmark[0][2] = findViewById(R.id.watchlater_gallery0_2);
        lottieAnimationViewbookmark[0][3] = findViewById(R.id.watchlater_gallery0_3);

        lottieAnimationViewbookmark[1][0] = findViewById(R.id.watchlater_gallery1_0);
        lottieAnimationViewbookmark[1][1] = findViewById(R.id.watchlater_gallery1_1);
        lottieAnimationViewbookmark[1][2] = findViewById(R.id.watchlater_gallery1_2);
        lottieAnimationViewbookmark[1][3] = findViewById(R.id.watchlater_gallery1_3);

        lottieAnimationViewbookmark[2][0] = findViewById(R.id.watchlater_gallery2_0);
        lottieAnimationViewbookmark[2][1] = findViewById(R.id.watchlater_gallery2_1);
        lottieAnimationViewbookmark[2][2] = findViewById(R.id.watchlater_gallery2_2);
        lottieAnimationViewbookmark[2][3] = findViewById(R.id.watchlater_gallery2_3);


        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleSignInAccount!=null){

            getPreviousData();

        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;i<3;i++){
                    for (int j=0;j<4;j++){

                        if (likes[i][j]==0){
                            lottieAnimationViewlike[i][j].setTag("like");
                        }else {
                            lottieAnimationViewlike[i][j].setTag("liked");
                        }
                        if (bookmarks[i][j] == 0){
                            lottieAnimationViewbookmark[i][j].setTag("bookmark");
                        }else {
                            lottieAnimationViewbookmark[i][j].setTag("bookmarked");
                        }
                    }

                }

                for (int i = 0;i<3;i++)
                {
                    for (int j = 0;j<4;j++){
                        Log.v("like value[][]" ,"like value" + likes[i][j]);
                        Log.v("","sdfasdf" + i + j);

                    }
                }

                for (int i =0;i<3;i++){
                    for (int j =0;j<4;j++){
                        if (lottieAnimationViewlike[i][j].getTag()=="like"){
                            lottieAnimationViewlike[i][j].setProgress(0);
                        }
                        else {
                            lottieAnimationViewlike[i][j].setProgress(0.414f);
                        }
                    }
                }
                for (int i =0;i<3;i++){
                    for (int j =0;j<4;j++){
                        if (lottieAnimationViewbookmark[i][j].getTag()=="bookmark"){
                            lottieAnimationViewbookmark[i][j].setProgress(0);
                        }
                        else {
                            lottieAnimationViewbookmark[i][j].setProgress(1);
                        }
                    }
                }
            }


        }, 3300);



        for (int i =0;i<3;i++ ){
            for (int j=0;j<4;j++){
                nrlikes(textView_like_number[i][j],book_id[i][j]);
            }
        }


        for (int i = 0;i<3;i++){
            for (int j=0;j<4;j++){
                book_Id[i][j] = "gallery" + i + "_" + j;
            }
        }        for (int i = 0;i<3;i++){
            for (int j = 0;j<4;j++){
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes").child(book_Id[i][j]);
                final int finalJ = j;
                final int finalI = i;
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        number_of_likes_of_each_book[finalI][finalJ] = (int) dataSnapshot.getChildrenCount();
                        Log.v("likes","LIkes = " + number_of_likes_of_each_book[finalI][finalJ]);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }



        final Handler handler4 = new Handler();
        handler4.postDelayed(new Runnable() {
            @Override
            public void run() {
                findHighestFourBooks();
            }
        },3330);



        super.onResume();
//        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
//        if (googleSignInAccount!=null){
//
//            getPreviousData();
//
//        }
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//        final LottieAnimationView[][] lottieAnimationViewlike = new LottieAnimationView[3][4];
//        lottieAnimationViewlike[0][0] = findViewById(R.id.like_gallery0_0);
//        lottieAnimationViewlike[0][1] = findViewById(R.id.like_gallery0_1);
//        lottieAnimationViewlike[0][2] = findViewById(R.id.like_gallery0_2);
//        lottieAnimationViewlike[0][3] = findViewById(R.id.like_gallery0_3);
//
//        lottieAnimationViewlike[1][0] = findViewById(R.id.like_gallery1_0);
//        lottieAnimationViewlike[1][1] = findViewById(R.id.like_gallery1_1);
//        lottieAnimationViewlike[1][2] = findViewById(R.id.like_gallery1_2);
//        lottieAnimationViewlike[1][3] = findViewById(R.id.like_gallery1_3);
//
//        lottieAnimationViewlike[2][0] = findViewById(R.id.like_gallery2_0);
//        lottieAnimationViewlike[2][1] = findViewById(R.id.like_gallery2_1);
//        lottieAnimationViewlike[2][2] = findViewById(R.id.like_gallery2_2);
//        lottieAnimationViewlike[2][3] = findViewById(R.id.like_gallery2_3);
//
//
//
//        final LottieAnimationView[][] lottieAnimationViewbookmark = new LottieAnimationView[3][4];
//        lottieAnimationViewbookmark[0][0] = findViewById(R.id.watchlater_gallery0_0);
//        lottieAnimationViewbookmark[0][1] = findViewById(R.id.watchlater_gallery0_1);
//        lottieAnimationViewbookmark[0][2] = findViewById(R.id.watchlater_gallery0_2);
//        lottieAnimationViewbookmark[0][3] = findViewById(R.id.watchlater_gallery0_3);
//
//        lottieAnimationViewbookmark[1][0] = findViewById(R.id.watchlater_gallery1_0);
//        lottieAnimationViewbookmark[1][1] = findViewById(R.id.watchlater_gallery1_1);
//        lottieAnimationViewbookmark[1][2] = findViewById(R.id.watchlater_gallery1_2);
//        lottieAnimationViewbookmark[1][3] = findViewById(R.id.watchlater_gallery1_3);
//
//        lottieAnimationViewbookmark[2][0] = findViewById(R.id.watchlater_gallery2_0);
//        lottieAnimationViewbookmark[2][1] = findViewById(R.id.watchlater_gallery2_1);
//        lottieAnimationViewbookmark[2][2] = findViewById(R.id.watchlater_gallery2_2);
//        lottieAnimationViewbookmark[2][3] = findViewById(R.id.watchlater_gallery2_3);
//
//
//                for (int i = 0;i<3;i++){
//                    for (int j=0;j<4;j++){
//
//                        if (likes[i][j]==0){
//                            lottieAnimationViewlike[i][j].setTag("like");
//                        }else {
//                            lottieAnimationViewlike[i][j].setTag("liked");
//                        }
//                        if (bookmarks[i][j] == 0){
//                            lottieAnimationViewbookmark[i][j].setTag("bookmark");
//                        }else {
//                            lottieAnimationViewbookmark[i][j].setTag("bookmarked");
//                        }
//                    }
//
//                }
//
//                for (int i = 0;i<3;i++)
//                {
//                    for (int j = 0;j<4;j++){
//                        Log.v("like value[][]" ,"like value" + likes[i][j]);
//                        Log.v("","sdfasdf" + i + j);
//
//                    }
//                }
//
//                for (int i =0;i<3;i++){
//                    for (int j =0;j<4;j++){
//                        if (lottieAnimationViewlike[i][j].getTag()=="like"){
//                            lottieAnimationViewlike[i][j].setProgress(0);
//                        }
//                        else {
//                            lottieAnimationViewlike[i][j].setProgress(0.414f);
//                        }
//                    }
//                }
//                for (int i =0;i<3;i++){
//                    for (int j =0;j<4;j++){
//                        if (lottieAnimationViewbookmark[i][j].getTag()=="bookmark"){
//                            lottieAnimationViewbookmark[i][j].setProgress(0);
//                        }
//                        else {
//                            lottieAnimationViewbookmark[i][j].setProgress(1);
//                        }
//                    }
//                }
//            }
//
//
//        }, 3500);
//        final Handler handler3 = new Handler();
//        handler3.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                storageReference = FirebaseStorage.getInstance().getReference();
//                databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
//
//                for (int i = 0;i<3;i++){
//                    for (int j = 0;j<4;j++){
//                        databaseReference.removeValue();
//                    }
//                }
//                for (int i = 0;i<3;i++){
//                    for (int j= 0;j<4;j++){
//                        final UploadPdf uploadPdf = new UploadPdf(book_names[i][j],url_of_book[i][j],urls[i][j]);
//                        databaseReference.child("gallery"+i + "_" + j).setValue(uploadPdf);
//                    }
//                }
//
//
//            }
//        },3000);

    }

    public void like(View view) {

        final GoogleSignInAccount googleSignIn = GoogleSignIn.getLastSignedInAccount(this);

        if (googleSignIn != null) {

            final TextView likes_textView;
            TextView likes_textView2 = null;
            String id = "com.example.e_bookardberrytechnologypvtltd:id/like_gallery";  //generally an id reffered as "its_package_name"+":"+"id/"+"id"
            String temp = id;
            final LottieAnimationView lottieAnimationView = findViewById(view.getId());
            int x = 0, y = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    id = id + i + "_" + j;
                    if (id.equals(getResources().getResourceName(view.getId()))) {
                        x = i;
                        y = j;
                        break;
                    }
                    id = temp;
                }
            }

            String VIEW_NAME = "com.example.e_bookardberrytechnologypvtltd:id/no_of_likes_" + x +"_" + y;
            likes_textView2 = findViewById(getResources().getIdentifier(VIEW_NAME, "id",getPackageName()));
            likes_textView = likes_textView2;
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                    .child("Likes")
                    .child(book_id[x][y]);
            final int finalX = x;
            final int finalY = y;
            if (likes[finalX][finalY] == 0) {
                likes[finalX][finalY] = 1;
                lottieAnimationView.setProgress(0);
                lottieAnimationView.setMinAndMaxFrame(0, 29);
                lottieAnimationView.playAnimation();
                lottieAnimationView.setTag("liked");
            } else if (likes[finalX][finalY] == 1) {
                likes[finalX][finalY] = 0;
                lottieAnimationView.setProgress(0.4142f);
                lottieAnimationView.setMinAndMaxFrame(29, 70);
                lottieAnimationView.playAnimation();
                lottieAnimationView.setTag("like");
            }
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    if (lottieAnimationView.getTag().equals("liked")){
                        FirebaseDatabase.getInstance().getReference().child("Likes").child(book_id[finalX][finalY])
                                .child(googleSignIn.getId()).setValue(1);

                    }else{
                        FirebaseDatabase.getInstance().getReference().child("Likes").child(book_id[finalX][finalY])
                                .child(googleSignIn.getId()).removeValue();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {



                }
            });
            nrlikes(likes_textView,book_id[finalX][finalY]);
        }else{
            Toast.makeText(MainActivity.this,"Please Sign In To Perform This Action",Toast.LENGTH_SHORT).show();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this,Account.class);
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

    public void popUpWindow(View view){

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
        if (connected==true) {
            String temp_compare_id = "com.example.e_bookardberrytechnologypvtltd:id/gallery";
            String equal_id_temp = temp_compare_id;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    temp_compare_id = temp_compare_id + "_" + i + "_" + j;
                    if (temp_compare_id.equals(getResources().getResourceName(view.getId()))) {
                        first = i;
                        second = j;
                        break;
                    }
                    temp_compare_id = equal_id_temp;
                }
            }


            final GoogleSignInAccount googleSignIn = GoogleSignIn.getLastSignedInAccount(this);

            FirebaseDatabase databasePop = FirebaseDatabase.getInstance();

            if (googleSignIn != null) {

                DatabaseReference myref = databasePop.getReference(googleSignIn.getId()).child("Pop Up Instance Like Status");
                myref.setValue(likes[first][second]);


                DatabaseReference myRef2 = databasePop.getReference(googleSignIn.getId()).child("Pop Up Instance Bookmark Status");
                myRef2.setValue(bookmarks[first][second]);


                DatabaseReference myRef3 = databasePop.getReference(googleSignIn.getId()).child("Pop Up Instance Image Url");
                myRef3.setValue(urls[first][second]);


                DatabaseReference myRef4 = databasePop.getReference(googleSignIn.getId()).child("Pop Up Instance Book Name");
                myRef4.setValue(book_names[first][second]);

                DatabaseReference myRef5 = databasePop.getReference(googleSignIn.getId()).child("Pop Up Instance Book Like number");
                myRef5.setValue(textView_like_number[first][second].getText());

                DatabaseReference myRef6 = databasePop.getReference(googleSignIn.getId()).child("First Book Index");
                myRef6.setValue(first);

                DatabaseReference myRef7 = databasePop.getReference(googleSignIn.getId()).child("Second Book Index");
                myRef7.setValue(second);


            } else {
                DatabaseReference myref = databasePop.getReference("Unknown User").child("Pop Up Instance Like Status");
                myref.setValue(likes[first][second]);


                DatabaseReference myRef2 = databasePop.getReference("Unknown User").child("Pop Up Instance Bookmark Status");
                myRef2.setValue(bookmarks[first][second]);


                DatabaseReference myRef3 = databasePop.getReference("Unknown User").child("Pop Up Instance Image Url");
                myRef3.setValue(urls[first][second]);


                DatabaseReference myRef4 = databasePop.getReference("Unknown User").child("Pop Up Instance Book Name");
                myRef4.setValue(book_names[first][second]);

                DatabaseReference myRef5 = databasePop.getReference("Unknown User").child("Pop Up Instance Book Like number");
                myRef5.setValue(textView_like_number[first][second].getText());

                DatabaseReference myRef6 = databasePop.getReference("Unknown User").child("First Book Index");
                myRef6.setValue(first);

                DatabaseReference myRef7 = databasePop.getReference("Unknown User").child("Second Book Index");
                myRef7.setValue(second);
            }


            Intent intent = new Intent(MainActivity.this, PopUpLayout.class);
            startActivity(intent);
        }else {
            Toast.makeText(getApplicationContext(),"Please connect to an internet connection",Toast.LENGTH_LONG).show();
        }


    }


    public void bookmark(View view){

        final GoogleSignInAccount googleSignIn = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
        if (googleSignIn != null) {

        String id = "com.example.e_bookardberrytechnologypvtltd:id/watchlater_gallery";  //generally an id reffered as "its_package_name"+":"+"id/"+"id"
        String temp = id;
        final LottieAnimationView lottieAnimationView = findViewById(view.getId());
        int x=0 ,y=0;
        for (int i = 0;i<3;i++){
            for (int j = 0;j<4;j++){
                id = id + i + "_" + j;
                Log.v("bookmark", "" + i + j);
                if(id.equals(getResources().getResourceName(view.getId()))){
                    x=i;y=j;
                    break;
                }
                id=temp;
            }
        }
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                    .child("Bookmarks")
                    .child(book_id[x][y]);
            final int finalX = x;
            final int finalY = y;
        if (bookmarks[x][y]==0){
            bookmarks[x][y]=1;
            lottieAnimationView.setProgress(0);
            lottieAnimationView.setMinAndMaxFrame(0,50);
            lottieAnimationView.setSpeed(1);
            lottieAnimationView.playAnimation();
            lottieAnimationView.setTag("Bookmarked");
        }
        else if (bookmarks[x][y]==1){
            bookmarks[x][y]=0;
            lottieAnimationView.setProgress(0.14f);
            lottieAnimationView.setSpeed(-1);
            lottieAnimationView.setMinAndMaxFrame(0,7);
            lottieAnimationView.playAnimation();
            lottieAnimationView.setTag("Bookmark");
        }

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (lottieAnimationView.getTag().equals("Bookmarked")){
                        FirebaseDatabase.getInstance().getReference().child("Bookmarks").child(book_id[finalX][finalY])
                                .child(googleSignIn.getId()).setValue(1);
                        Log.v("no","dsfasdfasdfasdjkfnasdkfnkasdnfk");

                    }else{
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                FirebaseDatabase.getInstance().getReference().child("Bookmarks").child(book_id[finalX][finalY])
                                        .child(googleSignIn.getId()).removeValue();

                            }
                        },1000);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }else{


            Toast.makeText(MainActivity.this,"Please Sign In To Perform This Action",Toast.LENGTH_SHORT).show();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this,Account.class);
                    startActivity(intent);
                }
            }, 2500);

        }

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    public void getPreviousData(){
        DatabaseReference reference ;
        FirebaseDatabase firebaseDatabase;
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
        final GoogleSignInAccount googleSignIn = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
        final String userId = googleSignIn.getId();
        String id_bookmark = "com.example.e_bookardberrytechnologypvtltd:id/watchlater_gallery";  //generally an id reffered as "its_package_name"+":"+"id/"+"id"
        String temp_bookmark = id_bookmark;
        LottieAnimationView view_like;
        LottieAnimationView view_bookmark;
        String id_like = "com.example.e_bookardberrytechnologypvtltd:id/like_gallery";
        String temp_like = id_like;
        int number = 0;
        for(int i = 0 ;i<3;i++){
            for (int j=0;j<4;j++){
                id_bookmark = id_bookmark + i + "_" + j;
                id_like = id_like + i + "_" + j;
                view_like = findViewById(getResources().getIdentifier(id_like, "id",getPackageName()));
                view_bookmark = findViewById(getResources().getIdentifier(id_bookmark, "id",getPackageName()));
                number = number +1;
                final int finalJ = j;
                final int finalI = i;
                reference.child("Likes");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds :dataSnapshot.getChildren()){
                            if (ds.getKey().equals("Likes")) {
                                if (ds.child("gallery" + finalI + "_" + finalJ).child(userId).getValue()== null){
                                likes[finalI][finalJ] = 0;
                                }else{
                                    likes[finalI][finalJ] = 1;
                                }

                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                id_like =temp_like;
                id_bookmark = temp_bookmark;

            }
        }
        for(int i = 0 ;i<3;i++){
            for (int j=0;j<4;j++){
                id_bookmark = id_bookmark + i + "_" + j;
                id_like = id_like + i + "_" + j;
                view_like = findViewById(getResources().getIdentifier(id_like, "id",getPackageName()));
                view_bookmark = findViewById(getResources().getIdentifier(id_bookmark, "id",getPackageName()));
                number = number +1;
                final int finalJ = j;
                final int finalI = i;
                reference.child("Bookmarks");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds :dataSnapshot.getChildren()){
                            if (ds.getKey().equals("Bookmarks")) {
                                if (ds.child("gallery" + finalI + "_" + finalJ).child(userId).getValue()== null){
                                    bookmarks[finalI][finalJ] = 0;
                                }else{
                                    bookmarks[finalI][finalJ] = 1;
                                }

                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                id_like =temp_like;
                id_bookmark = temp_bookmark;
            }
        }
    }

    public void ReadMoreArdberry(View view){
        TextView textView  = findViewById(R.id.textDiscriptionArdberry);
        TextView textView1 = findViewById(R.id.read_more_text);
        if (read_more_status==0){
            textView.setText(R.string.ardberry_discription);
            read_more_status = 1;
            textView1.setText(R.string.read_less);
        }
        else {
            textView.setText(R.string.ardberry_discription_less);
            read_more_status = 0;
            textView1.setText(R.string.read_more);
        }
    }
    public void findHighestFourBooks(){

        final GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        final String[] bookName = new String[1];
        final String[] bookUrl = new String[1];
        Book_id_highest = book_Id[0][0];
        int tempi=0,tempj=0;
        int temp = number_of_likes_of_each_book[0][0];
        for (int i = 0;i<3;i++){
            for (int j=0;j<4;j++){
                if (number_of_likes_of_each_book[i][j]>temp){
                    temp = number_of_likes_of_each_book[i][j];
                    Book_id_highest = book_Id[i][j];
                    tempi = i;
                    tempj = j;
                }
            }
        }

        like1_most_liked.setTag("like");
        like2_most_liked.setTag("like");
        like3_most_liked.setTag("like");
        like4_most_liked.setTag("like");
        bookmark1_most_liked.setTag("bookmark");
        bookmark2_most_liked.setTag("bookmark");
        bookmark3_most_liked.setTag("bookmark");
        bookmark4_most_liked.setTag("bookmark");

        if (googleSignInAccount!=null) {
            DatabaseReference referenceLikes = FirebaseDatabase.getInstance().getReference().child("Likes").child("gallery"+tempi+"_"+tempj).child(googleSignInAccount.getId());
            referenceLikes.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        like1_most_liked.setTag("liked");
                    }else {
                        like1_most_liked.setTag("like");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



            DatabaseReference referenceBookmarks = FirebaseDatabase.getInstance().getReference().child("Bookmarks").child("gallery"+tempi+"_"+tempj).child(googleSignInAccount.getId());
            referenceBookmarks.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                    String value = dataSnapshot.getValue().toString();
                    if (value==null){
                        bookmark1_most_liked.setTag("bookmark");
                    }else {
                        bookmark1_most_liked.setTag("bookmarked");
                    }
                    }else {
                        bookmark1_most_liked.setTag("bookmark");
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        number_of_likes_most_liked_book1.setText(""+ temp+ " likes");
        DatabaseReference referenceBooks1 = FirebaseDatabase.getInstance().getReference().child("uploads").child("gallery" + tempi + "_" + tempj);
        referenceBooks1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UploadPdf mostLikedClass1 = dataSnapshot.getValue(UploadPdf.class);
                bookName[0] = mostLikedClass1.getName();
                bookUrl[0] = mostLikedClass1.getImage_url();
                bookName1_most_liked.setText(bookName[0]);

                Glide.with(getApplicationContext()).load(bookUrl[0]).into(imageView1_most_liked);
                Log.v("details",bookName[0] + "    " + bookUrl[0]);}


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        number_of_likes_of_each_book[tempi][tempj] = -1;

        Book_id_second_highest = book_Id[0][0];
        temp = number_of_likes_of_each_book[0][0];
        tempi=0;tempj=0;
        for (int i = 0;i<3;i++){
            for (int j=0;j<4;j++){
                if (number_of_likes_of_each_book[i][j]>temp){
                    temp = number_of_likes_of_each_book[i][j];
                    Book_id_second_highest = book_Id[i][j];
                    tempi = i;
                    tempj = j;
                }
            }
        }
        if (googleSignInAccount!=null) {
            DatabaseReference referenceLikes = FirebaseDatabase.getInstance().getReference().child("Likes").child("gallery"+tempi+"_"+tempj).child(googleSignInAccount.getId());
            referenceLikes.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        like2_most_liked.setTag("liked");
                    }else {
                        like2_most_liked.setTag("like");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            DatabaseReference referenceBookmarks = FirebaseDatabase.getInstance().getReference().child("Bookmarks").child("gallery"+tempi+"_"+tempj).child(googleSignInAccount.getId());
            referenceBookmarks.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        bookmark2_most_liked.setTag("bookmarked");
                    }else {
                        bookmark2_most_liked.setTag("bookmark");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        number_of_likes_most_liked_book2.setText(""+ temp+ " likes");
        DatabaseReference referenceBooks2 = FirebaseDatabase.getInstance().getReference().child("uploads").child("gallery" + tempi + "_" + tempj);
        referenceBooks2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UploadPdf mostLikedClass = dataSnapshot.getValue(UploadPdf.class);
                bookName[0] = mostLikedClass.getName();
                bookUrl[0] = mostLikedClass.getImage_url();
                bookName2_most_liked.setText(bookName[0]);
                Glide.with(getApplicationContext()).load(bookUrl[0]).into(imageView2_most_liked);
                Log.v("details",bookName[0] + "    " + bookUrl[0]);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        number_of_likes_of_each_book[tempi][tempj] = -1;

        Book_id_third_highest = book_Id[0][0];
        temp = number_of_likes_of_each_book[0][0];
        tempi=0;tempj=0;
        for (int i = 0;i<3;i++){
            for (int j=0;j<4;j++){
                if (number_of_likes_of_each_book[i][j]>temp){
                    temp = number_of_likes_of_each_book[i][j];
                    Book_id_third_highest = book_Id[i][j];
                    tempi = i;
                    tempj = j;
                }
            }
        }
        if (googleSignInAccount!=null) {
            DatabaseReference referenceLikes = FirebaseDatabase.getInstance().getReference().child("Likes").child("gallery"+tempi+"_"+tempj).child(googleSignInAccount.getId());
            referenceLikes.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        like3_most_liked.setTag("liked");
                    }else {
                        like3_most_liked.setTag("like");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            DatabaseReference referenceBookmarks = FirebaseDatabase.getInstance().getReference().child("Bookmarks").child("gallery"+tempi+"_"+tempj).child(googleSignInAccount.getId());
            referenceBookmarks.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        bookmark3_most_liked.setTag("bookmarked");
                    }else {
                        bookmark3_most_liked.setTag("bookmark");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        number_of_likes_most_liked_book3.setText(""+ temp+ " likes");
        DatabaseReference referenceBooks3 = FirebaseDatabase.getInstance().getReference().child("uploads").child("gallery" + tempi + "_" + tempj);
        referenceBooks3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UploadPdf mostLikedClass = dataSnapshot.getValue(UploadPdf.class);
                bookName[0] = mostLikedClass.getName();
                bookUrl[0] = mostLikedClass.getImage_url();
                bookName3_most_liked.setText(bookName[0]);
                Glide.with(getApplicationContext()).load(bookUrl[0]).into(imageView3_most_liked);
                Log.v("details",bookName[0] + "    " + bookUrl[0]);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        number_of_likes_of_each_book[tempi][tempj] = -1;

        Book_id_forth_highest = book_Id[0][0];
        temp = number_of_likes_of_each_book[0][0];
        tempi=0;tempj=0;
        for (int i = 0;i<3;i++){
            for (int j=0;j<4;j++){
                if (number_of_likes_of_each_book[i][j]>temp){
                    temp = number_of_likes_of_each_book[i][j];
                    Book_id_forth_highest = book_Id[i][j];
                    tempi = i;
                    tempj = j;
                }
            }
        }
        if (googleSignInAccount!=null) {
            DatabaseReference referenceLikes = FirebaseDatabase.getInstance().getReference().child("Likes").child("gallery"+tempi+"_"+tempj).child(googleSignInAccount.getId());
            referenceLikes.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        like4_most_liked.setTag("liked");
                    }else {
                        like4_most_liked.setTag("like");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            DatabaseReference referenceBookmarks = FirebaseDatabase.getInstance().getReference().child("Bookmarks").child("gallery"+tempi+"_"+tempj).child(googleSignInAccount.getId());
            referenceBookmarks.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        bookmark4_most_liked.setTag("bookmarked");
                    }else {
                        bookmark4_most_liked.setTag("bookmark");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        number_of_likes_most_liked_book4.setText(""+ temp+ " likes");
        DatabaseReference referenceBooks4 = FirebaseDatabase.getInstance().getReference().child("uploads").child("gallery" + tempi + "_" + tempj);
        referenceBooks4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UploadPdf mostLikedClass = dataSnapshot.getValue(UploadPdf.class);
                bookName[0] = mostLikedClass.getName();
                bookUrl[0] = mostLikedClass.getImage_url();
                bookName4_most_liked.setText(bookName[0]);
                Glide.with(getApplicationContext()).load(bookUrl[0]).into(imageView4_most_liked);
                Log.v("details",bookName[0] + "    " + bookUrl[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (like1_most_liked.getTag()=="like"){
                    like1_most_liked.setProgress(0);
                }else {
                    like1_most_liked.setProgress(0.414f);
                }
                if (like2_most_liked.getTag()=="like"){
                    like2_most_liked.setProgress(0);
                }else {
                    like2_most_liked.setProgress(0.414f);
                }
                if (like3_most_liked.getTag()=="like"){
                    like3_most_liked.setProgress(0);
                }else {
                    like3_most_liked.setProgress(0.414f);
                }
                if (like4_most_liked.getTag()=="like"){
                    like4_most_liked.setProgress(0);
                }else {
                    like4_most_liked.setProgress(0.414f);
                }
                if (bookmark1_most_liked.getTag()=="bookmark"){
                    bookmark1_most_liked.setProgress(0);
                }else {
                    bookmark1_most_liked.setProgress(1);
                }
                if (bookmark2_most_liked.getTag()=="bookmark"){
                    bookmark2_most_liked.setProgress(0);
                }else {
                    bookmark2_most_liked.setProgress(1);
                }
                if (bookmark3_most_liked.getTag()=="bookmark"){
                    bookmark3_most_liked.setProgress(0);
                }else {
                    bookmark3_most_liked.setProgress(1);
                }
                if (bookmark4_most_liked.getTag()=="bookmark"){
                    bookmark4_most_liked.setProgress(0);
                }else {
                    bookmark4_most_liked.setProgress(1);
                }


            }
        }, 0);
        Log.v("MOST LIKES 1",Book_id_highest);
        Log.v("MOST LIKES 2",Book_id_second_highest);
        Log.v("MOST LIKES 3",Book_id_third_highest);
        Log.v("MOST LIKES 4",Book_id_forth_highest);


    }


    @Override
    protected void onPause() {


        super.onPause();
    }

    public void AccountFromHome(MenuItem item){
        Intent intent  = new Intent(this,Account.class);
        startActivity(intent);
    }
    public void HomeFromHome(MenuItem item){
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }
    public void FeedbackFromHome(MenuItem item){
        Intent intent  = new Intent(this,Feedback.class);
        startActivity(intent);
    }

    public void read0(View view){
        Intent intent = new Intent(MainActivity.this,bookPdf.class);
        intent.putExtra(Constant.BOOK,Constant.NUMBER1);
        startActivity(intent);
    }
    public void read1(View view){
        Intent intent = new Intent(MainActivity.this,bookPdf.class);
        intent.putExtra(Constant.BOOK,Constant.NUMBER2);
        startActivity(intent);
    }
    public void read2(View view){
        Intent intent = new Intent(MainActivity.this,bookPdf.class);
        intent.putExtra(Constant.BOOK,Constant.NUMBER3);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        sliderLayout.stopAutoCycle();
        super.onStop();
    }
}
