package com.holma.myserviceapp.EventBus;

import com.holma.myserviceapp.Model.Service;

public class MenuItemEvent {
    private boolean success;
    private Service service;

    public MenuItemEvent(boolean success, Service service) {
        this.success = success;
        this.service = service;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
