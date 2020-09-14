package com.example.e_bookardberrytechnologypvtltd;

public class BookItem {

    private String Image_Url;
    private int Like_Status;
    private int Bookmark_Status;
    private String child_id;
    private int no_of_likes;

    public BookItem(String image_Url, int like_Status, int bookmark_Status, String Book_id,int number_of_likes) {
        Image_Url = image_Url;
        Like_Status = like_Status;
        Bookmark_Status = bookmark_Status;
        child_id = Book_id;
        no_of_likes = number_of_likes;

    }

    public BookItem() {
    }

    public int getNo_of_likes(){return no_of_likes;}

    public void setNo_of_likes(int number_of_likes){ no_of_likes = number_of_likes; }

    public String getImage_Url() {
        return Image_Url;
    }

    public String getChild_id(){
        return child_id;
    }

    public void setChild_id(String childId){
        child_id = childId;
    }

    public void setImage_Url(String image_Url) {
        Image_Url = image_Url;
    }

    public int getLike_Status() {
        return Like_Status;
    }

    public void setLike_Status(int like_Status) {
        Like_Status = like_Status;
    }

    public int getBookmark_Status() {
        return Bookmark_Status;
    }

    public void setBookmark_Status(int bookmark_Status) {
        Bookmark_Status = bookmark_Status;
    }
}
