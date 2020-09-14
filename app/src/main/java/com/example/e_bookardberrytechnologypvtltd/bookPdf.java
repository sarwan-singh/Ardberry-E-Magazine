package com.example.e_bookardberrytechnologypvtltd;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebBackForwardList;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;

public class bookPdf extends AppCompatActivity {

    private PDFView pdfView;
    private ProgressBar progressBar;
    private String current_url;
    private String get_string;
    private String[] url = new String[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_pdf);

        url = new String[]{
                "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/Books%2Fbook0_0.pdf?alt=media&token=2627c4c2-623f-4944-98b9-96fa56435723",
                "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/Books%2Fbook1_0.pdf?alt=media&token=5454dc22-28f2-4723-96f0-23ad2ce12f3e",
                "https://firebasestorage.googleapis.com/v0/b/e-book-ardberry.appspot.com/o/Books%2Fbook2_0.pdf?alt=media&token=99af8af1-1462-41cf-b800-6ef8e08328ba"
        };

        if (savedInstanceState== null){
            Bundle extras = getIntent().getExtras();
            if (extras==null){
                get_string = "null";
            }
            else {
                get_string = extras.getString(Constant.BOOK);
            }
        }else {
            get_string = (String)savedInstanceState.getSerializable(Constant.BOOK);
        }
        Log.v("dsf","sdf " + get_string);

        switch (get_string){
            case "1":current_url=url[0];
            break;
            case "2":current_url=url[1];
            break;
            case "3":current_url=url[2];
            break;
            default:current_url=url[0];
        }

        pdfView = findViewById(R.id.pdfViewer);

        progressBar = findViewById(R.id.progressbar);

        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids){
                try {
                    InputStream input = new URL(current_url).openStream();
                    pdfView.fromStream(input).spacing(40).onLoad(new OnLoadCompleteListener() {
                        @Override
                        public void loadComplete(int nbPages) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }).swipeHorizontal(true).load();

                }catch (IOException e){
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
}
