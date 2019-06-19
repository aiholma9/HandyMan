package com.holma.myserviceapp.EventBus;

import com.holma.myserviceapp.Model.Service;

import java.util.List;

public class ServiceLoadEvent {
    private boolean success;
    private String message;
    private List<Service> serviceList;

    public ServiceLoadEvent(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ServiceLoadEvent(boolean success, List<Service> serviceList) {
        this.success = success;
        this.serviceList = serviceList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }
}
