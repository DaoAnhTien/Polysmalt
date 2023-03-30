package com.example.polysmall.views.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.example.polysmall.R;
import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.databinding.ActivityTruyvantaikhoanBinding;
import com.example.polysmall.views.Login_Activity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Truyvantaikhoan_Activity extends Base_Activity {
    private ActivityTruyvantaikhoanBinding binding;
    Api_Polysmall api_polysmall;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTruyvantaikhoanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        API();
        initView();
        ActionToolBar();
        OnchageTEXT();
    }

    void API() {
        api_polysmall = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_Polysmall.class);
    }
    void OnchageTEXT() {
        binding.inputTruyvanemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.Thongbaoloi.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        binding.inputTruyvansodienthoai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.Thongbaoloi.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }
    void ActionToolBar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationIcon(R.drawable.arrow_back_24);
        binding.toolbar.setNavigationOnClickListener(view -> {
            navigate(Login_Activity.class);
            finish();
        });
    }
    void initView() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String SDT = "^(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})$";
        binding.btnTruyvantaikhoanr.setOnClickListener(view -> {
            String str_sodienthoai = binding.inputTruyvansodienthoai.getText().toString().trim();
            String str_email = binding.inputTruyvanemail.getText().toString().trim();
            if(TextUtils.isEmpty(str_sodienthoai)||(TextUtils.isEmpty(str_email))){
                binding.Thongbaoloi.setText("Không được để trống");
                binding.Thongbaoloi.setVisibility(View.VISIBLE);
            }else if(!str_email.matches(emailPattern)){
                binding.Thongbaoloi.setText("Email không hợp lệ");
                binding.Thongbaoloi.setVisibility(View.VISIBLE);
            }else if(!str_sodienthoai.matches(SDT)){
                binding.Thongbaoloi.setText("Số điện thoại không hợp lệ");
                binding.Thongbaoloi.setVisibility(View.VISIBLE);
            }else {
                compositeDisposable.add(api_polysmall.TRUYVANTAIKHOAN(str_sodienthoai,str_email)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    if (userModel.isSuccess()){
                                        Utils.user_current = userModel.getResult().get(0);
                                        navigate(Ketquatimkiem_Activity.class);
                                        binding.inputTruyvanemail.setText("");
                                        binding.inputTruyvansodienthoai.setText("");
                                        finish();
                                    }else {
                                        navigate(Ketquatimkiem_thatbai_Activity.class);
                                        finish();
                                    }
                                }, throwable -> {
                                    binding.Thongbaoloi.setText(throwable.getMessage());
                                    binding.Thongbaoloi.setVisibility(View.VISIBLE);
                                }
                        ));

            }
        });
    }
    // check
    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.user_current.getMobile() != null && Utils.user_current.getEmail() != null ){
            binding.inputTruyvanemail.setText("");
            binding.inputTruyvansodienthoai.setText("");
        }
    }
}