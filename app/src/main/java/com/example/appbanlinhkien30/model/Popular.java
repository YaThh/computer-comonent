package com.example.appbanlinhkien30.model;

public class Popular {
    String Name;
    String Description;
    String Rating;
    String Discount;
    String Type;
    String ImgUrl;

    public Popular() {
    }

    public Popular(String name, String description, String rating, String discount, String type, String imgUrl) {
        Name = name;
        Description = description;
        Rating = rating;
        Discount = discount;
        Type = type;
        ImgUrl = imgUrl;
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

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
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
}
