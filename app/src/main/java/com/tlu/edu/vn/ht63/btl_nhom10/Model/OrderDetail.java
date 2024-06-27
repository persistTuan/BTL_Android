package com.tlu.edu.vn.ht63.btl_nhom10.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = { "orderId", "productId" })
public class OrderDetail {
    private int orderId;
    @NonNull
    private String productId;
    private int quantity;

    public OrderDetail(int orderId, String productId, int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
