package com.example.polysmall.views.user;


import android.os.Bundle;
import android.view.View;

import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.databinding.ActivityKetquatimkiemThatbaiBinding;

public class Ketquatimkiem_thatbai_Activity extends Base_Activity {
    private ActivityKetquatimkiemThatbaiBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKetquatimkiemThatbaiBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.ketquatimkiemthatbaiThulai.setOnClickListener(view1 -> {
            navigate(Truyvantaikhoan_Activity.class);
            finish();
        });
    }
}