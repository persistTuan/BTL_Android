package com.tlu.edu.vn.ht63.btl_nhom10.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlu.edu.vn.ht63.btl_nhom10.AccountViewModel;
import com.tlu.edu.vn.ht63.btl_nhom10.Activity.SearchPlaceActivity;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.User;
import com.tlu.edu.vn.ht63.btl_nhom10.R;
import com.tlu.edu.vn.ht63.btl_nhom10.databinding.FragmentAccountBinding;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private AccountViewModel viewModel;


    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new AccountViewModel(getContext());
            }
        }).get(AccountViewModel.class);

        actionView();


        return binding.getRoot();
    }

    private void actionView() {
        User user = viewModel.getUserCurrent();
        binding.inforAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchPlaceActivity.class);
                startActivity(intent);
            }
        });

        binding.txtUserName.setText(user.getName());
        if(user.getAddress() != null){
            binding.txtAddress.setText(user.getAddress());
        }
        else{
            binding.txtAddress.setText("Chưa cập nhật địa chỉ hãy câp nhật lai ngay !");
            binding.txtAddress.setError("Chưa có địa chỉ");
        }
        binding.txtPhoneNumber.setText(user.getPhoneNumber());

    }


}