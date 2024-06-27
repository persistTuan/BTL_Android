package com.tlu.edu.vn.ht63.btl_nhom10;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Notification;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Order;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Product;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.User;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.UserWithOrderAndProduct;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.NotificationReponsitory;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.OrderReponsitory;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.ProductReponsitory;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.UserReponsitory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;


public class NotificationViewModel extends ViewModel {
    private UserReponsitory userReponsitory;
    public MutableLiveData<Boolean> hideBadge = new MutableLiveData<>();
    public MutableLiveData<Integer> countNewNotification = new MutableLiveData<>(0);
    private boolean isLoadingOldNotification;
    private ProductReponsitory productReponsitory;
    private Notification notificationSender;

    private Notification notiFeedback;


    private NotificationReponsitory notificationReponsitory;
    private OrderReponsitory orderReponsitory;
    private User userCurrent;
    public ChildEventListener childEventListener;
    public MutableLiveData<Boolean> isAcceptOrder = new MutableLiveData<>();
    public MutableLiveData<Notification> newNotification = new MutableLiveData<>();
    public MutableLiveData<UserWithOrderAndProduct> obeserveOrderDetail = new MutableLiveData<>();
    public MutableLiveData<List<Notification>> listNotification = new MutableLiveData<>();

    public NotificationViewModel(Context context){
        notificationReponsitory = new NotificationReponsitory(context);
        orderReponsitory = new OrderReponsitory(context);
        userReponsitory = new UserReponsitory(context);
        productReponsitory = new ProductReponsitory(context);
        userCurrent = userReponsitory.getUserCurrent();

    }

    public void loadNotification(){
        listNotification.setValue(new ArrayList<Notification>());
        isLoadingOldNotification = false;
          notificationReponsitory.getNOtiByRecieverId(userCurrent.getUserId(), new NotificationReponsitory.OnGetDataNotificationListener() {
              @Override
              public void onGetData(List<Notification> notifications) {
                  listNotification.setValue(notifications);
              }

              @Override
              public void onGetNewNotification(Notification notification) {

              }

              @Override
              public void onNotificationUpdated(Notification notification) {

              }
          });
          isLoadingOldNotification = false;
    }

    public void startListeningForNotification(){
        this.childEventListener = notificationReponsitory.getNewNotificationListener(userCurrent.getUserId(), new NotificationReponsitory.OnGetDataNotificationListener() {
            @Override
            public void onGetData(List<Notification> notifications) {

            }

            @Override
            public void onGetNewNotification(Notification notification) {
                Log.d("isNew", notification.isNew() + "");
                if(notification.isNew()){
                    newNotification.setValue(notification);
                    countNewNotification.setValue(countNewNotification.getValue() + 1);
                }
            }

            @Override
            public void onNotificationUpdated(Notification notification) {

            }
        });
    }



    public OrderReponsitory getOrderReponsitory() {
        return orderReponsitory;
    }


    public void showOrderDetail(Notification notification){
        notificationSender = notification;
        orderReponsitory.getOrderDetail(notification.getSenderId(), notification.getOrderId(), new OrderReponsitory.OnListenerOrderDetail() {
            @Override
            public void onSuccess(UserWithOrderAndProduct orderDetails) {
                obeserveOrderDetail.setValue(orderDetails);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    public void acceptOrder(UserWithOrderAndProduct userWithOrderAndProduct){
        Order order = userWithOrderAndProduct.getOrder();
        order.setAccepted(true);
        orderReponsitory.update(order, new OrderReponsitory.OnListenerOrder() {
            @Override
            public void OnSuccess(Boolean success) {
                if(success){
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            loadNotification();
                            sendNotiToCustomer(notificationSender);
                        }
                    });

                    List<Pair<Product, Integer>> pairProduct = userWithOrderAndProduct.getProducts();
                    for (Pair<Product, Integer> pair : pairProduct) {
                        Product product = pair.first;
                        int quantity = product.getNumberOfBuyers() + pair.second;
                        product.setNumberOfBuyers(quantity);
                        productReponsitory.update(product, new ProductReponsitory.ChangeProductCallback() {
                            @Override
                            public void changeCallback(boolean success) {

                            }
                        });
                    }

                }
            }
        });

    }

    public void sendNotiToCustomer(Notification notiSender){
        Notification feedback = new Notification();
        feedback.setSenderId(userCurrent.getUserId());
        feedback.setReceiverId(notiSender.getSenderId());
        feedback.setMessage("Đơn hàng đã được xác nhận");
        feedback.setTitle("Cập Nhật Đơn Hàng");
        feedback.setNew(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            feedback.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        feedback.setOrderId(notiSender.getOrderId());

        notificationReponsitory.insert(feedback);
    }

    public void updateNotification(Notification notification){
        notificationReponsitory.update(notification);
    }
}
