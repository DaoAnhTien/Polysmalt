package com.example.polysmall.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.polysmall.R;
import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.databinding.ActivityQuenmkBinding;
import com.example.polysmall.views.user.Ketquatimkiem_Activity;
import com.example.polysmall.views.user.sendthanhcongActivity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Quenmk_Activity extends Base_Activity {
    private ActivityQuenmkBinding binding;
    Api_Polysmall api_polysmall;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quenmk);
        binding = ActivityQuenmkBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initview();
        API();
    }

    void initview() {
        binding.cardQuaylai.setOnClickListener(view -> {
            navigate(Ketquatimkiem_Activity.class);
            finish();
        });
        binding.btnQuenmatkhau.setOnClickListener(view -> {
            QUENMATKHAU();
            return;
        });
        binding.edtEmail.setOnClickListener(view -> {
            binding.txterr.setVisibility(View.GONE);
            return;
        });
    }

    @SuppressLint("SetTextI18n")
    void QUENMATKHAU() {
        String str_email = binding.edtEmail.getText().toString().trim();
        if (TextUtils.isEmpty(str_email)){
            binding.txterr.setText("Vui lòng nhập Email");
            binding.txterr.setVisibility(View.VISIBLE);
            return;
        }else {
            binding.ProgressBar.setVisibility(View.VISIBLE);
            compositeDisposable.add(api_polysmall.QUENMATKHAU(str_email)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            userModel -> {
                                if (userModel.isSuccess()) {
                                    navigate(sendthanhcongActivity.class);
                                    finish();
                                } else {
                                    binding.txterr.setVisibility(View.VISIBLE);
                                    binding.txterr.setText(userModel.getMessage());
                                }
                                binding.ProgressBar.setVisibility(View.INVISIBLE);
                            },
                            throwable -> {
                                binding.txterr.setVisibility(View.VISIBLE);
                                binding.txterr.setText(throwable.getMessage());
                                binding.ProgressBar.setVisibility(View.INVISIBLE);
                            }));
            return;
        }
    }

    void API() {
        api_polysmall = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_Polysmall.class);
    }
}