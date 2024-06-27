package com.tlu.edu.vn.ht63.btl_nhom10;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tlu.edu.vn.ht63.btl_nhom10.Model.User;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.UserReponsitory;

import kotlinx.coroutines.CoroutineScope;

public class LoginViewModel extends ViewModel {
    private UserReponsitory userReponsitory;
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    public MutableLiveData<Boolean> loginWithAdmin = new MutableLiveData<>();
    public User userCurrenr;

    public LoginViewModel(Context context){
        userReponsitory = new UserReponsitory(context);
        userCurrenr = userReponsitory.getUserCurrent();
    }

    public void login(View view) {
        if(email.getValue() == null || password.getValue() == null){
            Toast.makeText(view.getContext(), "Email or password is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        email.setValue(email.getValue().trim());
        password.setValue(password.getValue().trim());
        User user = userReponsitory.authen(email.getValue(), password.getValue());
        if(user != null){
            Log.i("user", user.isAdmin() + "");
            if(user.isAdmin()){
                loginWithAdmin.setValue(true);
            }
            else{
                loginSuccess.setValue(true);
            }
        }
        else{
            loginSuccess.setValue(false);
        }
    }

    public void onLoginStart() {
        User user = userReponsitory.getUserCurrent();
       if( user !=null){
           Log.i("user", user.isAdmin() + "");
           if(user.isAdmin()){
               loginWithAdmin.setValue(true);
           }
           else{
               loginSuccess.setValue(true);
           }
       }
    }
}
