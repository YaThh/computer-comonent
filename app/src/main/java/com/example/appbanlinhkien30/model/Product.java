package com.example.appbanlinhkien30.model;

import java.io.Serializable;

public class Product implements Serializable {
    private String Name;
    private String Description;
    private String Type;
    private String ImgUrl;
    private String Price;
    private String Rating;

    public Product() {
    }

    public Product(String name, String description, String type, String imgUrl, String price, String rating) {
        Name = name;
        Description = description;
        Type = type;
        ImgUrl = imgUrl;
        Price = price;
        Rating = rating;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }
}
