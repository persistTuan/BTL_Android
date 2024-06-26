package com.tlu.edu.vn.ht63.btl_nhom10.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tlu.edu.vn.ht63.btl_nhom10.CartViewModel;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Cart;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.CartAndUserWithProducts;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Product;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.ProductReponsitory;
import com.tlu.edu.vn.ht63.btl_nhom10.databinding.ItemCartBinding;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewModel> {
    private List<CartAndUserWithProducts> productOnCart;
    final private Context context;
    final private CartViewModel viewModel;

    public CartAdapter(Context context, List<CartAndUserWithProducts> productOnCart, CartViewModel viewModel){
        this.context = context;
        this.viewModel = viewModel;
        this.productOnCart = productOnCart;
    }
    public void setProductOnCart(List<CartAndUserWithProducts> productOnCart) {
        this.productOnCart = productOnCart;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         ItemCartBinding binding = ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
         return new ViewModel(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewModel holder, int position) {
        if(viewModel.totalPrice.getValue() == null){
            viewModel.totalPrice.setValue("0");
        }

        CartAndUserWithProducts cart = productOnCart.get(position);
        Product product = productOnCart.get(position).getProduct();

        float totalPrice = Float.parseFloat(viewModel.totalPrice.getValue());
        totalPrice += product.getProductPrice() * (1 - product.getProductDiscount()) * cart.getQuantity();
        viewModel.totalPrice.setValue(totalPrice + "");

        holder.binding.txtQuantity.setText(cart.getQuantity() + "");
        holder.binding.txtProductDescription.setText(product.getProductDescription());
        holder.binding.txtPrice.setText(product.getProductPrice() + "");
        holder.binding.iconAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.setQuantity(cart.getQuantity() + 1);
                holder.binding.txtQuantity.setText(cart.getQuantity() + "");

                float newTotal = Float.parseFloat(viewModel.totalPrice.getValue());
                newTotal += product.getProductPrice() * (1 - product.getProductDiscount());
                viewModel.totalPrice.setValue(newTotal+"");


                Cart newCart = new Cart();
                newCart.setUserId(cart.getUserId());
                newCart.setQuantity(cart.getQuantity());
                newCart.setProductId(product.getProductId());
                newCart.setCartId(cart.getCartId());
                viewModel.updateCart(newCart);
            }
        });
        holder.binding.iconRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cart.getQuantity() > 1){
                    cart.setQuantity(cart.getQuantity() - 1);
                    holder.binding.txtQuantity.setText(cart.getQuantity() + "");
                    float newTotal = Float.parseFloat(viewModel.totalPrice.getValue());
                    newTotal -= product.getProductPrice() * (1 - product.getProductDiscount());
                    viewModel.totalPrice.setValue(newTotal+"");

                    Cart newCart = new Cart();
                    newCart.setUserId(cart.getUserId());
                    newCart.setQuantity(cart.getQuantity());
                    newCart.setProductId(product.getProductId());
                    newCart.setCartId(cart.getCartId());
                    viewModel.updateCart(newCart);
                }
            }
        });

        holder.binding.iconDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productOnCart.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, productOnCart.size());
                Log.i("totalPrice", calculateTotalPrice()+"");
                viewModel.totalPrice.setValue(calculateTotalPrice()+"");
                Cart newCart = new Cart();
                newCart.setCartId(cart.getCartId());
                viewModel.deleteCart(newCart);
            }
        });
    }

    @Override
    public int getItemCount() {

        if(productOnCart != null){
            return productOnCart.size();
        }else{
            return 0;
        }
    }

    public float calculateTotalPrice(){
        float totalPrice = 0;
        for(CartAndUserWithProducts cart : productOnCart){
            Product product = cart.getProduct();
            totalPrice += product.getProductPrice() * (1 - product.getProductDiscount()) * cart.getQuantity();
        }
        return totalPrice;
    }

    public class ViewModel extends RecyclerView.ViewHolder {
        public ItemCartBinding binding;
        public ViewModel(@NonNull ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
