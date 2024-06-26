package com.tlu.edu.vn.ht63.btl_nhom10.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;



public class CartAndUserWithProducts implements Parcelable {
    private int cartId;
    private int userId;
    private Product product;
    private int quantity;

    public CartAndUserWithProducts(int cartId, int userId, Product product, int quantity) {
        this.userId = userId;
        this.product = product;
        this.quantity = quantity;
        this.cartId = cartId;
    }

    protected CartAndUserWithProducts(Parcel in) {
        cartId = in.readInt();
        userId = in.readInt();
        product = in.readParcelable(Product.class.getClassLoader());
        quantity = in.readInt();
    }

    public static final Creator<CartAndUserWithProducts> CREATOR = new Creator<CartAndUserWithProducts>() {
        @Override
        public CartAndUserWithProducts createFromParcel(Parcel in) {
            return new CartAndUserWithProducts(in);
        }

        @Override
        public CartAndUserWithProducts[] newArray(int size) {
            return new CartAndUserWithProducts[size];
        }
    };

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(cartId);
        dest.writeInt(userId);
        dest.writeParcelable(product, flags);
        dest.writeInt(quantity);
    }
}
