package com.tlu.edu.vn.ht63.btl_nhom10.Model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private boolean isAdmin;
    private String createdAt;

    private String address;

    public User(){}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }


    @Ignore
    public User(String email, String name, String password, String phoneNumber){
        this.email = email;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
    @Ignore
    public User(int id, String name, String phoneNumber, String email, String password) {
        this.phoneNumber = phoneNumber;
        this.userId = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isValidEmail() {
        if (this.email == null) return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this.email).matches();
    }

    public boolean isValidPhoneNumber() {
        if (this.phoneNumber == null) return false;
        return android.util.Patterns.PHONE.matcher(this.phoneNumber).matches();
    }

    public boolean isValidPassword() {
        if (this.password == null) return false;
        return this.password.length() >= 8;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
