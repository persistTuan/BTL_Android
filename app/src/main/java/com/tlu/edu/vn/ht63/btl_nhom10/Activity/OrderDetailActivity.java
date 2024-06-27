package com.tlu.edu.vn.ht63.btl_nhom10.Activity;

import android.os.Bundle;
import android.util.Pair;

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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tlu.edu.vn.ht63.btl_nhom10.Adapter.Admin.ProductOnOrderAdapter;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Order;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Product;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.User;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.UserWithOrderAndProduct;
import com.tlu.edu.vn.ht63.btl_nhom10.OrderViewModel;
import com.tlu.edu.vn.ht63.btl_nhom10.R;
import com.tlu.edu.vn.ht63.btl_nhom10.databinding.ActivityOrderDetailBinding;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {

    private ActivityOrderDetailBinding binding;
    private OrderViewModel viewModel;
    private UserWithOrderAndProduct userWithOrderAndProduct;
    private ProductOnOrderAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        initBindingAndViewModel();

        adapter = new ProductOnOrderAdapter(this, viewModel.userWithOrderAndProduct.getValue());

        obeserveViewModel();

        actionView();
    }

    private void actionView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.rcvProduct.setLayoutManager(layoutManager);
        binding.rcvProduct.addItemDecoration(dividerItemDecoration);
        binding.rcvProduct.setAdapter(adapter);

        if(viewModel.userWithOrderAndProduct != null){
            userWithOrderAndProduct = viewModel.userWithOrderAndProduct.getValue();
        }

        Order order = userWithOrderAndProduct.getOrder();
        User user = userWithOrderAndProduct.getUser();
        List<Pair<Product, Integer>> productOnOrder = userWithOrderAndProduct.getProducts();

        binding.txtOrderId.setText(order.getOrderId() + "");
        binding.txtCreatedOrder.setText(order.getCreatedAt());
        binding.txtDistance.setText(user.getDistance() + " km");
        binding.txtUserName.setText(user.getName());
        binding.txtPhoneNumber.setText(user.getPhoneNumber());
        binding.txtAddress.setText(user.getAddress());
        DecimalFormat formatter = new DecimalFormat("#,###.##");
        binding.txtTotalPrice.setText(formatter.format(order.getTotalMoney()) + "đ");
        if(user.getDistance() > 3){
            binding.txtFeeTransport.setText("10,000Đ");
        }
        else{
            binding.txtFeeTransport.setText("0Đ");
        }


    }

    private void obeserveViewModel() {
        viewModel.userWithOrderAndProduct.observe(this, new Observer<UserWithOrderAndProduct>() {
            @Override
            public void onChanged(UserWithOrderAndProduct userWithOrderAndProduct) {
                if(userWithOrderAndProduct != null){
                    adapter.setProductOnOrder(userWithOrderAndProduct);
                }
            }
        });
    }

    private void initBindingAndViewModel() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new OrderViewModel(getBaseContext());
            }
        }).get(OrderViewModel.class);
    }
}