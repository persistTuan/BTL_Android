package com.tlu.edu.vn.ht63.btl_nhom10.Model;

import java.util.List;

public class CartAndUserWithProducts {
    private int userId;
    private List<Product> products;

    public int getUser() {
        return userId;
    }

    public void setUser(int user) {
        this.userId = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
