package com.tlu.edu.vn.ht63.btl_nhom10;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tlu.edu.vn.ht63.btl_nhom10.Model.Product;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.ProductReponsitory;

public class ProductViewModel extends ViewModel {
    private ProductReponsitory productReponsitory;
    public MutableLiveData<String> productName = new MutableLiveData<>();
    public MutableLiveData<String> productDescription = new MutableLiveData<>();
    public MutableLiveData<String> productPrice = new MutableLiveData<>();
    public MutableLiveData<String> productCategory = new MutableLiveData<>();
    public MutableLiveData<String> productDiscount = new MutableLiveData<>();
    public MutableLiveData<Boolean> insertSuccess = new MutableLiveData<>();
    public MutableLiveData<Boolean> enableButton = new MutableLiveData<>(true);
    public MutableLiveData<String> discountError = new MutableLiveData<>();

    public MutableLiveData<Boolean> checkFile = new MutableLiveData<>();
    public ProductViewModel(Context context){
        productReponsitory = new ProductReponsitory(context);
    }

    public void addProduct(String uri){

        if(productDescription.getValue() == null || productName.getValue() == null
                || productPrice.getValue() == null || productCategory.getValue() == null
                || productDiscount.getValue() == null){
            checkFile.setValue(false);
            return ;
        }
        checkFile.setValue(true);
        
        String name = productName.getValue();
        String description = productDescription.getValue();
        Log.i("price", productPrice.getValue().toString());
        float price = Float.parseFloat(productPrice.getValue());
        String category = productCategory.getValue();
        float discount = Float.parseFloat(productDiscount.getValue());
        int productId = name.hashCode();
        if(discount > 100){
            discountError.setValue("Discount must be less than 100");
            return;
        }
        else{
            discountError.setValue(null);
        }

        Product product = new Product();
        product.setProductId(productId);
        product.setProductName(name);
        product.setProductCategory(category);
        product.setProductPrice(price);
        product.setProductDescription(description);
        product.setProductDiscount(discount);
        product.setNumberOfBuyers(0);

        enableButton.setValue(false);

        productReponsitory.uploadImage_Storage(Uri.parse(uri), new ProductReponsitory.AddListenerUploadImage() {
            @Override
            public void getUrlImage(String url) {
                product.setProductImage(url);
                productReponsitory.insert(product, new ProductReponsitory.ChangeProductCallback() {
                    @Override
                    public void changeCallback(boolean success) {
                        insertSuccess.setValue(success);
                        enableButton.setValue(true);
                    }
                });
            }
        });

    }


}
