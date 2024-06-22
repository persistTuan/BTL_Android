package com.tlu.edu.vn.ht63.btl_nhom10.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tlu.edu.vn.ht63.btl_nhom10.HomeViewModel;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Product;
import com.tlu.edu.vn.ht63.btl_nhom10.R;
import com.tlu.edu.vn.ht63.btl_nhom10.databinding.ItemProductBinding;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    HomeViewModel homeViewModel;
    private Context context;
    public ProductAdapter(Context context, List<Product> productList, HomeViewModel homeViewModel){
        this.context = context;
        this.productList = productList;
        this.homeViewModel = homeViewModel;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.item_product, parent, false);
        binding.setHomeViewModel(homeViewModel);

        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.binding.setIdProduct(product.getProductId());
        holder.binding.btnAddCart.setText("Add to cart");

        holder.binding.txtProductDescription.setText(product.getProductDescription());
        float priceCurrent = product.getProductPrice() - (product.getProductPrice() * product.getProductDiscount() / 100);
        if(product.getProductDiscount()==0)
            holder.binding.txtPriceOld.setVisibility(View.GONE);
        else{
            holder.binding.txtPriceOld.setVisibility(View.VISIBLE);
            holder.binding.txtPriceOld.setText(product.getProductPrice()+"");
            holder.binding.txtPriceOld.setPaintFlags(holder.binding.txtPriceOld.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.binding.txtPriceCurrent.setText(priceCurrent+"");
        holder.binding.setHomeViewModel(this.homeViewModel);
    }

    @Override
    public int getItemCount() {
        if(productList != null)
            return productList.size();
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        public ItemProductBinding binding;
        public ProductViewHolder(@NonNull ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ICart{
        void onCart(Product product);
    }
}
