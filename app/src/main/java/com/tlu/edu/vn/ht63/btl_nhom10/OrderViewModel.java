package com.tlu.edu.vn.ht63.btl_nhom10;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tlu.edu.vn.ht63.btl_nhom10.Model.Cart;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.CartAndUserWithProducts;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Notification;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Order;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.OrderDetail;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.User;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.CartReponsitory;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.NotificationReponsitory;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.OrderReponsitory;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.UserReponsitory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderViewModel extends ViewModel {
    private UserReponsitory userReponsitory;
    private OrderReponsitory orderReponsitory;
    private CartReponsitory cartReponsitory;
    private NotificationReponsitory notificationReponsitory;
    public MutableLiveData<Boolean> noAddress = new MutableLiveData<>();
    public MutableLiveData<Boolean> progressBar = new MutableLiveData<>(false);
    public MutableLiveData<String> totalPrice = new MutableLiveData<>();
    public MutableLiveData<Boolean> createOrderSuccess = new MutableLiveData<>();
    private final String TITLE_NEW_ORDER = "Đơn hàng mới";
    private User userCurrent;

    public OrderViewModel(Context context){
        userReponsitory = new UserReponsitory(context);
        cartReponsitory = new CartReponsitory(context);
        orderReponsitory = new OrderReponsitory(context);
        notificationReponsitory = new NotificationReponsitory(context);
        userCurrent = userReponsitory.getUserCurrent();
        if(userCurrent.getAddress() == null || userCurrent.getAddress().isEmpty()){
//            noAddress.setValue(true);
            userCurrent.setAddress("Ngõ 157 pháo đài láng láng thượng đống đa hà nội");
        }
    }

    public User getUserCurrent(){
        return userReponsitory.getUserCurrent();
    }
    @SuppressLint({"NewApi", "LocalSuppress"})
    public void createOrder(List<CartAndUserWithProducts> productOnCart){
        progressBar.setValue(true);
        noAddress.setValue(false);

        Order order = new Order();
        order.setUserCreatorId(userCurrent.getUserId());
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        order.setCreatedAt(formattedDateTime);
        order.setTotalMoney(Float.parseFloat(totalPrice.getValue()));
        order.setAccepted(false);
        order.setDeliveryAddress(userCurrent.getAddress());

        int id = (userCurrent.getName() + formattedDateTime).hashCode();
        order.setOrderId(id);

        orderReponsitory.insert(order, new OrderReponsitory.OnListenerOrder() {
            @Override
            public void OnSuccess(Boolean success) {
                Log.i("resultInsertOrder", success + "");


            }
        });

        Notification notification = new Notification();

        notification.setOrderId(id);
        notification.setSenderId(userCurrent.getUserId());
        notification.setReceiverId(-1);
        notification.setTitle(TITLE_NEW_ORDER);
        notification.setMessage("Có đơn hàng mới shop ơi!");
        notification.setCreatedAt(formattedDateTime);



        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartAndUserWithProducts cart : productOnCart) {
            orderDetails.add(new OrderDetail(id, cart.getProduct().getProductId(), cart.getCartId()));
        }

        cartReponsitory.deleteAllByUserIdAsync(userCurrent.getUserId());
        orderReponsitory.createOrderDetailAsync(orderDetails, new OrderReponsitory.OnListenerOrder() {
            @Override
            public void OnSuccess(Boolean success) {
                Log.i("resultCreateOrder", success + "");
            }
        });

        notificationReponsitory.insert(notification);

        createOrderSuccess.setValue(true);
        progressBar.setValue(false);

    }

}
