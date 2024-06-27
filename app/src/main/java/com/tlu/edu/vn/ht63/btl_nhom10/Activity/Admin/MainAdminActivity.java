package com.tlu.edu.vn.ht63.btl_nhom10.Activity.Admin;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.tlu.edu.vn.ht63.btl_nhom10.Adapter.Admin.ViewPager2AdminAdater;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Notification;
import com.tlu.edu.vn.ht63.btl_nhom10.NotificationViewModel;
import com.tlu.edu.vn.ht63.btl_nhom10.ProductViewModel;
import com.tlu.edu.vn.ht63.btl_nhom10.R;
import com.tlu.edu.vn.ht63.btl_nhom10.databinding.ActivityMainAdminBinding;

public class MainAdminActivity extends AppCompatActivity {

    private ActivityMainAdminBinding binding;
    private NotificationViewModel notificationViewModel;
    private ProductViewModel viewModel;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_admin);

        initBingAndViewModel();

        actionView();

        observeViewModel();

    }

    private void observeViewModel() {
//        notificationViewModel.newNotification.observe(this, new Observer<Notification>() {
//            @Override
//            public void onChanged(Notification notification) {
//                // thực hiện cập nhật badges ở đây
//                bottomNavigationView.getOrCreateBadge(R.id.notifications);
//                bottomNavigationView.getBadge(R.id.notifications).setNumber(0);
//            }
//        });
    }

    private void actionView() {
        bottomNavigationView = binding.bottomNavigationView;
        binding.viewPager2.setAdapter(new ViewPager2AdminAdater(this));
        binding.viewPager2.setUserInputEnabled(false);

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.home) {
                    binding.viewPager2.setCurrentItem(0);
                } else if (id == R.id.notifications) {
                    binding.viewPager2.setCurrentItem(1);
                } else if (id == R.id.saleStatistics) {
                    binding.viewPager2.setCurrentItem(2);
                } else {
                    binding.viewPager2.setCurrentItem(0);
                }
                return true;
            }
        });
    }

    private void initBingAndViewModel() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_admin);
        notificationViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new NotificationViewModel(getBaseContext());
            }
        }).get(NotificationViewModel.class);
    }



}