package com.example.polysmall.controller.models.Evenrbus;

import com.example.polysmall.controller.models.Sanpham;
import com.example.polysmall.controller.models.User;

public class UserEvenrbus {
    User user;

    public UserEvenrbus(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
