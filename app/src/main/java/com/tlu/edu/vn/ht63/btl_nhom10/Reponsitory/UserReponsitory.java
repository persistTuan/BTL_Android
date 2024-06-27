package com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory;

import android.content.Context;

import com.tlu.edu.vn.ht63.btl_nhom10.Model.User;
import com.tlu.edu.vn.ht63.btl_nhom10.MySharedPreferences;
import com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.AppDatabase;
import com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.Dao.UserDao;

import java.util.List;
import java.util.concurrent.Executors;

public class UserReponsitory {
    private Context context;
    private UserDao userDao;
    private List<User> users;
    private User userCurrent;

    public User getUserCurrent() {
        String userId = MySharedPreferences.getInstance(context).getString("user_id");
        if(userId != null){
            userCurrent = userDao.getUserById(Integer.parseInt(userId));
        }
        else{
            userCurrent = null;
        }
        return userCurrent;
    }

    public UserReponsitory(Context context) {
        this.context = context;
        this.userDao = AppDatabase.getInstance(context).userDao();
    }

    public List<User> getAll(){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                users = userDao.getAll();
            }
        });
        return users;
    }


    public User authen(String email, String password){
        if(email == null || password == null) return null;
        userCurrent = userDao.auth(email, password);
        if(userCurrent != null){
            MySharedPreferences.getInstance(context).putString("user_id", userCurrent.getUserId()+"");
        }
//        Executors.newSingleThreadExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//            });
        return userCurrent;
    }

    public boolean register(User user){
        long res = userDao.insert(user);
        return res > 0;
    }

    public void updateUser(User user, OnUserListener callback){
        int result = userDao.update(user);
        if(result > 0){
            callback.onChangeData(true);
        }
        else{
            callback.onChangeData(false);
        }
    }

    public void clearUserCurrent(){
        MySharedPreferences.getInstance(context).clear();
    }

    public interface OnUserListener{
        public void onChangeData(boolean success);
    }
    public interface OnGetUserListener{
        public void onGetUser(User user);
    }
    public void getUserByIdAsync(int userId, OnGetUserListener callback){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                User result = userDao.getUserById(userId);
                callback.onGetUser(result);
            }
        });
    }
}
