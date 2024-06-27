package com.tlu.edu.vn.ht63.btl_nhom10.Fragment.Admin;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.tlu.edu.vn.ht63.btl_nhom10.Adapter.Admin.NotificationAdminAdapter;
import com.tlu.edu.vn.ht63.btl_nhom10.Adapter.Admin.ProductOnOrderAdapter;
import com.tlu.edu.vn.ht63.btl_nhom10.Adapter.ProductAdapter;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Notification;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Order;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Product;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.User;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.UserWithOrderAndProduct;
import com.tlu.edu.vn.ht63.btl_nhom10.NotificationViewModel;
import com.tlu.edu.vn.ht63.btl_nhom10.R;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.OrderReponsitory;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.ProductReponsitory;
import com.tlu.edu.vn.ht63.btl_nhom10.databinding.BottomSheetLayoutBinding;
import com.tlu.edu.vn.ht63.btl_nhom10.databinding.FragmentNotificationAdminBinding;
import com.tlu.edu.vn.ht63.btl_nhom10.generated.callback.OnClickListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationAdminFragment extends Fragment {

    private NotificationViewModel viewModel;
    private NotificationAdminAdapter mainAdapter;
    private FragmentNotificationAdminBinding mainBinding;

    private RecyclerView rcvProductOnOrder;
    private UserWithOrderAndProduct products;




    public NotificationAdminFragment() {
        // Required empty public constructor
    }
    public static NotificationAdminFragment newInstance(String param1, String param2) {
        NotificationAdminFragment fragment = new NotificationAdminFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View ViewOrderDetail = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        mainBinding = FragmentNotificationAdminBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new NotificationViewModel(getContext());
            }
        }).get(NotificationViewModel.class);

        rcvProductOnOrder = ViewOrderDetail.findViewById(R.id.rcv_product);

        actionView();
        observeViewModel();

        viewModel.loadNotification();

        return mainBinding.getRoot();
    }

    private void observeViewModel() {
        viewModel.listNotification.observe(getViewLifecycleOwner(), new Observer<List<Notification>>() {
            @Override
            public void onChanged(List<Notification> notifications) {
                mainAdapter.setListNotification(notifications);
            }
        });

        viewModel.newNotification.observe(getViewLifecycleOwner(), new Observer<Notification>() {
            @Override
            public void onChanged(Notification notification) {
                List<Notification> list = viewModel.listNotification.getValue();
                assert list != null;
                list.add(0, notification);
                mainAdapter.setListNotification(list);
            }
        });

        viewModel.obeserveOrderDetail.observe(getViewLifecycleOwner(), new Observer<UserWithOrderAndProduct>() {
            @Override
            public void onChanged(UserWithOrderAndProduct userWithOrderAndProduct) {
                showDialog(userWithOrderAndProduct, viewModel);
            }
        });
    }

    private void actionView() {


        mainAdapter = new NotificationAdminAdapter(getContext(),
                viewModel.listNotification.getValue(), viewModel);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), layoutManager.getOrientation());


        mainBinding.rcvNotification.setLayoutManager(layoutManager);
        mainBinding.rcvNotification.addItemDecoration(dividerItemDecoration);
        mainBinding.rcvNotification.setAdapter(mainAdapter);

    }


    public void showDialog(UserWithOrderAndProduct userWithOrderAndProduct, NotificationViewModel viewModel){
        BottomSheetLayoutBinding sheetBinding = BottomSheetLayoutBinding.inflate(getLayoutInflater());

        Order order = userWithOrderAndProduct.getOrder();
        User user = userWithOrderAndProduct.getUser();
        List<Pair<Product, Integer>> productOnOrder = userWithOrderAndProduct.getProducts();

        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(sheetBinding.getRoot());

        ProductOnOrderAdapter productOnOrderAdapter = new ProductOnOrderAdapter(getContext(), userWithOrderAndProduct);


        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), layoutManager2.getOrientation());
        sheetBinding.rcvProduct.setLayoutManager(layoutManager2);
        sheetBinding.rcvProduct.addItemDecoration(dividerItemDecoration);
        sheetBinding.rcvProduct.setAdapter(productOnOrderAdapter);

        if(order.isAccepted()){
            sheetBinding.txtStatus.setVisibility(View.VISIBLE);
            sheetBinding.btnAccept.setVisibility(View.GONE);
            sheetBinding.btnDenied.setVisibility(View.GONE);
        }else{
            sheetBinding.btnAccept.setVisibility(View.VISIBLE);
            sheetBinding.btnDenied.setVisibility(View.VISIBLE);
            sheetBinding.txtStatus.setVisibility(View.GONE);
        }


        sheetBinding.txtOrderId.setText(order.getOrderId() + "");
        sheetBinding.txtCreatedOrder.setText(order.getCreatedAt());
        sheetBinding.txtDistance.setText(user.getDistance() + " km");
        sheetBinding.txtUserName.setText(user.getName());
        sheetBinding.txtPhoneNumber.setText(user.getPhoneNumber());
        sheetBinding.txtAddress.setText(user.getAddress());

        sheetBinding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.acceptOrder(userWithOrderAndProduct);
                Toast.makeText(getContext(), "Đã chấp nhận đơn hàng", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        sheetBinding.btnDenied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



        sheetBinding.iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}