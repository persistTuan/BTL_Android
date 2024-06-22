package com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.Dao;

import com.tlu.edu.vn.ht63.btl_nhom10.Model.Notification;

import java.util.List;

public interface NotificationDao {
    List<Notification> getNOtiByRecieverId(int id);
    void insert(Notification notification);
    void update(Notification notification);
}
