package com.example.polysmall.views;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.example.polysmall.MainActivity;
import com.example.polysmall.R;
import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.models.GioHang;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.databinding.ActivityLoginBinding;
import com.example.polysmall.views.user.Truyvantaikhoan_Activity;

import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Login_Activity extends Base_Activity {
    private ActivityLoginBinding binding;
    Api_Polysmall api_polysmall;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    CheckBox chkRememberPass;
    boolean isLogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        API();
        initView();
        OnchageText();
        List<GioHang> gioHangs = Paper.book().read("cart");
        Utils.manggiohang = gioHangs;
        if(Utils.manggiohang != null){
            return;
        }else {
            return;
        }
    }

    void OnchageText() {
        binding.edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.txtValidate.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        binding.edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            binding.txtValidate.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    void API() {
        api_polysmall = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_Polysmall.class);
    }
    void initView() {
        chkRememberPass = findViewById(R.id.chkRememberPass);
        Luutaikhoan();
        Paper.init(this);
        if(Paper.book().read("email")!=null && Paper.book().read("password")!=null){
            binding.edtEmail.setText(Paper.book().read("email"));
            binding.edtPassword.setText(Paper.book().read("password"));
            if (Paper.book().read("isLogin")!=null){
                boolean flag = Paper.book().read("isLogin");
                if (flag){
                    new Handler().postDelayed(()->{
                    },5);
                }
            }
        }
        binding.txtDangky.setOnClickListener(view -> {
            navigate(Register_Activity.class);
            finish();
        });
        binding.txtTruyvan.setOnClickListener(view -> {
            navigate(Truyvantaikhoan_Activity.class);
            finish();
        });
        binding.btnDangnhap.setOnClickListener(view -> {
            login();
        });
        binding.imgFacebook.setOnClickListener(view -> {
            loginFacebook();
        });
        binding.imgGoogle.setOnClickListener(view -> {
            loginGoogle();
        });

    }

    void loginGoogle() {
        Log.d("LOGIN_GOOGLE","True");
    }

    void loginFacebook() {
        Log.d("LOGIN_FACEBOOK","False");
    }

    void login(){
        String str_email = binding.edtEmail.getText().toString().trim();
        String str_password = binding.edtPassword.getText().toString().trim();
        if(TextUtils.isEmpty(str_email)||(TextUtils.isEmpty(str_password))){
            binding.txtValidate.setText("Không được để trống.");
            binding.txtValidate.setVisibility(View.VISIBLE);
            return;
        }else {
            // login
            compositeDisposable.add(api_polysmall.DANGNHAP(str_email,str_password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            userModel -> {
                                if (userModel.isSuccess()){
                                    isLogin = true;
                                    Paper.book().write("isLogin",isLogin);
                                    // Lưu lại toàn bộ
                                    Paper.book().write("User.txt",userModel.getResult().get(0));
                                    // Lưu lại toàn bộ and tự động đăng nhập
                                    Paper.book().delete("cart");
                                    rememberUser(str_email,str_password,chkRememberPass.isChecked());
                                    Utils.user_current = userModel.getResult().get(0);
                                    navigate(MainActivity.class);
                                    finish();
                                }else {
                                    binding.txtValidate.setText("Thông tin tài khoản không khớp.");
                                    binding.txtValidate.setVisibility(View.VISIBLE);
                                    return;
                                }
                            }, throwable -> {
                                showMsg(throwable.getMessage());
                   }
            ));
        }
    }
    // check
    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.user_current.getEmail() != null && Utils.user_current.getPassword() != null ){
            binding.edtEmail.setText("");
            binding.edtPassword.setText("");
        }
    }
    void Luutaikhoan() {
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        chkRememberPass.setChecked(pref.getBoolean("REMEMBER",true));
    }
    public void rememberUser(String u,String p,boolean status){
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        if (!status){
            //Xoa tinh trang truoc do
            editor.clear();
        }else {
            // luu du lieu
            editor.putString("USERNAME",u);
            editor.putString("PASSWORD",p);
            editor.putBoolean("REMEMBER",status);
        }
        // luu lai toan bo
        editor.commit();
    }

}