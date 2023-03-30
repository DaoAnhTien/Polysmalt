package com.example.polysmall.views.user;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.polysmall.R;
import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.models.User;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.databinding.ActivityDoipasswordBinding;
import com.example.polysmall.views.Thongtincanhan_Activity;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Doipassword_Activity extends Base_Activity {
    private ActivityDoipasswordBinding binding;
    Api_Polysmall api_polysmall;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    CheckBox chkRememberPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoipasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ActionToolBar();
        API();
        trangthaiDangnhap();
        initview();
        chkRememberPass = findViewById(R.id.chkRememberPasstow);
        Luutaikhoan();
    }

    void initview() {
            binding.edPassOld.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.txtNotificationCheck.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        binding.edPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.txtNotificationCheck.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        binding.edRepass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.txtNotificationCheck.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    void trangthaiDangnhap(){
        Paper.init(this);
        if (Paper.book().read("User.txt")!= null){
            User user = Paper.book().read("User.txt");
            Utils.user_current = user;
        }
    }
    void ActionToolBar() {
        setSupportActionBar(binding.Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.Toolbar.setNavigationIcon(R.drawable.arrow_back_24);
        binding.Toolbar.setNavigationOnClickListener(view -> {
            navigate(Setting_Activity.class);
            finish();
        });
    }
    private void API() {
        api_polysmall = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_Polysmall.class);
         binding.btnthaydoimatkhau.setOnClickListener(view -> {
             Thaydoimatkhau();
         });
    }

    void Thaydoimatkhau(){
        String str_password = binding.edPass.getText().toString().trim();
        if(validate()>0){
            compositeDisposable.add(api_polysmall.DOIMATKHAU(str_password,Utils.user_current.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            userModel -> {
                                if (userModel.isSuccess()){
                                    DIALOG(Gravity.CENTER);
                                    rememberUser(str_password,chkRememberPass.isChecked());
                                }else {
                                    binding.txtNotificationCheck.setText(userModel.getMessage());
                                    binding.txtNotificationCheck.setVisibility(View.VISIBLE);
                                }
                            }, throwable -> {
                                binding.txtNotificationCheck.setText(throwable.getMessage());
                                binding.txtNotificationCheck.setVisibility(View.VISIBLE);
                            }
                    ));
        }

    }
    void Luutaikhoan() {
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        chkRememberPass.setChecked(pref.getBoolean("REMEMBER",true));
    }
    public int validate(){
        // khai báo biến
        int check=1;
        String pass= binding.edPass.getText().toString();
        String rePass= binding.edRepass.getText().toString();
        if(binding.edPassOld.getText().length()==0 || binding.edPass.getText().length()==0 || binding.edRepass.getText().length()==0){
            binding.txtNotificationCheck.setText("Không được để trống.");
            binding.txtNotificationCheck.setVisibility(View.VISIBLE);
            check=-1;
        }else if (pass.length()<6){
            binding.txtNotificationCheck.setText("Mật khẩu tối thiểu phải có 6 ký tự.");
            binding.txtNotificationCheck.setVisibility(View.VISIBLE);
            check=-1;
        }else{
            SharedPreferences pref = getApplicationContext().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String passOld= pref.getString("PASSWORD","");
            if (!passOld.equals(binding.edPassOld.getText().toString())){
                binding.txtNotificationCheck.setText("Mật khẩu củ sai.");
                binding.txtNotificationCheck.setVisibility(View.VISIBLE);
                check=-1;
            }
            if (!pass.equals(rePass)){
                binding.txtNotificationCheck.setText("Xác nhận mật khẩu mới không chính xác.");
                binding.txtNotificationCheck.setVisibility(View.VISIBLE);
                check=-1;
            }
        }
        return check;
    }
    void DIALOG(int gravity) {
        Dialog dialog;
        dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogthemvaogiohang);
        TextView dialog_text = dialog.findViewById(R.id.dialog_text);
        dialog_text.setText("Thay đổi mật khẩu thành công.");
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttriabutes = window.getAttributes();
        windowAttriabutes.gravity = gravity;
        window.setAttributes(windowAttriabutes);

        if (Gravity.CENTER == gravity){
            dialog.setCancelable(true);
        }
        // Delay 1s
        new Handler().postDelayed(() -> {
            dialog.dismiss();
            navigate(Thongtincanhan_Activity.class);
            finish();
        },1500);
        dialog.show();
    }
    public void rememberUser(String p,boolean status){
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        if (!status){
            //Xoa tinh trang truoc do
            editor.clear();
        }else {
            // luu du lieu
            editor.putString("PASSWORD",p);
            editor.putBoolean("REMEMBER",status);
        }
        // luu lai toan bo
        editor.commit();
    }
}