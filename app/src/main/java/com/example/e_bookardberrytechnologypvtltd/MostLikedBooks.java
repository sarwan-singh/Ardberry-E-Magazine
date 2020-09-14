package com.example.e_bookardberrytechnologypvtltd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

import static java.lang.Integer.parseInt;

public class MostLikedBooks extends AppCompatActivity {


    private int[][] number_of_likes_of_each_book = new int[3][4];
    private String[][] book_Id = new String[3][4];
    private String Book_id_highest,Book_id_second_highest,Book_id_third_highest,Book_id_forth_highest;
    private ImageView imageView1,imageView2,imageView3,imageView4;
    private TextView bookName1,bookName2,bookName3,bookName4;
    private LottieAnimationView like1,like2,like3,like4,bookmark1,bookmark2,bookmark3,bookmark4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_most_liked_books);

        bookName1 = findViewById(R.id.bookname_gallery_most_liked_0);
        bookName2 = findViewById(R.id.bookname_gallery_most_liked_1);
        bookName3 = findViewById(R.id.bookname_gallery_most_liked_2);
        bookName4 = findViewById(R.id.bookname_gallery_most_liked_3);

        imageView1 = findViewById(R.id.gallery_image_most_liked_0);
        imageView2 = findViewById(R.id.gallery_image_most_liked_1);
        imageView3 = findViewById(R.id.gallery_image_most_liked_2);
        imageView4 = findViewById(R.id.gallery_image_most_liked_3);
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
        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                findHighestFourBooks();
            }
        },3000);




    }

    public void findHighestFourBooks(){

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
        DatabaseReference referenceBooks1 = FirebaseDatabase.getInstance().getReference().child("uploads").child("gallery" + tempi + "_" + tempj);
        referenceBooks1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mostLikedClass mostLikedClass = dataSnapshot.getValue(com.example.e_bookardberrytechnologypvtltd.mostLikedClass.class);
                bookName[0] = mostLikedClass.getBookName();
                bookUrl[0] = mostLikedClass.getBookUrl();
                bookName1.setText(bookName[0]);
                Glide.with(getApplicationContext()).load(bookUrl[0]).into(imageView1);
            }

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
        DatabaseReference referenceBooks2 = FirebaseDatabase.getInstance().getReference().child("uploads").child("gallery" + tempi + "_" + tempj);
        referenceBooks2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mostLikedClass mostLikedClass = dataSnapshot.getValue(com.example.e_bookardberrytechnologypvtltd.mostLikedClass.class);
                bookName[0] = mostLikedClass.getBookName();
                bookUrl[0] = mostLikedClass.getBookUrl();
                bookName2.setText(bookName[0]);
                Glide.with(getApplicationContext()).load(bookUrl[0]).into(imageView2);
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
        DatabaseReference referenceBooks3 = FirebaseDatabase.getInstance().getReference().child("uploads").child("gallery" + tempi + "_" + tempj);
        referenceBooks3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mostLikedClass mostLikedClass = dataSnapshot.getValue(com.example.e_bookardberrytechnologypvtltd.mostLikedClass.class);
                bookName[0] = mostLikedClass.getBookName();
                bookUrl[0] = mostLikedClass.getBookUrl();
                bookName3.setText(bookName[0]);
                Glide.with(getApplicationContext()).load(bookUrl[0]).into(imageView3);
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
        DatabaseReference referenceBooks4 = FirebaseDatabase.getInstance().getReference().child("uploads").child("gallery" + tempi + "_" + tempj);
        referenceBooks4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mostLikedClass mostLikedClass = dataSnapshot.getValue(com.example.e_bookardberrytechnologypvtltd.mostLikedClass.class);
                bookName[0] = mostLikedClass.getBookName();
                bookUrl[0] = mostLikedClass.getBookUrl();
                bookName4.setText(bookName[0]);
                Glide.with(getApplicationContext()).load(bookUrl[0]).into(imageView4);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        Log.v("MOST LIKES 1",Book_id_highest);
        Log.v("MOST LIKES 2",Book_id_second_highest);
        Log.v("MOST LIKES 3",Book_id_third_highest);
        Log.v("MOST LIKES 4",Book_id_forth_highest);

    }
}
