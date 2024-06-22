package com.tlu.edu.vn.ht63.btl_nhom10.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tlu.edu.vn.ht63.btl_nhom10.CartViewModel;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Cart;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Product;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.ProductReponsitory;
import com.tlu.edu.vn.ht63.btl_nhom10.databinding.ItemCartBinding;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewModel> {
    private List<Cart> cartList;
    final private ProductReponsitory productReponsitory;
    final private Context context;
    final private CartViewModel viewModel;

    public CartAdapter(Context context, List<Cart> cartList, CartViewModel viewModel){
        this.context = context;
        this.viewModel = viewModel;
        this.cartList = cartList;
        productReponsitory = new ProductReponsitory(context);
    }
    public void setCarts(List<Cart> carts){
        cartList = carts;
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
        Cart cart = cartList.get(position);
        holder.binding.txtQuantity.setText(cart.getQuantity() + "");
        productReponsitory.getProductById(cart.getProductId(), new ProductReponsitory.GetProductCallback() {
            @Override
            public void getProductCallback(Product product) {
                float totalPrice = Float.parseFloat(viewModel.totalPrice.getValue());
                totalPrice += product.getProductPrice() * (1 - product.getProductDiscount()) * cart.getQuantity();
                viewModel.totalPrice.setValue(totalPrice + "");
                holder.binding.txtProductDescription.setText(product.getProductDescription());
                holder.binding.txtPrice.setText(product.getProductPrice() + "");
                holder.binding.iconAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        float newTotal = Float.parseFloat(viewModel.totalPrice.getValue());
                        cart.setQuantity(cart.getQuantity() + 1);
                        newTotal += product.getProductPrice() * (1 - product.getProductDiscount());
                        holder.binding.txtQuantity.setText(cart.getQuantity() + "");
                        viewModel.totalPrice.setValue(newTotal+"");
                        viewModel.updateCart(cart);
                    }
                });
                holder.binding.iconRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(cart.getQuantity() > 1){
                            float newTotal = Float.parseFloat(viewModel.totalPrice.getValue());
                            newTotal -= product.getProductPrice() * (1 - product.getProductDiscount());
                            cart.setQuantity(cart.getQuantity() - 1);
                            holder.binding.txtQuantity.setText(cart.getQuantity() + "");
                            viewModel.totalPrice.setValue(newTotal+"");
                            viewModel.updateCart(cart);
                        }
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        if(cartList != null){
            return cartList.size();
        }else{
            return 0;
        }
    }

    private void calculateTotal(Product product){
        int total = 0;
        for(Cart cart : cartList){
            float newPrice = product.getProductPrice() * (1 - product.getProductDiscount());
            total += cart.getQuantity() * newPrice;
        }
        viewModel.totalPrice.setValue(total+"");
    }

    public class ViewModel extends RecyclerView.ViewHolder {
        public ItemCartBinding binding;
        public ViewModel(@NonNull ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnListenerCart{
        public void updateCart(int productID, int quantity);
    }
}
