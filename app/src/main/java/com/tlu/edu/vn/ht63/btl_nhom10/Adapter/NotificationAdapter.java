package com.tlu.edu.vn.ht63.btl_nhom10.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tlu.edu.vn.ht63.btl_nhom10.Model.Notification;
import com.tlu.edu.vn.ht63.btl_nhom10.NotificationViewModel;
import com.tlu.edu.vn.ht63.btl_nhom10.databinding.NotificationItemBinding;

import java.util.List;

public class NotificationAdapter extends  RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    List<Notification> notifications;
    private Context context;
    private NotificationViewModel viewModel;

    public NotificationAdapter(Context context, List<Notification> notifications){
        this.context = context;
        this.notifications = notifications;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNotifications(List<Notification> notifications){
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    public void setViewModel(NotificationViewModel viewModel){
        this.viewModel = viewModel;
    }

    public void addNotification(Notification notification){
        notifications.add(0, notification);
        notifyItemInserted(0);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotificationItemBinding binding = NotificationItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.binding.txtTitle.setText(notification.getTitle());
        holder.binding.txtOrderId.setText(notification.getOrderId() + "");
        holder.binding.txtMessage.setText(notification.getMessage());
        holder.binding.txtCreateDate.setText(notification.getCreatedAt());

        if(notification.isNew()){
            holder.binding.itemNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.countNewNotification.setValue(viewModel.countNewNotification.getValue() - 1);
                    notification.setNew(false);
                    viewModel.updateNotification(notification);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
       if(notifications != null){
           return notifications.size();
       }
       return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public NotificationItemBinding binding;
        public ViewHolder(@NonNull NotificationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
