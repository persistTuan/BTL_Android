package com.tlu.edu.vn.ht63.btl_nhom10.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlu.edu.vn.ht63.btl_nhom10.AccountViewModel;
import com.tlu.edu.vn.ht63.btl_nhom10.Activity.AccountActivity;
import com.tlu.edu.vn.ht63.btl_nhom10.Activity.LoginActivity;
import com.tlu.edu.vn.ht63.btl_nhom10.Activity.OrderActivity;
import com.tlu.edu.vn.ht63.btl_nhom10.Activity.SearchPlaceActivity;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.User;
import com.tlu.edu.vn.ht63.btl_nhom10.R;
import com.tlu.edu.vn.ht63.btl_nhom10.databinding.FragmentAccountBinding;

public class AccountFragment extends Fragment {
    private static final int REQUEST_CODE = 371;

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
        binding.iconNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchPlaceActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        binding.txtUserName.setText(user.getName());
        if(user.getAddress() != null && !user.getAddress().isEmpty()){
            binding.txtAddress.setText(user.getAddress());
        }
        else{
            binding.txtAddress.setText("Chưa cập nhật địa chỉ hãy câp nhật lai ngay !");
            binding.txtAddress.setError("Chưa có địa chỉ");
        }
        binding.txtPhoneNumber.setText(user.getPhoneNumber());
        binding.layoutSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Bạn có muốn đăng xuất không ?");
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(data!=null){
                String address = data.getStringExtra("address");
                binding.txtAddress.setText(address);
            }
        }
    }



    private void showDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thông báo");
        builder.setMessage(title);

        // Thêm nút Positive
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                viewModel.signOut();
                // Xử lý khi người dùng nhấn OK
                Intent intent = new Intent(getContext(), LoginActivity.class);
                dialog.dismiss();
                startActivity(intent);

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Tạo và hiển thị dialog
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.RED);
    }

}