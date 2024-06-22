package com.tlu.edu.vn.ht63.btl_nhom10;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tlu.edu.vn.ht63.btl_nhom10.Activity.LoginActivity;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.User;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.UserReponsitory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SignupViewModel extends ViewModel {
    private UserReponsitory userReponsitory ;
    public MutableLiveData<String> email = new MutableLiveData<>("");
    public MutableLiveData<String> userName = new MutableLiveData<>("");
    public MutableLiveData<String> phoneNumber = new MutableLiveData<>("");
    public MutableLiveData<String> password = new MutableLiveData<>("");
    public MutableLiveData<String> confirmPassword = new MutableLiveData<>("");
    public MutableLiveData<String> emailError = new MutableLiveData<>();
    public MutableLiveData<String> passwordError = new MutableLiveData<>();
    public MutableLiveData<String> confirmPasswordError = new MutableLiveData<>();
    public MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();


    public SignupViewModel(Context context){
        userReponsitory = new UserReponsitory(context);
    }
    public void register(View view){
        email.setValue(email.getValue().trim());
        userName.setValue(userName.getValue().trim());
        phoneNumber.setValue(phoneNumber.getValue().trim());
        password.setValue(password.getValue().trim());
        confirmPassword.setValue(confirmPassword.getValue().trim());

        User user = new User();
        user.setEmail(email.getValue());
        user.setName(userName.getValue());
        user.setPhoneNumber(phoneNumber.getValue());
        user.setPassword(password.getValue());
        user.setCreatedAt(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        if(!user.isValidEmail()){
            emailError.setValue("Email is not valid");
            return;
        }
        else {
            emailError.setValue(null);
        }
        if(!user.isValidPassword()){
            passwordError.setValue("Password length must be greater than 8");
            return;
        }
        else {
            passwordError.setValue(null);
        }

        if(!confirmPassword.getValue().equals(password.getValue())){
            confirmPasswordError.setValue("Mật khẩu không khớp");
            return;
        }
        else {
            confirmPasswordError.setValue(null);
        }

        if(userName.getValue().isEmpty()){
            Toast.makeText(view.getContext(), "Bạn chưa nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        }
        if(phoneNumber.getValue().isEmpty()){
            Toast.makeText(view.getContext(), "Bạn chưa nhập số điện thoại", Toast.LENGTH_SHORT).show();
            return;
        }


        if(userReponsitory.register(user)){
            loginSuccess.setValue(true);
            Intent intent = new Intent(view.getContext(), LoginActivity.class);
            view.getContext().startActivity(intent);
        }
        else {
            loginSuccess.setValue(false);
        }
    }

    public void goToLogin(View view){
        Intent intent = new Intent(view.getContext(), LoginActivity.class);
        view.getContext().startActivity(intent);
        ((Activity)view.getContext()).finish();
    }

}
