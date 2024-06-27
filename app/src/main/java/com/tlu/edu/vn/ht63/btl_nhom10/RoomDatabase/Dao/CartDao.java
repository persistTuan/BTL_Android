package com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tlu.edu.vn.ht63.btl_nhom10.Model.Cart;

import java.util.List;

@Dao
public interface CartDao {
    @Insert
    void insert(Cart cart);
    @Delete
    void delete(Cart cart);
    @Query("DELETE FROM carts WHERE userId = :userId")
    public void deleteAllByUserId(int userId);
    @Update
    void update(Cart cart);
    @Query("SELECT * FROM carts WHERE userId = :userId")
    List<Cart> getByUserId(int userId);
    @Query("SELECT * FROM carts WHERE userId = :userId AND productId = :productId")
    Cart checkExistInCart(int userId, String productId);
}
