package com.tlu.edu.vn.ht63.btl_nhom10.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

public class Product implements Parcelable {
    private String productId;
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

    protected Product(Parcel in) {
        productId = in.readString();
        productName = in.readString();
        productDescription = in.readString();
        productPrice = in.readFloat();
        productCategory = in.readString();
        productDiscount = in.readFloat();
        productImage = in.readString();
        numberOfBuyers = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(productId);
        dest.writeString(productName);
        dest.writeString(productDescription);
        dest.writeFloat(productPrice);
        dest.writeString(productCategory);
        dest.writeFloat(productDiscount);
        dest.writeString(productImage);
        dest.writeInt(numberOfBuyers);
    }
}
