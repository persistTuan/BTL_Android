package com.tlu.edu.vn.ht63.btl_nhom10;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    private static MySharedPreferences instance;
    private static SharedPreferences sharedPreferences;
     public static MySharedPreferences getInstance(Context context) {
         if(instance == null) {
             synchronized (MySharedPreferences.class) {
                 if (instance == null) {
                     sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);
                     instance = new MySharedPreferences();
                 }
             }
         }
         return instance;
     }

     public void putString(String key, String value){
         SharedPreferences.Editor editor = sharedPreferences.edit();
         editor.putString(key, value);
         editor.apply();
     }

     public String getString(String key){
         return sharedPreferences.getString(key, null);
     }

     public boolean contain(String key){
         return sharedPreferences.contains(key);
     }

     public void clear(){
         SharedPreferences.Editor editor = sharedPreferences.edit();
         editor.clear();
         editor.apply();
     }
}
