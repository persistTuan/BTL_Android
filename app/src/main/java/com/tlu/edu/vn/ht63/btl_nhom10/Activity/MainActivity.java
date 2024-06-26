package com.tlu.edu.vn.ht63.btl_nhom10.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.tlu.edu.vn.ht63.btl_nhom10.Adapter.ViewPager2Adapter;
import com.tlu.edu.vn.ht63.btl_nhom10.HomeViewModel;
import com.tlu.edu.vn.ht63.btl_nhom10.R;
import com.tlu.edu.vn.ht63.btl_nhom10.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private HomeViewModel homeViewModel;
    private ActivityMainBinding binding;


    BottomNavigationView bottomNavigationView;
    ViewPager2 viewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        homeViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new HomeViewModel(getBaseContext());
            }
        }).get(HomeViewModel.class);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        viewPager2 = findViewById(R.id.viewPager2);

        viewPager2.setAdapter(new ViewPager2Adapter(this));
        viewPager2.setUserInputEnabled(false);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.home) {
                    viewPager2.setCurrentItem(0);
                } else if (id == R.id.notifications) {
                    viewPager2.setCurrentItem(1);
                } else if (id == R.id.account) {
                    viewPager2.setCurrentItem(2);
                } else {
                    viewPager2.setCurrentItem(0);
                }
                return true;
            }
        });
    }
}