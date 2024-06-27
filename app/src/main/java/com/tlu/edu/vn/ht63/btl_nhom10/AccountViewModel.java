package com.tlu.edu.vn.ht63.btl_nhom10;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tlu.edu.vn.ht63.btl_nhom10.Model.User;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.UserReponsitory;

public class AccountViewModel extends ViewModel {
    public MutableLiveData<Boolean> isLogout = new MutableLiveData<>();
    public MutableLiveData<Boolean> updateSuccess = new MutableLiveData<>();
    private UserReponsitory userReponsitory;

    public AccountViewModel(Context context){
        userReponsitory = new UserReponsitory(context);
    }

    public void logOut(View view){
        userReponsitory.clearUserCurrent();
        isLogout.setValue(true);
    }

    public User getUserCurrent(){
        return userReponsitory.getUserCurrent();
    }

    public void update(User user){
        userReponsitory.updateUser(user, new UserReponsitory.OnUserListener() {
            @Override
            public void onChangeData(boolean success) {
                updateSuccess.setValue(success);
            }
        });
    }

    public void signOut(){
        userReponsitory.clearUserCurrent();
    }


}
