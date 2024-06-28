package com.tlu.edu.vn.ht63.btl_nhom10.Model;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tlu.edu.vn.ht63.btl_nhom10.R;

import java.net.URL;
import java.net.URLDecoder;

public class DownloadImage {
    private static FirebaseStorage storage;


    public static void loadImage(Context context, String storagePath, ImageView imageView, LoadCallback callback) {

        storage = FirebaseStorage.getInstance();
        String decodePath = URLDecoder.decode(storagePath);
        StorageReference imageRef = storage.getReferenceFromUrl(decodePath);

        GlideApp.with(context )
                .load(imageRef)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.baseline_error_24)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

//        MyAppGlideModule.getGlide(context)
//                .load(imageRef)
//                .into(imageView);

    }

    public interface LoadCallback {
        void onSuccess(String imageUrl);
        void onFailure(String error);
    }



}



//        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                String imageUrl = uri.toString();
//                Log.d("DownloadImage", "Image URL: " + imageUrl);
////                Glide.with(context).using(new )
//
//                Glide.with(context)
//                        .load(imageUrl)
//                        .placeholder(R.drawable.ic_launcher_background)
//                        .error(R.drawable.baseline_error_24)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .into(imageView);
//
//                if (callback != null) {
//                    callback.onSuccess(imageUrl);
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                if (callback != null) {
//                    callback.onFailure(e.getMessage());
//                }
//            }
//        });