package com.tlu.edu.vn.ht63.btl_nhom10;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tlu.edu.vn.ht63.btl_nhom10.Model.Cart;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.CartAndUserWithProducts;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.User;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.CartReponsitory;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.UserReponsitory;

import java.lang.invoke.MutableCallSite;
import java.util.List;
import java.util.concurrent.Executors;

public class CartViewModel extends ViewModel {
    private CartReponsitory cartRepository;
    private UserReponsitory userReponsitory;
    public MutableLiveData<String> numberProducts = new MutableLiveData<>();
    public MutableLiveData<String> totalPrice = new MutableLiveData<>("0");
    public MutableLiveData<List<Cart>> cartList = new MutableLiveData<>();

    public MutableLiveData<Boolean> isHaveCart = new MutableLiveData<>(false);

    public CartViewModel(Context context){
        cartRepository = new CartReponsitory(context);
        userReponsitory = new UserReponsitory(context);
    }



    public void loadCart(){
        User userCurrent = userReponsitory.getUserCurrent();
        cartRepository.getByUserId(userCurrent.getUserId(), new CartReponsitory.OnLinstenerGetCart() {
            @Override
            public void onGetCart(List<Cart> carts) {
                if(carts.size() > 0){
                    isHaveCart.setValue(true);
                }else{
                    isHaveCart.setValue(false);
                }
                cartList.setValue(carts);
            }
        });
    }

    public void updateCart(Cart cart){
        cartRepository.update(cart);
    }




    public void senNotification(){
        if(cartList.getValue() != null){

        }
    }
}
