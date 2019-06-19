package com.holma.myserviceapp.Adapter;

import com.holma.myserviceapp.Model.Service;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class ServiceSliderAdapter extends SliderAdapter {

    List<Service> serviceList;

    public ServiceSliderAdapter(List<Service> serviceList) {
        this.serviceList = serviceList;
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
        imageSlideViewHolder.bindImageSlide(serviceList.get(position).getImage());
    }
}
