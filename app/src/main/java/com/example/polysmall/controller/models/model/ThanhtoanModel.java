package com.example.polysmall.controller.models.model;

import com.example.polysmall.controller.models.Thanhtoan;
import com.example.polysmall.controller.models.User;

import java.util.List;

public class ThanhtoanModel {
    boolean success;
    String message;
    List<Thanhtoan> result;

    public ThanhtoanModel() {
    }

    public ThanhtoanModel(boolean success, String message, List<Thanhtoan> result) {
        this.success = success;
        this.message = message;
        this.result = result;
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

    public List<Thanhtoan> getResult() {
        return result;
    }

    public void setResult(List<Thanhtoan> result) {
        this.result = result;
    }
}
