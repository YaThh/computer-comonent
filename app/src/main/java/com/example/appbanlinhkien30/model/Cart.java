package com.example.appbanlinhkien30.model;

public class Cart {
    private String ProductName;
    private String ProductPrice;
    private String CurrentDate;
    private String CurrentTime;
    private String TotalQuantity;
    private String TotalPrice;
    public Cart() {
    }

    public Cart(String productName, String productPrice, String currentDate, String currentTime, String totalQuantity, String totalPrice) {
        ProductName = productName;
        ProductPrice = productPrice;
        CurrentDate = currentDate;
        CurrentTime = currentTime;
        TotalQuantity = totalQuantity;
        TotalPrice = totalPrice;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getCurrentDate() {
        return CurrentDate;
    }

    public void setCurrentDate(String currentDate) {
        CurrentDate = currentDate;
    }

    public String getCurrentTime() {
        return CurrentTime;
    }

    public void setCurrentTime(String currentTime) {
        CurrentTime = currentTime;
    }

    public String getTotalQuantity() {
        return TotalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        TotalQuantity = totalQuantity;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }
}
