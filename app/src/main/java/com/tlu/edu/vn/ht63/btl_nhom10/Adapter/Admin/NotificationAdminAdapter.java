package com.tlu.edu.vn.ht63.btl_nhom10.Adapter.Admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tlu.edu.vn.ht63.btl_nhom10.Model.Notification;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Order;
import com.tlu.edu.vn.ht63.btl_nhom10.NotificationViewModel;
import com.tlu.edu.vn.ht63.btl_nhom10.R;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.OrderReponsitory;
import com.tlu.edu.vn.ht63.btl_nhom10.databinding.ItemNotificationAdminBinding;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class NotificationAdminAdapter extends RecyclerView.Adapter<NotificationAdminAdapter.ViewHolder>{

    private List<Notification> listNotification;
    private Context context;
    private NotificationViewModel viewModel;

    public NotificationAdminAdapter(Context contextList,
                        List<Notification> listNotification, NotificationViewModel viewModel1) {
        this.listNotification = listNotification;
        this.context = context;
        this.viewModel = viewModel1;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListNotification(List<Notification> listNotification) {
        Log.i("sizeNotification", listNotification.size() + "");

        this.listNotification = listNotification;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNotificationAdminBinding binding = ItemNotificationAdminBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = listNotification.get(position);
        viewModel.getOrderReponsitory().getByOrderId(notification.getOrderId(), new OrderReponsitory.OnListenerGetOrder() {
            @Override
            public void OnGetOrder(Order order) {
                holder.binding.txtOrderId.setText(order.getOrderId() + "");
                NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
                holder.binding.txtTotalPrice.setText(numberFormat.format(order.getTotalMoney()));
                if(!order.isAccepted()){
                    holder.binding.imageItemNotification.setImageResource(R.drawable.notifications_active_24px);
                }
                else{
                    holder.binding.imageItemNotification.setImageResource(R.drawable.check_circle_24px);
                }
                holder.binding.txtCreateDate.setText(order.getCreatedAt());
            }
        });
        holder.binding.itemNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.showOrderDetail(notification);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listNotification != null){
            return listNotification.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ItemNotificationAdminBinding binding;
        public ViewHolder(@NonNull ItemNotificationAdminBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
