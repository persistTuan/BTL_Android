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
    public int delete(Order order);

    @Query("SELECT * FROM orders")
    public List<Order> getAll();

    @Query("SELECT * FROM orders WHERE userCreatorId = :userId")
    public Order getByUserId(int userId);

    @Query("Select * from orders where orderId = :orderId")
    public Order getByOrderId(int orderId);

    @Query("SELECT * FROM orders WHERE userCreatorId = :userId AND isAccepted = 0")
    public List<Order> getOrderPending(int userId);
    @Query("SELECT * FROM orders WHERE userCreatorId = :userId AND isAccepted = 1")
    public List<Order> getOrderAccepted(int userId);

//    @Transaction
//    @Query("SELECT * FROM orders WHERE orderId = :orderId")
//    public List<OrderWithProducts> getOrderWithProductsByOrderId(int orderId);

}
