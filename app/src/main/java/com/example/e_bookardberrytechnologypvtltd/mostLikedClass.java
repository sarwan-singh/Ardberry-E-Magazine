package com.example.e_bookardberrytechnologypvtltd;

public class mostLikedClass {
    private String BookName,BookUrl;

    public mostLikedClass(String bookName, String bookUrl) {
        BookName = bookName;
        BookUrl = bookUrl;
    }

    public mostLikedClass() {
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getBookUrl() {
        return BookUrl;
    }

    public void setBookUrl(String bookUrl) {
        BookUrl = bookUrl;
    }
}
