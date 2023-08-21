package com.example.appbanlinhkien30.model;

import java.util.Date;
import java.util.List;

public class Order {
    private String userId;
    private Date purchaseDate;
    private List<Cart> cartList;
    private String id;


    private String totalPrice;

    public Order() {
    }

    public Order(String userId, Date purchaseDate, List<Cart> cartList, String id) {
        this.userId = userId;
        this.purchaseDate = purchaseDate;
        this.cartList = cartList;
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
