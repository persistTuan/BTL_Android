package com.tlu.edu.vn.ht63.btl_nhom10;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tlu.edu.vn.ht63.btl_nhom10.Model.Cart;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Product;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.User;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.CartReponsitory;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.ProductReponsitory;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.UserReponsitory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HomeViewModel extends ViewModel {
    private ProductReponsitory productReponsitory;
    private CartReponsitory cartReponsitory;
    private UserReponsitory userReponsitory;
    private User userCurrent;
    public MutableLiveData<List<Product>> productList= new MutableLiveData<>();
    public MutableLiveData<Boolean> existCart = new MutableLiveData<>();
    public MutableLiveData<List<Product>> top5Product = new MutableLiveData<>();


    public HomeViewModel(Context context){
        productReponsitory = new ProductReponsitory(context);
        cartReponsitory = new CartReponsitory(context);
        userReponsitory = new UserReponsitory(context);
        userCurrent = userReponsitory.getUserCurrent();
    }

    public void loadProduct(){
        productReponsitory.getAll(new ProductReponsitory.ISetProductCallback() {
            @Override
            public void getProductCallback(List<Product> product) {
                productList.setValue(product);
                loadTop5Product();
            }
        });
    }

    public void loadTop5Product(){
        List<Product> tmpList = productList.getValue();
        List<Product> top5 = tmpList.stream()
                .sorted(Comparator.comparingDouble(Product::getNumberOfBuyers).reversed())
                .limit(5)
                .collect(Collectors.toList());
        top5Product.setValue(top5);
    }

    public void addToCart(View view, int idProduct){


        if(cartReponsitory.checkCart(userCurrent.getUserId(), idProduct)){
            existCart.setValue(true);
        }
        else{
            Cart cart = new Cart(userCurrent.getUserId(), idProduct);
            cartReponsitory.insert(cart);
            Toast.makeText(view.getContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            existCart.setValue(false);
        }
    }

}
