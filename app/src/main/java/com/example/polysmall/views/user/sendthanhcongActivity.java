package com.example.polysmall.views.user;

import android.os.Bundle;
import android.view.View;

import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.databinding.ActivitySendthanhcongBinding;
import com.example.polysmall.views.Login_Activity;

public class sendthanhcongActivity extends Base_Activity {

    private ActivitySendthanhcongBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySendthanhcongBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.buttonDangnhap.setOnClickListener(view1 -> {
            navigate(Login_Activity.class);
            finish();
        });
    }
}