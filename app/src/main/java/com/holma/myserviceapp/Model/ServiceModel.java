package com.holma.myserviceapp.Model;

import java.util.List;

public class ServiceModel {
    private boolean success;
    private String message;
    private List<Service> result;

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

    public List<Service> getResult() {
        return result;
    }

    public void setResult(List<Service> result) {
        this.result = result;
    }
}
