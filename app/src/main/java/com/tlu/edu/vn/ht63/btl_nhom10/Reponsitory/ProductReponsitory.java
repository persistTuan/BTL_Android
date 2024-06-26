package com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Product;
import com.tlu.edu.vn.ht63.btl_nhom10.R;
import com.tlu.edu.vn.ht63.btl_nhom10.RoomDatabase.Dao.ProductDao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

public class ProductReponsitory{
    private final String NAME_BUCKET = "gs://btn-android-170c2.appspot.com";
    private Context context;
    FirebaseDatabase database;
    FirebaseStorage myStorage;
    StorageReference storageReference;

    DatabaseReference myRef;
    List<Product> products;
    private static final String NODE_PRODUCT  = "products";
    public ProductReponsitory(Context context){
        this.context = context;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(NODE_PRODUCT);
        products = new ArrayList<>();
        myStorage = FirebaseStorage.getInstance(NAME_BUCKET);
        storageReference = myStorage.getReference("imageProducts");
    }

    public void insert(Product product, ChangeProductCallback callback){
        String keyNode = myRef.push() + "";
        String keyProduct = keyNode.hashCode() + "";
        product.setProductId(keyProduct);
        myRef.child(keyProduct+"").setValue(product)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("insert", task.isSuccessful() + "");
                        callback.changeCallback(task.isSuccessful());
                    }
                });
    }

    public void delete(Product product, ChangeProductCallback callback){
        myRef.child(product.getProductId()+"").removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        callback.changeCallback(task.isSuccessful());
                    }
                });
    }

    public void getAll(ISetProductCallback callback){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> productList = new ArrayList<>();
                for (DataSnapshot product : snapshot.getChildren()){
                    Product product1 = new Product();
                    product1.setProductImage(product.child("productImage").getValue(String.class));
                    product1.setProductName(product.child("productName").getValue(String.class));
                    product1.setProductPrice(product.child("productPrice").getValue(Float.class));
                    String id = product.getKey();
                    product1.setProductId(id);
                    product1.setProductDescription(product.child("productDescription").getValue(String.class));
                    product1.setProductDiscount(product.child("productDiscount").getValue(Float.class));
                    product1.setProductCategory(product.child("productCategory").getValue(String.class));
                    product1.setNumberOfBuyers(product.child("numberOfBuyers").getValue(Integer.class));
                    productList.add(product1);
                }
                callback.getProductCallback(productList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

//    @Override
    public void getProductById(String id, GetProductCallback callback) {
        myRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Product product1 = new Product();
//                product1.setProductImage(snapshot.child("productImage").getValue(String.class));
//                product1.setProductName(snapshot.child("productName").getValue(String.class));
//                product1.setProductPrice(snapshot.child("productPrice").getValue(Float.class));
//                product1.setProductId(String.valueOf(snapshot.child("productId").getValue(Long.class)));
//                product1.setProductDescription(snapshot.child("productDescription").getValue(String.class));
//                product1.setProductDiscount(snapshot.child("productDiscount").getValue(Float.class));
//                product1.setProductCategory(snapshot.child("productCategory").getValue(String.class));
//                product1.setNumberOfBuyers(snapshot.child("numberOfBuyers").getValue(Integer.class));
                callback.getProductCallback(snapshot.getValue(Product.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void update(Product product, ChangeProductCallback callback){
        myRef.child(product.getProductId()).setValue(product).addOnCompleteListener
                (new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                callback.changeCallback(task.isSuccessful());
            }
        });
    }

    public void uploadImage_Storage(Uri uriFile, AddListenerUploadImage callback){
        String pathStorage = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            pathStorage = uriFile.toString().hashCode() + LocalDateTime.now().toString();
        }
        storageReference.child(pathStorage).putFile(uriFile)
        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String url = taskSnapshot.getMetadata().getReference().toString();
                callback.getUrlImage(url);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "upload ảnh thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public interface ChangeProductCallback{
        void changeCallback(boolean success);
    }

    public interface GetProductCallback{
        void getProductCallback(Product product);
    }

    public interface ISetProductCallback{
        void getProductCallback(List<Product> product);
    }

    public interface AddListenerUploadImage{
        void getUrlImage(String url);
    }
}
