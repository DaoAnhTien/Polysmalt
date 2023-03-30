package com.example.polysmall.controller.models.model;

import com.example.polysmall.controller.models.Thongke;

import java.util.List;

public class ThongkeModel {
    boolean success;
    String message;
    List<Thongke> result;

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

    public List<Thongke> getResult() {
        return result;
    }

    public void setResult(List<Thongke> result) {
        this.result = result;
    }
}
