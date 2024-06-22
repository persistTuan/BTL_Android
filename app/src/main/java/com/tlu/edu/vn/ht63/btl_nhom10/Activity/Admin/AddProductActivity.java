package com.tlu.edu.vn.ht63.btl_nhom10.Activity.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tlu.edu.vn.ht63.btl_nhom10.Activity.MainActivity;
import com.tlu.edu.vn.ht63.btl_nhom10.LoginViewModel;
import com.tlu.edu.vn.ht63.btl_nhom10.ProductViewModel;
import com.tlu.edu.vn.ht63.btl_nhom10.R;
import com.tlu.edu.vn.ht63.btl_nhom10.databinding.ActivityAccountBinding;
import com.tlu.edu.vn.ht63.btl_nhom10.databinding.ActivityAddProductBinding;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddProductActivity extends AppCompatActivity {
    private ActivityAddProductBinding binding;
    private ProductViewModel productViewModel;
    private boolean isUploadImage_fromDevice = false;
    private boolean isUploadImage_fromUrl = false;
    private boolean existUrlImage = false;
    private String imageUrl;
    private String[] listCategory = {"Nước ngọt", "Trà sữa", "Cà phê"};
    private ArrayAdapter<String> adapterCategory;
    private ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_product);

        productViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                if(modelClass.isAssignableFrom(ProductViewModel.class)){
                    return (T) new ProductViewModel(getApplication());
                }
                return null;
            }
        }).get(ProductViewModel.class);
        binding.setProductViewModel(productViewModel);


        registerResult();

        adapterCategory = new ArrayAdapter<>(this, R.layout.item_category, listCategory);

        binding.autoCompleteTextView.setAdapter(adapterCategory);
        binding.autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                productViewModel.productCategory.setValue(listCategory[position]);
            }
        });


        binding.btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                resultLauncher.launch(intent);
            }
        });


        binding.btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUrl != null){
                    productViewModel.addProduct(imageUrl);
                }
                else{
                    showDialog("Bạn chưa chọn ảnh");
                }
            }
        });


        observeOfProductViewModel();



    }


    private void registerResult(){
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try{
                            Uri uri = result.getData().getData();
                            binding.imgProduct.setImageURI(uri);
                            imageUrl = uri.toString();
                        }catch (Exception e){
                            imageUrl = null;
                            showDialog("Bạn chưa chọn ảnh");
                        }
                    }
                }
        );
    }


    private void showDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage(title);

        // Thêm nút Positive
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Xử lý khi người dùng nhấn OK
                dialog.dismiss();
            }
        });

        // Tạo và hiển thị dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void clearInput(){
        binding.txtProductName.setText(null);
        binding.txtProductDiscount.setText(null);
        binding.txtProductDescription.setText(null);
        binding.txtProductDiscount.setText(null);
        binding.txtProductPrice.setText(null);
        binding.autoCompleteTextView.setText(null);
    }

    public void observeOfProductViewModel(){
        productViewModel.insertSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Toast.makeText(AddProductActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    binding.imgProduct.setImageResource(R.drawable.broken_image_24px);
                    imageUrl = null;
                    clearInput();
                }
                else{
                    showDialog("Không được để trống các ô");
                }
            }
        });

        productViewModel.discountError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.txtProductDiscount.setError(s);
            }
        });

        productViewModel.enableButton.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.btnAddProduct.setEnabled(aBoolean);
            }
        });
    }



}