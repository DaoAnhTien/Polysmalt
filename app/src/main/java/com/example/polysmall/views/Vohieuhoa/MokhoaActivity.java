package com.example.polysmall.views.Vohieuhoa;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.databinding.ActivityMokhoaBinding;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MokhoaActivity extends Base_Activity {
    private ActivityMokhoaBinding binding;
    Api_Polysmall api_polysmall;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMokhoaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        API();
        initView();
        OnchageTEXT();
    }
    void API() {
        api_polysmall = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_Polysmall.class);
    }
    void OnchageTEXT() {
        binding.inputMokhoasodienthoai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.Thongbaoloi.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        binding.inputMokhoaemail.addTextChangedListener(new TextWatcher() {
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
    void initView(){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String SDT = "^(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})$";
        binding.btnMokhoataikhoan.setOnClickListener(view -> {
            String str_sodienthoai = binding.inputMokhoasodienthoai.getText().toString().trim();
            String str_email = binding.inputMokhoaemail.getText().toString().trim();
            if(TextUtils.isEmpty(str_sodienthoai)||(TextUtils.isEmpty(str_email))){
                binding.Thongbaoloi.setText("Không được để trống");
                binding.Thongbaoloi.setVisibility(View.VISIBLE);
            }else if(!str_email.matches(emailPattern)){
                binding.Thongbaoloi.setText("Email không hợp lệ");
                binding.Thongbaoloi.setVisibility(View.VISIBLE);
            }else if(!str_sodienthoai.matches(SDT)){
                binding.Thongbaoloi.setText("Số điện thoại không hợp lệ");
                binding.Thongbaoloi.setVisibility(View.VISIBLE);
            } else{
                compositeDisposable.add(api_polysmall.MOKHOATAIKHOAN(str_sodienthoai,str_email,Utils.user_current.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    if (userModel.isSuccess()){
                                        Utils.user_current = userModel.getResult().get(0);
                                        navigate(MokhoaTaikhoanthanhcongActivity.class);
                                        binding.inputMokhoasodienthoai.setText("");
                                        binding.inputMokhoaemail.setText("");
                                        finish();
                                    }else {
                                     showMsg(userModel.getMessage());
                                    }
                                }, throwable -> {
                                    showMsg(throwable.getMessage());
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
            binding.inputMokhoaemail.setText("");
            binding.inputMokhoasodienthoai.setText("");
        }
    }
}