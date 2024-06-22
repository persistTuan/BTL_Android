package com.tlu.edu.vn.ht63.btl_nhom10;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tlu.edu.vn.ht63.btl_nhom10.Model.User;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.UserReponsitory;

public class AccountViewModel extends ViewModel {
    public MutableLiveData<Boolean> isLogout = new MutableLiveData<>();
    private UserReponsitory userReponsitory;
    public User userCurrent;

    public AccountViewModel(Context context){
        userReponsitory = new UserReponsitory(context);
    }

    public void logOut(View view){
        userReponsitory.clearUserCurrent();
        isLogout.setValue(true);
    }

}
