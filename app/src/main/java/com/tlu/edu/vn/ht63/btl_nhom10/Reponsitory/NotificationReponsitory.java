package com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Notification;
import com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.Dao.NotificationDao;

import java.util.ArrayList;
import java.util.List;

public class NotificationReponsitory implements NotificationDao {
    private Context context;
    FirebaseDatabase database;
    DatabaseReference reference;
    public NotificationReponsitory(Context context) {
        this.context = context;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Notifications");
    }
    @Override
    public List<Notification> getNOtiByRecieverId(int id) {
        List<Notification> notifications = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notifications.clear();
                for(DataSnapshot item : snapshot.getChildren()){
                    if(item.child("recieverId").getValue(Integer.class) == id)
                        notifications.add(item.getValue(Notification.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return notifications;
    }

    @Override
    public void insert(Notification notification) {
        DatabaseReference newNode = reference.push();
        notification.setNotificationId(newNode.getKey());
        newNode.setValue(notification);
    }

    @Override
    public void update(Notification notification) {
        reference.child(notification.getNotificationId()).setValue(notification);
    }
}
