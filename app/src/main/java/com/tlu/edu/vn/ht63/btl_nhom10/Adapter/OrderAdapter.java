package com.tlu.edu.vn.ht63.btl_nhom10.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tlu.edu.vn.ht63.btl_nhom10.Model.CartAndUserWithProducts;
import com.tlu.edu.vn.ht63.btl_nhom10.R;
import com.tlu.edu.vn.ht63.btl_nhom10.databinding.ItemProductOrderBinding;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<CartAndUserWithProducts> productOnCart;
    private Context context;

    public OrderAdapter(Context context, List<CartAndUserWithProducts> productOnCart){
        this.context = context;
        this.productOnCart = productOnCart;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductOrderBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.item_product_order, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartAndUserWithProducts cart = productOnCart.get(position);
        holder.binding.txtProductDescription.setText(cart.getProduct().getProductDescription());
        holder.binding.txtQuantity.setText(cart.getQuantity() + "");
        Float totalPrice = cart.getProduct().getProductPrice() * (1 - cart.getProduct().getProductDiscount() / 100) * cart.getQuantity();
        holder.binding.txtTotalPriceAProduct.setText(totalPrice.toString());
        if(cart.getProduct().getProductDiscount() == 0){
            holder.binding.txtOldPrice.setVisibility(View.GONE);
            holder.binding.txtNewPrice.setText(cart.getProduct().getProductPrice() + "");
        }
        else{
            holder.binding.txtOldPrice.setText(cart.getProduct().getProductPrice() + "");
            float newPrice = cart.getProduct().getProductPrice() * (1 - cart.getProduct().getProductDiscount() / 100);
        }
    }

    @Override
    public int getItemCount() {
        if(productOnCart != null){
            return productOnCart.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemProductOrderBinding binding;
        public ViewHolder(@NonNull ItemProductOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
