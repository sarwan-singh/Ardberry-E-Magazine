package com.example.e_bookardberrytechnologypvtltd;

public class UploadPdf {

    public String name;
    public String url;
    public String image_url;
    public UploadPdf(){

    }

    public UploadPdf(String name, String url, String image_url) {
        this.name = name;
        this.url = url;
        this.image_url = image_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
