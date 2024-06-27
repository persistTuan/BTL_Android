package com.tlu.edu.vn.ht63.btl_nhom10.Adapter.Admin;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.tlu.edu.vn.ht63.btl_nhom10.Fragment.Admin.HomeAdminFragment;
import com.tlu.edu.vn.ht63.btl_nhom10.Fragment.Admin.NotificationAdminFragment;
import com.tlu.edu.vn.ht63.btl_nhom10.Fragment.Admin.SaleStatisticsFragment;

public class ViewPager2AdminAdater extends FragmentStateAdapter {
    public ViewPager2AdminAdater(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HomeAdminFragment();
            case 1:
                return new NotificationAdminFragment();
            case 2:
                return new SaleStatisticsFragment();
            default:
                return new HomeAdminFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
