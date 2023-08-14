package com.example.appbanlinhkien30.model;

public class HomeCategory {
    private String Name;
    private String Type;
    private String ImgUrl;

    public HomeCategory() {
    }

    public HomeCategory(String name, String type, String imgUrl) {
        Name = name;
        Type = type;
        ImgUrl = imgUrl;
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
}
