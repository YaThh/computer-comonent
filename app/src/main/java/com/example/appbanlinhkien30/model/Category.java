package com.example.appbanlinhkien30.model;

public class Category {
    private String Name;
    private String ImgUrl;
    private String Description;

    public Category() {
    }

    public Category(String name, String imgUrl, String description) {
        Name = name;
        ImgUrl = imgUrl;
        Description = description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
