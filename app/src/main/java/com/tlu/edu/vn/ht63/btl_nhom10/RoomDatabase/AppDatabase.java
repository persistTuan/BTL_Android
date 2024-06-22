package com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tlu.edu.vn.ht63.btl_nhom10.Model.Cart;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Order;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.OrderDetail;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Product;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.User;
import com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.Dao.CartDao;
import com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.Dao.OrderDao;
import com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.Dao.OrderDetailDao;
import com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.Dao.ProductDao;
import com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.Dao.UserDao;


@Database(entities = {User.class, Order.class,
        OrderDetail.class, Cart.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "DrinkApp";
    public abstract UserDao userDao();
    public abstract OrderDao orderDao();
    public abstract OrderDetailDao orderDetailDao();
    public abstract CartDao cartDao();
    public static AppDatabase instance;

    public static AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext() , AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
