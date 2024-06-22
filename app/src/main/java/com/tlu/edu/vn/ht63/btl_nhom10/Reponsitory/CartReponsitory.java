package com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory;

import android.content.Context;

import com.tlu.edu.vn.ht63.btl_nhom10.Model.Cart;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.CartAndUserWithProducts;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Product;
import com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.AppDatabase;
import com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.Dao.CartDao;
import com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.Dao.UserDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class CartReponsitory {
    private CartDao cartDao;
    private ProductReponsitory productReponsitory;
    Context context;
    public CartReponsitory(Context context){
        this.context = context;
        cartDao = AppDatabase.getInstance(context).cartDao();
        productReponsitory = new ProductReponsitory(context);
    }

    public void insert(Cart cart){
        cartDao.insert(cart);
    }

    public void delete(Cart cart){
        cartDao.delete(cart);
    }
    public void update(Cart cart){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                cartDao.update(cart);
            }
        });
    }

    public void getByUserId(int userId, OnLinstenerGetCart callback){
        callback.onGetCart( cartDao.getByUserId(userId) );
    }

    public boolean checkCart(int userId, int productId){
        Cart cart = cartDao.checkExistInCart(userId, productId);
        if(cart == null){
            return false;
        }
        return true;
    }

    public interface OnListenerChangeCart{
        void onChangeCart(boolean success);
    }

    public interface OnLinstenerGetCart{
        void onGetCart(List<Cart> carts);
    }
}
