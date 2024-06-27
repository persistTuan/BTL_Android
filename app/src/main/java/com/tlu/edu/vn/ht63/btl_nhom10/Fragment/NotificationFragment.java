package com.tlu.edu.vn.ht63.btl_nhom10.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlu.edu.vn.ht63.btl_nhom10.Adapter.Admin.ProductOnOrderAdapter;
import com.tlu.edu.vn.ht63.btl_nhom10.Adapter.NotificationAdapter;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Notification;
import com.tlu.edu.vn.ht63.btl_nhom10.NotificationViewModel;
import com.tlu.edu.vn.ht63.btl_nhom10.R;
import com.tlu.edu.vn.ht63.btl_nhom10.databinding.FragmentNotificationBinding;

import java.util.List;


public class NotificationFragment extends Fragment {

    private FragmentNotificationBinding binding;
    private NotificationViewModel viewModel;

    private NotificationAdapter adapter;

    public NotificationFragment() {
        // Required empty public constructor
    }


    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new NotificationViewModel(getContext());
            }
        }).get(NotificationViewModel.class);

        oberserViewModel();

        viewModel.loadNotification();

        actionView();
        return binding.getRoot();
    }

    private void actionView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                                    layoutManager.getOrientation());
        binding.rcvNotification.setLayoutManager(layoutManager);
        binding.rcvNotification.addItemDecoration(dividerItemDecoration);
        adapter = new NotificationAdapter(getContext(), viewModel.listNotification.getValue());
        adapter.setViewModel(viewModel);
        binding.rcvNotification.setAdapter(adapter);

    }

    private void oberserViewModel() {
        viewModel.listNotification.observe(getViewLifecycleOwner(), new Observer<List<Notification>>() {
            @Override
            public void onChanged(List<Notification> notifications) {
                adapter.setNotifications(notifications);
            }
        });

        viewModel.newNotification.observe(getViewLifecycleOwner(), new Observer<Notification>() {
            @Override
            public void onChanged(Notification notification) {
                adapter.addNotification(notification);
            }
        });
    }
}