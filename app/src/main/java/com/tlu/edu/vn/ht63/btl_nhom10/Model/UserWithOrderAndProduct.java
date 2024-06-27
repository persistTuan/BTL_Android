package com.tlu.edu.vn.ht63.btl_nhom10.Model;

import android.util.Pair;

import java.util.List;

public class UserWithOrderAndProduct {
    public User user;
    public Order order;
    private List<Pair<Product, Integer> > products;

    public List<Pair<Product, Integer>> getProducts() {
        return products;
    }

    public void setProducts(List<Pair<Product, Integer>> products) {
        this.products = products;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
