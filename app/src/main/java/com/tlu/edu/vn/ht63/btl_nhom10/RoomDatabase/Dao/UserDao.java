package com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.tlu.edu.vn.ht63.btl_nhom10.Model.User;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.UserWithOrders;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    public Long insert(User user);
    @Update
    public int update(User user);
    @Delete
    public int delete(User user);
    @Query("SELECT * FROM users")
    public List<User> getAll();

    @Query("SELECT * FROM users WHERE userId = :id")
    public User getUserById(int id);
    @Transaction
    @Query("SELECT * FROM users")
    public List<UserWithOrders> getUserWithOrders();
    @Transaction
    @Query("SELECT * FROM users WHERE userId = :userId")
    public List<UserWithOrders> getUserWithOrdersById(int userId);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    public User auth(String email, String password);
}
