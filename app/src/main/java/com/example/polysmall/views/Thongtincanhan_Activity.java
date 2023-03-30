package com.example.polysmall.views;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.polysmall.MainActivity;
import com.example.polysmall.R;
import com.example.polysmall.controller.adapters.base.Base_Activity;

import com.example.polysmall.controller.models.Evenrbus.UserEvenrbus;
import com.example.polysmall.controller.models.GioHang;
import com.example.polysmall.controller.models.User;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.Utils;


import com.example.polysmall.databinding.ActivityThongtincanhanBinding;
import com.example.polysmall.views.CRUD_Product.Hiensanpham_Activity;

import com.example.polysmall.views.Doanhthu.Doanhthu_Activity;
import com.example.polysmall.views.user.Setting_Activity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class Thongtincanhan_Activity extends Base_Activity {
    private ActivityThongtincanhanBinding binding;
    Api_Polysmall api_polysmall;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Dialog dialog;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThongtincanhanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        API();
        quanlyuser();
        VALIDATE_USER();
        dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        phanQuyen();
        PHANQUYENNAVI();
        NangcapVip();
        Giohang();
        trangthaiDangnhap();
        Paper.init(this);
    }

    void trangthaiDangnhap(){
        if (Paper.book().read("User.txt")!= null){
            User user = Paper.book().read("User.txt");
            Utils.user_current = user;
        }
        if (Paper.book().read("cart")!=null){
            List<GioHang> list =Paper.book().read("cart");
            Utils.manggiohang = list;
            Giohang();
        }
    }
    void phanQuyen(){
        int maquyen = 1;
        if (Utils.user_current.getStatus()==maquyen){
            return;
        }else {
            binding.quanlyadmin.setVisibility(View.VISIBLE);
        }
    }
    @SuppressLint("SetTextI18n")
    void quanlyuser() {
        if (Utils.user_current.getVIP()==0){
            binding.vip.setText(R.string.damua);
            binding.vip.setTextColor(Color.WHITE);
            binding.vip.setBackgroundResource(R.color.black);
        }else{
            binding.vip.setText(R.string.chuamua);
            binding.vip.setTextColor(Color.BLACK);
            binding.vip.setBackgroundResource(R.color.white);
        }
        binding.txtEmail.setText(Utils.user_current.getEmail()+"");
        binding.btnDangxuat.setOnClickListener(view -> {
                openFeedbackDialog_dangxuat(Gravity.BOTTOM);
        });
        binding.LinearLayoutKhachhang.setOnClickListener(view -> {
            navigate(Khachhang_Activity.class);
            finish();
        });
        binding.LinearLayoutSanphammoi.setOnClickListener(view -> {
            navigate(Hiensanpham_Activity.class);
            finish();
        });
        binding.linearlayoutDoanhthu.setOnClickListener(view -> {
            navigate(Doanhthu_Activity.class);
            finish();
        });
        binding.LinearLayoutDialogDichvu.setOnClickListener(view -> {
            DIALOG_DIEUKHOANDICH(Gravity.CENTER);
            return;
        });
        binding.LinearLayoutDialogChinhsach.setOnClickListener(view -> {
            DIALOG_CHINHSACH(Gravity.CENTER);
            return;
        });
        binding.LinearLayoutdialogLienhe.setOnClickListener(view -> {
            DIALOG_LIENHE(Gravity.CENTER);
        });
        binding.LinearLayoutDialogCauhoi.setOnClickListener(view -> {
            DIALOGCAUHOITHUONGGAP(Gravity.CENTER);
        });
        binding.Setting.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Setting_Activity.class);
            intent.putExtra("Cập nhật",user);
            startActivity(intent);
            finish();
        });
    }

    void DIALOGCAUHOITHUONGGAP(int gravity) {
        dialog.setContentView(R.layout.dialogcauhoithuonggap);
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
            dialog.setCancelable(false);
        }
        // xu ly code
        // khai bao & anh xa
        ImageView dialog_cauhoithuonggap_thoat = dialog.findViewById(R.id.dialog_cauhoithuonggap_thoat);
        dialog_cauhoithuonggap_thoat.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    void DIALOG_LIENHE(int gravity) {
        dialog.setContentView(R.layout.dialog_lienhe);
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
            dialog.setCancelable(false);
        }
        // xu ly code
        // khai bao & anh xa
        ImageView dialog_lienhe_thoat = dialog.findViewById(R.id.dialog_lienhe_thoat);
        dialog_lienhe_thoat.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    void DIALOG_CHINHSACH(int gravity) {
        dialog.setContentView(R.layout.dialog_chinhsach);
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
            dialog.setCancelable(false);
        }
        // xu ly code
        // khai bao & anh xa
        ImageView dialog_chinhsach_thoat = dialog.findViewById(R.id.dialog_chinhsach_thoat);
        dialog_chinhsach_thoat.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    void DIALOG_DIEUKHOANDICH(int gravity) {
        dialog.setContentView(R.layout.dialog_dieukhoan);
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
            dialog.setCancelable(false);
        }
        // xu ly code
        // khai bao & anh xa
        ImageView dialog_dieukhoan_thoat = dialog.findViewById(R.id.dialog_dieukhoan_thoat);
        dialog_dieukhoan_thoat.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    private void openFeedbackDialog_dangxuat(int gravity){
        dialog.setContentView(R.layout.dialog_dangxuat);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttriabutes = window.getAttributes();
        windowAttriabutes.gravity = gravity;
        window.setAttributes(windowAttriabutes);

        if (Gravity.BOTTOM == gravity){
            dialog.setCancelable(false);
        }
        // xu ly code
        // khai bao & anh xa
        TextView btn_dangxuat = dialog.findViewById(R.id.btn_dangxuat);
        TextView btn_huy_dangxuat = dialog.findViewById(R.id.btn_huy_dangxuat);
        btn_dangxuat.setOnClickListener(view -> {
            // xóa key user và giỏ hàng
            Paper.book().delete("User.txt");
            Paper.book().delete("cart");
            navigate(Login_Activity.class);
            finish();
        });
        btn_huy_dangxuat.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    private void API() {
        api_polysmall = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_Polysmall.class);
    }

    void VALIDATE_USER() {
        String imageview = null;
        if (Utils.user_current.getImage_user() == imageview){
            binding.imageview.setImageResource(R.drawable.user);
        }else {
            if (Utils.user_current.getImage_user().contains("http")){
                Glide.with(getApplicationContext()).load(Utils.user_current.getImage_user()).into(binding.imageview);
            }else {
                String hinh = Utils.BASE_URL+"images/"+Utils.user_current.getImage_user();
                Glide.with(getApplicationContext()).load(hinh).into(binding.imageview);
            }
        }
        binding.name.setText(Utils.user_current.getHo()+" " + Utils.user_current.getTen());
    }
    void PHANQUYENNAVI() {
        if(Utils.user_current.getStatus()==1){
            binding.navigationAdmin.setVisibility(View.GONE);
            NavigatorBottomUser();
        }else {
            binding.navigationUser.setVisibility(View.GONE);
            NavigatorBottomAdmin();
        }
    }

    void NavigatorBottomUser(){
        binding.navigationUser.setItemSelected(R.id.nguoidung_activity,true);
        binding.navigationUser.setOnItemSelectedListener(i -> {
            switch (i){
                case R.id.Home_activity:{
                    navigate(MainActivity.class);
                    overridePendingTransition(0,0);
                    finish();
                    break;
                }
                case R.id.All_product:{
                    navigate(All_prduct_Activity.class);
                    overridePendingTransition(0,0);
                    finish();
                    break;
                }
                case R.id.M_Lichsumua:{
                    navigate(LichsumuahangActivity.class);
                    overridePendingTransition(0,0);
                    finish();
                    break;
                }

            }
        });
    }

    void NavigatorBottomAdmin(){
        binding.navigationAdmin.setItemSelected(R.id.nguoidung_activity,true);
        binding.navigationAdmin.setOnItemSelectedListener(i -> {
            switch (i){
                case R.id.Home_activity:{
                    navigate(MainActivity.class);
                    overridePendingTransition(0,0);
                    finish();
                    break;
                }
                case R.id.All_product:{
                    navigate(All_prduct_Activity.class);
                    overridePendingTransition(0,0);
                    finish();
                    break;
                }
                case R.id.DonhangAdmin:{
                    navigate(LichsumuahangActivity.class);
                    overridePendingTransition(0,0);
                    finish();
                    break;
                }

            }
        });
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void evenUser(UserEvenrbus event) {
        if (event != null) {
            Utils.user_current = event.getUser();
        }
    }

    void NangcapVip(){
        if (Utils.user_current.getVIP()==1){
                binding.vip.setOnClickListener(view -> {
                    Dialogmuaquangcao(Gravity.CENTER);
                });
        }
    }
    @SuppressLint("SetTextI18n")
    void Dialogmuaquangcao(int gravity) {
        dialog.setContentView(R.layout.dialognangcap);
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
            dialog.setCancelable(false);
        }
        // xu ly code
        // khai bao & anh xa
        Button dialognangcapMuangay = dialog.findViewById(R.id.dialognangcapMuangay);
        Button dialognangcapBtnHuy = dialog.findViewById(R.id.dialognangcapBtnHuy);
        dialognangcapMuangay.setOnClickListener(view -> {
            MUAQUANGCAO();
        });
        dialognangcapBtnHuy.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    void MUAQUANGCAO() {
            compositeDisposable.add(api_polysmall.MUAQUANGCAO(Utils.user_current.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            userModel -> {
                                if (userModel.isSuccess()){
                                    trangthaiDangnhap();
                                    dialog.dismiss();
                                }else {
                                    showMsg(userModel.getMessage());
                                }
                       }, throwable -> {
                                showMsg(throwable.getMessage());
                   }
            ));
    }

    void Giohang() {
        if (Utils.manggiohang == null){
            Utils.manggiohang = new ArrayList<>();
        }else {
            int totalItem = 0;
            for (int i = 0 ; i<Utils.manggiohang.size();i++){
                totalItem =  totalItem+Utils.manggiohang.get(i).getSoluong();
            }
            if (totalItem>0){
                binding.menusl.setVisibility(View.VISIBLE);
                binding.menusl.setText(String.valueOf(totalItem));
            }

        }
        binding.FrameLayoutGiohang.setOnClickListener(view -> {
            navigate(Giohang_Activity.class);
            finish();
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}