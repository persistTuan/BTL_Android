package com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.tlu.edu.vn.ht63.btl_nhom10.Model.OrderDetail;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.OrderWithProducts;

import java.util.List;

@Dao
public interface OrderDetailDao {
    @Insert
    public Long insert(OrderDetail orderDetail);
    @Update
    public int update(OrderDetail orderDetail);
    @Delete
    public int delete(OrderDetail orderDetail);
//    @Transaction
//    @Query("SELECT * FROM orders WHERE orderId = :orderId")
//    public List<OrderWithProducts> getOrderWithProductsByOrderId(int orderId);


}
