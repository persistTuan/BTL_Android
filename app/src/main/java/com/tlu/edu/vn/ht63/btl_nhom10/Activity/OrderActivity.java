package com.tlu.edu.vn.ht63.btl_nhom10.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tlu.edu.vn.ht63.btl_nhom10.Adapter.OrderAdapter;
import com.tlu.edu.vn.ht63.btl_nhom10.CartViewModel;
import com.tlu.edu.vn.ht63.btl_nhom10.LoadingDialog;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.CartAndUserWithProducts;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.User;
import com.tlu.edu.vn.ht63.btl_nhom10.OrderViewModel;
import com.tlu.edu.vn.ht63.btl_nhom10.R;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.OrderReponsitory;
import com.tlu.edu.vn.ht63.btl_nhom10.databinding.ActivityOrderBinding;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class OrderActivity extends AppCompatActivity {
    private LoadingDialog loadingDialog;
    private List<CartAndUserWithProducts> productOnCart;
    private ActivityOrderBinding binding;
    private OrderReponsitory reponsitory;
    private String totalOrder;
    private String totalPrice;
    private OrderAdapter adapter;
    private OrderViewModel orderViewModel;
    private User userCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);
        loadingDialog = new LoadingDialog(this);

        initBindingAndVieModel();

        ArrayList<CartAndUserWithProducts> tmp = getIntent().getExtras().getParcelableArrayList("productOnCart");
        productOnCart = tmp;
        adapter = new OrderAdapter(OrderActivity.this, productOnCart);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layout.VERTICAL);
        binding.rcvListProduct.setLayoutManager(layout);
        binding.rcvListProduct.addItemDecoration(dividerItemDecoration);
        binding.rcvListProduct.setAdapter(adapter);


        actionView();




        observeData();
        
        callApi();

    }

    private void inintData() {


    }

    private void observeData() {
        orderViewModel.noAddress.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                    showDialog("Bạn chưa nhập địa chỉ. Đi đến Account manager để nhập địa chỉ ?");
            }
        });

        orderViewModel.createOrderSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Intent intent = new Intent(OrderActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        orderViewModel.progressBar.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    binding.fullscreenOverlay.setVisibility(View.VISIBLE);
                }
                else {
                    binding.fullscreenOverlay.setVisibility(View.GONE);
                }
            }
        });
    }

    private void callApi() {
    }

    public void initBindingAndVieModel(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order);
        reponsitory = new OrderReponsitory(OrderActivity.this);
        totalPrice = getIntent().getStringExtra("totalPrice");
        orderViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new OrderViewModel(OrderActivity.this);
            }
        }).get(OrderViewModel.class);

        userCurrent = orderViewModel.getUserCurrent();
    }


    public void actionView() {
        totalPrice = getIntent().getStringExtra("totalPrice");
        float numberTotalPrice = Float.parseFloat(totalPrice);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        binding.txtTotalPrice.setText(numberFormat.format(numberTotalPrice));
        totalOrder = numberTotalPrice + "";
        binding.txtTotalPriceAllProduct.setText(numberFormat.format(numberTotalPrice));

        binding.iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnNextOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderViewModel.totalPrice.setValue(totalOrder);
                orderViewModel.createOrder(productOnCart);
            }
        });

        binding.txtUserName.setText(userCurrent.getName());
        binding.txtPhoneNumber.setText(userCurrent.getPhoneNumber());
        binding.txtAddressUser.setText(userCurrent.getAddress());

    }

    private void showDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage(title);

        // Thêm nút Positive
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Xử lý khi người dùng nhấn OK
                Intent intent = new Intent(OrderActivity.this, AccountActivity.class);
                dialog.dismiss();
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        // Tạo và hiển thị dialog
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.RED);
    }

}