package com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.tlu.edu.vn.ht63.btl_nhom10.Model.Order;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.OrderWithProducts;

import java.util.List;

@Dao
public interface OrderDao {
    @Insert
    public Long insert(Order order);
    @Update
    public int update(Order order);
    @Delete
    int delete(Order order);

    @Query("SELECT * FROM orders")
    public List<Order> getAll();

    @Query("SELECT * FROM orders WHERE userCreatorId = :userId")
    public Order getByUserId(int userId);

//    @Transaction
//    @Query("SELECT * FROM orders WHERE orderId = :orderId")
//    public List<OrderWithProducts> getOrderWithProductsByOrderId(int orderId);

}
