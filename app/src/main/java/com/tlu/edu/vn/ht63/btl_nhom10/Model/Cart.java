package com.tlu.edu.vn.ht63.btl_nhom10.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "carts")
public class Cart {
    @PrimaryKey(autoGenerate = true)
    private int cartId;
    private int userId;
    private String productId;
    private int quantity;
    public Cart(){};

    public Cart(int userId, String productId) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = 1;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

}
