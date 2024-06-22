package com.tlu.edu.vn.ht63.btl_nhom10.Model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

public class Product {
    private int productId;
    private String productName;
    private String productDescription;
    private float productPrice;
    private String productCategory;
    @ColumnInfo(defaultValue = "0")
    private float productDiscount;
    private String productImage;
    @ColumnInfo(defaultValue = "0")
    private int numberOfBuyers;

    public Product(){}

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public float getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(float productDiscount) {
        this.productDiscount = productDiscount;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getNumberOfBuyers() {
        return numberOfBuyers;
    }

    public void setNumberOfBuyers(int numberOfBuyers) {
        this.numberOfBuyers = numberOfBuyers;
    }
}
