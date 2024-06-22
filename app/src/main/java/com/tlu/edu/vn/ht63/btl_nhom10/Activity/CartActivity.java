package com.tlu.edu.vn.ht63.btl_nhom10.Activity;

import android.os.Bundle;
import android.view.View;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tlu.edu.vn.ht63.btl_nhom10.Adapter.CartAdapter;
import com.tlu.edu.vn.ht63.btl_nhom10.CartViewModel;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Cart;
import com.tlu.edu.vn.ht63.btl_nhom10.R;
import com.tlu.edu.vn.ht63.btl_nhom10.databinding.ActivityCartBinding;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private ActivityCartBinding binding;
    private CartViewModel viewModel;
    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        initBindingAndVieModel();

        LinearLayoutManager layoutForCart = new LinearLayoutManager(this);
        binding.rcvProductCart.setLayoutManager(layoutForCart);
        adapter = new CartAdapter(getBaseContext(), viewModel.cartList.getValue(), viewModel);
        binding.rcvProductCart.setAdapter(adapter);
        viewModel.loadCart();

        observeData();

        actionView();

    }

    private void actionView() {
        binding.iconBackCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initBindingAndVieModel(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new CartViewModel(getBaseContext());
            }
        }).get(CartViewModel.class);
    }

    public void observeData(){
        viewModel.isHaveCart.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    binding.inforNoProduct.setVisibility(View.GONE);
                    binding.rcvProductCart.setVisibility(View.VISIBLE);
                    binding.layoutTotalPrice.setVisibility(View.VISIBLE);
                }
                else{
                    binding.inforNoProduct.setVisibility(View.VISIBLE);
                    binding.rcvProductCart.setVisibility(View.GONE);
                    binding.layoutTotalPrice.setVisibility(View.GONE);
                }
            }
        });

        viewModel.cartList.observe(this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                adapter.setCarts(carts);
            }
        });

        viewModel.totalPrice.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.numberTotalPrice.setText(s);
            }
        });
    }
}