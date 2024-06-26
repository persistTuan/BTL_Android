package com.tlu.edu.vn.ht63.btl_nhom10;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tlu.edu.vn.ht63.btl_nhom10.Model.Cart;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.CartAndUserWithProducts;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Order;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Product;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.User;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.CartReponsitory;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.OrderReponsitory;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.ProductReponsitory;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.UserReponsitory;

import java.lang.invoke.MutableCallSite;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.Executors;

public class CartViewModel extends ViewModel {
    private CartReponsitory cartRepository;
    private UserReponsitory userReponsitory;
    private OrderReponsitory orderReponsitory;
    private ProductReponsitory productReponsitory;
    public MutableLiveData<String> numberProducts = new MutableLiveData<>();
    public MutableLiveData<String> totalPrice = new MutableLiveData<>("0");
    public MutableLiveData<List<CartAndUserWithProducts>> mproductOnCart = new MutableLiveData<>();

    public MutableLiveData<Boolean> isHaveCart = new MutableLiveData<>(false);

    public CartViewModel(Context context){
        cartRepository = new CartReponsitory(context);
        userReponsitory = new UserReponsitory(context);
        orderReponsitory = new OrderReponsitory(context);
        productReponsitory = new ProductReponsitory(context);

    }



    public void loadCart(){
        User userCurrent = userReponsitory.getUserCurrent();
        cartRepository.getByUserId(userCurrent.getUserId(), new CartReponsitory.OnLinstenerGetCart() {
            @Override
            public void onGetCart(List<CartAndUserWithProducts> productOnCart) {
                if(productOnCart != null){
                    if(productOnCart.size() > 0){
                        isHaveCart.setValue(true);
                    }
                }
                mproductOnCart.setValue(productOnCart);
            }
        });
    }

    public void updateCart(Cart cart){
        cartRepository.update(cart);
    }
    public void deleteCart(Cart cart){
        cartRepository.delete(cart);
    }

    @SuppressLint({"NewApi", "LocalSuppress"})
    public void createOrder(View view ){
        User userCurrent = userReponsitory.getUserCurrent();
        Order order = new Order();
        order.setUserCreatorId(userCurrent.getUserId());
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
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

            }
        });
    }




    public void senNotification(){
//        if(cartList.getValue() != null){
//
//        }
    }
}
