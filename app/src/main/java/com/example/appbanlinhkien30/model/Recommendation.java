package com.example.appbanlinhkien30.model;

public class Recommendation {
    private String Name;
    private String Type;
    private String ImgUrl;
    private String Rating;
    private String Price;

    public Recommendation() {
    }

    public Recommendation(String name, String type, String imgUrl, String rating, String price) {
        Name = name;
        Type = type;
        ImgUrl = imgUrl;
        Rating = rating;
        Price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
