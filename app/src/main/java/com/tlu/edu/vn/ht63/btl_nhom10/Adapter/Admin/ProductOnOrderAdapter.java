package com.tlu.edu.vn.ht63.btl_nhom10.Adapter.Admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tlu.edu.vn.ht63.btl_nhom10.Model.Order;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Product;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.UserWithOrderAndProduct;
import com.tlu.edu.vn.ht63.btl_nhom10.databinding.ItemProductOrderBinding;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ProductOnOrderAdapter extends RecyclerView.Adapter<ProductOnOrderAdapter.ViewHolder>{

    private UserWithOrderAndProduct userWithOrderAndProduct;
    private Context context;

    public ProductOnOrderAdapter(Context context, UserWithOrderAndProduct userWithOrderAndProduct) {
        this.userWithOrderAndProduct = userWithOrderAndProduct;
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setProductOnOrder(UserWithOrderAndProduct userWithOrderAndProduct){
        this.userWithOrderAndProduct = userWithOrderAndProduct;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductOrderBinding binding = ItemProductOrderBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = userWithOrderAndProduct.getOrder();
        Pair<Product, Integer> pairProduct = userWithOrderAndProduct.getProducts().get(position);
        holder.binding.txtProductDescription.setText(pairProduct.first.getProductDescription());
        holder.binding.txtQuantity.setText(pairProduct.second + "");
        float totalPrice = pairProduct.first.getProductPrice() * (1 - pairProduct.first.getProductDiscount() / 100) * pairProduct.second;
        DecimalFormat formatNumber = new DecimalFormat("#,###.##");
        holder.binding.txtTotalPriceAProduct.setText(formatNumber.format(totalPrice) + " Đ");

        if(pairProduct.first.getProductDiscount() != 0){
            holder.binding.txtOldPrice.setVisibility(View.VISIBLE);
            holder.binding.txtOldPrice.setText(formatNumber.format(pairProduct.first.getProductPrice()) + " Đ");
            holder.binding.txtNewPrice.setText(pairProduct.first.getProductPrice() * (1 - pairProduct.first.getProductDiscount() / 100) + " Đ");
        }
        else{
            holder.binding.txtOldPrice.setVisibility(View.GONE);
            holder.binding.txtNewPrice.setText(formatNumber.format(pairProduct.first.getProductPrice()) + " Đ");
        }
    }

    @Override
    public int getItemCount() {
        if(userWithOrderAndProduct != null){
            Log.i("ProductOnOrderAdapter", "getItemCount: " + userWithOrderAndProduct.getProducts().size());
            return userWithOrderAndProduct.getProducts().size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ItemProductOrderBinding binding;
        public ViewHolder(@NonNull ItemProductOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
