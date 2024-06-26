package com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Notification;


import java.util.ArrayList;
import java.util.List;

public class NotificationReponsitory{
    private Context context;
    FirebaseDatabase database;
    DatabaseReference reference;
    public NotificationReponsitory(Context context) {
        this.context = context;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Notifications");
    }
    public void getNOtiByRecieverId(int id, OnGetDataNotificationListener callback) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Notification> notifications = new ArrayList<>();
                for(DataSnapshot item : snapshot.getChildren()){
                    if(item.child("recieverId").getValue(Integer.class) == id)
                        notifications.add(item.getValue(Notification.class));
                }
                callback.onGetData(notifications);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        reference.addValueEventListener(listener);
    }

    public ChildEventListener getNewNotificationListener(OnGetDataNotificationListener callback){
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                callback.onGetNewNotification(snapshot.getValue(Notification.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                callback.onNotificationUpdated(snapshot.getValue(Notification.class));
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        reference.addChildEventListener(childEventListener);
        return childEventListener;
    }

    public void insert(Notification notification) {
        DatabaseReference newNode = reference.push();
        notification.setNotificationId(newNode.getKey());
        newNode.setValue(notification);
    }

    public void update(Notification notification) {
        reference.child(notification.getNotificationId()).setValue(notification);
    }

    public interface OnDataChangeNotificationListener{
        void onDataChange(boolean success);
    }

    public interface OnGetDataNotificationListener{
        void onGetData(List<Notification> notifications);
        void onGetNewNotification(Notification notification);
        void onNotificationUpdated(Notification notification);
    }
}
