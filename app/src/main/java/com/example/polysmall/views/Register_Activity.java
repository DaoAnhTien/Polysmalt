package com.example.polysmall.views;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;

import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.example.polysmall.R;
import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.databinding.ActivityRegisterBinding;
import com.example.polysmall.views.user.Hoanthanhdangky_Activity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Register_Activity extends Base_Activity {
    private ActivityRegisterBinding binding;
    Api_Polysmall api_polysmall;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    boolean ischeck = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        API();
        initView();
        OnchageText();
        binding.checkbox.setOnClickListener(view1 -> {
            if (binding.checkbox.isChecked()==ischeck){
                binding.btnDangky.setBackgroundResource(R.color.colorRemove);
                binding.Thongbaoloi.setVisibility(View.GONE);
            }else {
                binding.btnDangky.setBackgroundResource(R.color.colorAccent);
                binding.checkbox.setTextColor(Color.BLACK);
            }
        });
    }

    void OnchageText() {
        binding.inputHo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.Thongbaoloi.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        binding.inputTen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.Thongbaoloi.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        binding.inputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.Thongbaoloi.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        binding.inputPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.Thongbaoloi.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        binding.inputDiachi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.Thongbaoloi.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        binding.inputPasword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.Thongbaoloi.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        binding.inputRePasword.addTextChangedListener(new TextWatcher() {
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

    void API() {
        api_polysmall = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_Polysmall.class);
    }
    void initView() {
        binding.btnDangky.setOnClickListener(view -> {
            dangky_user();
        });

        binding.iconBackRe.setOnClickListener(view -> {
            navigate(Login_Activity.class);
            finish();
        });
    }

    void dangky_user() {
        //kiểm tra email and Phone
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+";
        String SDT = "^(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})$";
        //kiểm tra email and Phone
        String str_ho = binding.inputHo.getText().toString().trim();
        String str_ten = binding.inputTen.getText().toString().trim();
        String str_Email = binding.inputEmail.getText().toString().trim();
        String str_password = binding.inputPasword.getText().toString().trim();
        String str_re_password = binding.inputRePasword.getText().toString().trim();
        String strPhone = binding.inputPhone.getText().toString().trim();
        String strDiachi = binding.inputDiachi.getText().toString().trim();
        // validate so sanh chuoi
        if(TextUtils.isEmpty(str_ho)||(TextUtils.isEmpty(str_ten))| (TextUtils.isEmpty(str_Email))||
                (TextUtils.isEmpty(str_password))||(TextUtils.isEmpty(str_re_password))
                ||(TextUtils.isEmpty(strPhone))||(TextUtils.isEmpty(strDiachi))
        ){
            binding.Thongbaoloi.setText("Không được để trống.");
            binding.Thongbaoloi.setVisibility(View.VISIBLE);
        }else if(!str_Email.matches(emailPattern)){
            binding.Thongbaoloi.setText("Email không hợp lệ.");
            binding.Thongbaoloi.setVisibility(View.VISIBLE);
        }else if(!strPhone.matches(SDT)){
            binding.Thongbaoloi.setText("Số điện thoại không hợp lệ");
            binding.Thongbaoloi.setVisibility(View.VISIBLE);
        }else if(str_password.length()<6){
            binding.Thongbaoloi.setText("Mật khẩu tối thiểu phải có 6 ký tự.");
            binding.Thongbaoloi.setVisibility(View.VISIBLE);
        }else if(binding.checkbox.isChecked()==ischeck){
           binding.checkbox.setTextColor(Color.RED);
        }else if (str_password.equals(str_re_password)){
                compositeDisposable.add(api_polysmall.DANGKY(str_ho,str_ten,str_Email,strPhone,strDiachi,str_password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    if (userModel.isSuccess()){
                                            Utils.user_current.setEmail(str_Email);
                                            Utils.user_current.setPassword(str_password);
                                            navigate(Hoanthanhdangky_Activity.class);
                                            finish();
                                    }else {
                                        binding.Thongbaoloi.setText(userModel.getMessage());
                                        binding.Thongbaoloi.setVisibility(View.VISIBLE);
                                    }
                                }, throwable -> {
                                    binding.Thongbaoloi.setText(throwable.getMessage());
                                    binding.Thongbaoloi.setVisibility(View.VISIBLE);
                                }
                        ));
               }else {
                binding.Thongbaoloi.setText("Xác nhận mật khẩu mới không chính xác.");
                binding.Thongbaoloi.setVisibility(View.VISIBLE);
               }
    }
}