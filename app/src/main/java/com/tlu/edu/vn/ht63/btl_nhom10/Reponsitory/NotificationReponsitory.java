package com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Notification;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Notification> notifications = new ArrayList<>();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime currentTime = LocalDateTime.now();
                LocalDateTime fiveDaysAgo = currentTime.minusDays(5);
                for(DataSnapshot item : snapshot.getChildren()){
                    Notification notification = item.getValue(Notification.class);
                    if(notification.getReceiverId() == id){
                        LocalDateTime created_at = LocalDateTime.parse(notification.getCreatedAt(), formatter);
                        if(created_at.isAfter(fiveDaysAgo) && created_at.isBefore(currentTime.plusDays(1))){
                            notifications.add(notification);
                        }
                    }
                }
                callback.onGetData(notifications);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        reference.addListenerForSingleValueEvent(listener);
    }

    public ChildEventListener getNewNotificationListener(int userId, OnGetDataNotificationListener callback){
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Notification notification = snapshot.getValue(Notification.class);
                assert notification != null;
                if(notification.getReceiverId() == userId)
                    callback.onGetNewNotification(snapshot.getValue(Notification.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Notification notification = snapshot.getValue(Notification.class);
                assert notification != null;
                if(notification.getReceiverId() == userId)
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

    // async
    public void insert(Notification notification) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                DatabaseReference newNode = reference.push();
                notification.setNotificationId(newNode.getKey());
                newNode.setValue(notification);
            }
        });

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
