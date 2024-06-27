package com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory;

import android.content.Context;
import android.util.Log;

import com.tlu.edu.vn.ht63.btl_nhom10.Model.Cart;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.CartAndUserWithProducts;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Product;
import com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.AppDatabase;
import com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.Dao.CartDao;
import com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.Dao.UserDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

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

    public void deleteAllByUserIdAsync(int userId){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                cartDao.deleteAllByUserId(userId);
            }
        });
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
        List<Cart> carts = cartDao.getByUserId(userId);
        List<CartAndUserWithProducts> productOnCart = new ArrayList<>();
        AtomicInteger counter = new AtomicInteger(carts.size());
        for (Cart cart : carts){
            productReponsitory.getProductById(cart.getProductId(), new ProductReponsitory.GetProductCallback() {
                @Override
                public void getProductCallback(Product product) {
                    productOnCart.add(new CartAndUserWithProducts(cart.getCartId(), userId, product, cart.getQuantity()));
                    if(counter.decrementAndGet() == 0){
                        callback.onGetCart(productOnCart);
                    }
                }
            });
        }
    }

    public boolean checkCart(int userId, String productId){
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
        void onGetCart(List<CartAndUserWithProducts> productOnCart);
    }
}
