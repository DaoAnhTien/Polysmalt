package com.example.polysmall.controller.models.model;

import com.example.polysmall.controller.models.Danhmuc;

import java.util.List;

public class DanhmucModel {
    boolean success;
    String message;
    List<Danhmuc> result;

    public DanhmucModel() {
    }

    public DanhmucModel(boolean success, String message, List<Danhmuc> result) {
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

    public List<Danhmuc> getResult() {
        return result;
    }

    public void setResult(List<Danhmuc> result) {
        this.result = result;
    }
}
