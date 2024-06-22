package com.tlu.edu.vn.ht63.btl_nhom10.Activity;

import static androidx.constraintlayout.motion.widget.TransitionBuilder.validate;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tlu.edu.vn.ht63.btl_nhom10.SignupViewModel;
import com.tlu.edu.vn.ht63.btl_nhom10.R;
import com.tlu.edu.vn.ht63.btl_nhom10.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    public ActivitySignupBinding binding;
    public SignupViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                if(modelClass.isAssignableFrom(SignupViewModel.class)){
                    return (T) new SignupViewModel(getApplication());
                }
                return null;
            }
        }).get(SignupViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setSignupViewModel(viewModel);

        validate();
    }


    private void validate(){
        viewModel.emailError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s != null){
                    binding.edtEmail.setError(s);
                }
                else{
                    binding.edtEmail.setError(null);
                }
            }
        });
        viewModel.passwordError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s != null){
                    binding.edtPassword.setError(s);
                }
                else{
                    binding.edtPassword.setError(null);
                }
            }
        });
        viewModel.confirmPasswordError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s != null){
                    binding.edtRepassword.setError(s);
                }
                else{
                    binding.edtRepassword.setError(null);
                }
            }
        });
        viewModel.loginSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean res) {
                if(res){
                    Toast.makeText(SignupActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SignupActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}