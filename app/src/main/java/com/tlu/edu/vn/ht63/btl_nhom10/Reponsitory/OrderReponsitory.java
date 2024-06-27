package com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.tlu.edu.vn.ht63.btl_nhom10.Model.Cart;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Order;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.OrderDetail;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Product;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.User;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.UserWithOrderAndProduct;
import com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.AppDatabase;
import com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.Dao.OrderDao;
import com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.Dao.OrderDetailDao;
import com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.Dao.UserDao;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class OrderReponsitory {
    private OrderDao orderDao;
    private UserReponsitory userReponsitory;
    private OrderDetailDao orderDetailDao;
    private ProductReponsitory productReponsitory;
    private Context context;
    public OrderReponsitory(Context context){
        this.context = context;
        orderDao = AppDatabase.getInstance(context).orderDao();
        orderDetailDao = AppDatabase.getInstance(context).orderDetailDao();
        userReponsitory = new UserReponsitory(context);
        productReponsitory = new ProductReponsitory(context);
    }

    public void insert(Order order, OnListenerOrder callback){
        Long result = orderDao.insert(order);
        if(result > 0){
            callback.OnSuccess(true);
        }else{
            callback.OnSuccess(false);
        }
    }

    public void delete(Order order, OnListenerOrder callback){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                int result = orderDao.delete(order);
                if(result > 0){
                    callback.OnSuccess(true);
                }else{
                    callback.OnSuccess(false);
                }
            }
        });
    }

    public void update(Order order, OnListenerOrder callback){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                int result = orderDao.update(order);
                if(result > 0){
                    callback.OnSuccess(true);
                }else{
                    callback.OnSuccess(false);
                }
            }
        });

    }

    public void createOrderDetailAsync(List<OrderDetail> orderDetails, OnListenerOrder callback){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                orderDetailDao.insertAll(orderDetails);
                callback.OnSuccess(true);
            }
        });
    }

    public void getByOrderId(int orderId, OnListenerGetOrder callback){
        Order order = orderDao.getByOrderId(orderId);
        callback.OnGetOrder(order);
    }


    public void getOrderDetail(int userId, int orderId, OnListenerOrderDetail callback)
    {
        UserWithOrderAndProduct orderDetails = new UserWithOrderAndProduct();
        userReponsitory.getUserByIdAsync(userId, new UserReponsitory.OnGetUserListener() {
            @Override
            public void onGetUser(User user) {
                orderDetails.setUser(user);
            }
        });

        getByOrderId(orderId, new OnListenerGetOrder() {
            @Override
            public void OnGetOrder(Order order) {
                orderDetails.setOrder(order);
            }
        });

        List<OrderDetail> orderDetailList = new ArrayList<>();
        List<Pair<Product, Integer>> PairProduct = new ArrayList<>();

        orderDetailList = orderDetailDao.getByOrderId(orderId);

        for (OrderDetail orderDetail : orderDetailList) {
            productReponsitory.getProductById(orderDetail.getProductId(), new ProductReponsitory.GetProductCallback() {
                @Override
                public void getProductCallback(Product product) {
                    PairProduct.add(new Pair<>(product, orderDetail.getQuantity()));
                }
            });
        }

        orderDetails.setProducts(PairProduct);

        callback.onSuccess(orderDetails);
        callback.onFailure("Lỗi mọi người ơi");

    }



    public interface OnListenerOrder{
        public void OnSuccess(Boolean success);
    }
    public interface OnListenerGetOrder{
        public void OnGetOrder(Order order);
    }

    public interface OnListenerOrderDetail{
        public void onSuccess(UserWithOrderAndProduct orderDetails);
        public void onFailure(String message);
    }
}
