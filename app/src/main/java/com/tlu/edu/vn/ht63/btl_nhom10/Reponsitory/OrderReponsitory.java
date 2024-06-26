package com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory;

import android.content.Context;
import android.util.Log;

import com.tlu.edu.vn.ht63.btl_nhom10.Model.Cart;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Order;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.OrderDetail;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.User;
import com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.AppDatabase;
import com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.Dao.OrderDao;
import com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.Dao.OrderDetailDao;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;

public class OrderReponsitory {
    private OrderDao orderDao;
    private OrderDetailDao orderDetailDao;
    private Context context;
    public OrderReponsitory(Context context){
        this.context = context;
        orderDao = AppDatabase.getInstance(context).orderDao();
        orderDetailDao = AppDatabase.getInstance(context).orderDetailDao();
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
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Order order = orderDao.getByOrderId(orderId);
                callback.OnGetOrder(order);
            }
        });
    }



    public interface OnListenerOrder{
        public void OnSuccess(Boolean success);
    }
    public interface OnListenerGetOrder{
        public void OnGetOrder(Order order);
    }
}
