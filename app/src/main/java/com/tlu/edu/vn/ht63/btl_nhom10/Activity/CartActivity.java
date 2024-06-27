package com.tlu.edu.vn.ht63.btl_nhom10.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tlu.edu.vn.ht63.btl_nhom10.Adapter.CartAdapter;
import com.tlu.edu.vn.ht63.btl_nhom10.CartViewModel;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Cart;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.CartAndUserWithProducts;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Product;
import com.tlu.edu.vn.ht63.btl_nhom10.R;
import com.tlu.edu.vn.ht63.btl_nhom10.databinding.ActivityCartBinding;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        adapter = new CartAdapter(getBaseContext(), viewModel.mproductOnCart.getValue(), viewModel);
        binding.rcvProductCart.setAdapter(adapter);

        viewModel.loadCart();
        observeData();

        actionView();

    }

    private void actionView() {
        binding.iconBack.setOnClickListener(new View.OnClickListener() {
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

        binding.btnNextOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, OrderActivity.class);
                Bundle bundle = new Bundle();
                ArrayList<CartAndUserWithProducts> tmp = new ArrayList<>(viewModel.mproductOnCart.getValue());
                bundle.putParcelableArrayList("productOnCart", tmp);
                intent.putExtras(bundle);
                intent.putExtra("totalPrice", viewModel.totalPrice.getValue().toString());
                startActivity(intent);
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
                    binding.layoutProductCart.setVisibility(View.VISIBLE);
                }
                else{
                    binding.inforNoProduct.setVisibility(View.VISIBLE);
                    binding.rcvProductCart.setVisibility(View.GONE);
                    binding.layoutTotalPrice.setVisibility(View.GONE);
                    binding.layoutProductCart.setVisibility(View.GONE);
                }
            }
        });

        viewModel.mproductOnCart.observe(this, new Observer<List<CartAndUserWithProducts>>() {
            @Override
            public void onChanged(List<CartAndUserWithProducts> cartAndUserWithProducts) {
//                Log.i("sizeCart", cartAndUserWithProducts.size() + "");
                adapter.setProductOnCart(cartAndUserWithProducts);
            }
        });

        viewModel.totalPrice.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                float totalPrice = Float.parseFloat(s);
                NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
                String res = numberFormat.format(totalPrice);
                binding.numberTotalPrice.setText(res);
            }
        });
    }
}