package com.example.polysmall.controller.models.model;

import com.example.polysmall.controller.models.Danhsach;

import java.util.List;

public class DanhsachModel {
    boolean success;
    String message;
    List<Danhsach> result;

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

    public List<Danhsach> getResult() {
        return result;
    }

    public void setResult(List<Danhsach> result) {
        this.result = result;
    }
}
