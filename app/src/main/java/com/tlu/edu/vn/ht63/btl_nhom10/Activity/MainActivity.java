package com.tlu.edu.vn.ht63.btl_nhom10.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

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
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.tlu.edu.vn.ht63.btl_nhom10.Adapter.ViewPager2Adapter;
import com.tlu.edu.vn.ht63.btl_nhom10.HomeViewModel;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Notification;
import com.tlu.edu.vn.ht63.btl_nhom10.NotificationViewModel;
import com.tlu.edu.vn.ht63.btl_nhom10.R;
import com.tlu.edu.vn.ht63.btl_nhom10.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private NotificationViewModel notificationViewModel;
    private ActivityMainBinding binding;

    private BadgeDrawable badge;

    BottomNavigationView bottomNavigationView;
    ViewPager2 viewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new NotificationViewModel(getBaseContext());
            }
        }).get(NotificationViewModel.class);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        notificationViewModel.startListeningForNotification();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        viewPager2 = findViewById(R.id.viewPager2);
        badge = bottomNavigationView.getOrCreateBadge(R.id.notifications);


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

        obeserveNotificationViewModel();
    }

    private void obeserveNotificationViewModel() {
        notificationViewModel.newNotification.observe(this, new Observer<Notification>() {
            @Override
            public void onChanged(Notification notification) {
                Toast.makeText(MainActivity.this, "New Notification", Toast.LENGTH_SHORT).show();
                badge.setVisible(true);
                badge.setNumber(0);
            }
        });

        notificationViewModel.hideBadge.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    badge.clearNumber();
                }
            }
        });

        notificationViewModel.countNewNotification.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 0){
                    badge.setVisible(false);
                    badge.clearNumber();
                }
                else{
                    badge.setVisible(true);
                    badge.setNumber(integer);
                }
            }
        });
    }
}