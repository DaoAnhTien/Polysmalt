package com.example.polysmall.controller.models.model;

import com.example.polysmall.controller.models.lichsu.Lichsu;

import java.util.List;

public class LichsuModel {
    boolean success;
    String message;
    List<Lichsu> result;

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

    public List<Lichsu> getResult() {
        return result;
    }

    public void setResult(List<Lichsu> result) {
        this.result = result;
    }
}
