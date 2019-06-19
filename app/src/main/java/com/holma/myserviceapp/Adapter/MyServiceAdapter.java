package com.holma.myserviceapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.holma.myserviceapp.Interface.IOnRecyclerViewClickListener;
import com.holma.myserviceapp.Model.Service;
import com.holma.myserviceapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyServiceAdapter extends RecyclerView.Adapter<MyServiceAdapter.MyViewHolder> {

    Context context;
    List<Service> serviceList;

    public MyServiceAdapter(Context context, List<Service> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_service, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(serviceList.get(position).getImage()).into(holder.img_service);
        holder.txt_service_name.setText(new StringBuilder(serviceList.get(position).getAddress()));
        holder.txt_service_address.setText(new StringBuilder(serviceList.get(position).getName()));

        holder.setListener((view, position1) -> {
            //Implement later
            Toast.makeText(context, ""+serviceList.get(position).getName(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.txt_service_name)
        TextView txt_service_name;
        @BindView(R.id.txt_service_address)
        TextView txt_service_address;
        @BindView(R.id.img_service)
        ImageView img_service;

        IOnRecyclerViewClickListener listener;

        public void setListener(IOnRecyclerViewClickListener listener) {
            this.listener = listener;
        }

        Unbinder unbinder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            listener.onClick(view, getAdapterPosition());
        }
    }
}
