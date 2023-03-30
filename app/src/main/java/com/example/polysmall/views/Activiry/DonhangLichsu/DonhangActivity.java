package com.example.polysmall.views.Activiry.DonhangLichsu;


import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import com.example.polysmall.MainActivity;
import com.example.polysmall.R;
import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.models.GioHang;
import com.example.polysmall.controller.models.User;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.databinding.ActivityDonhangBinding;
import com.example.polysmall.views.All_prduct_Activity;
import com.example.polysmall.views.Giohang_Activity;
import com.example.polysmall.views.Thongtincanhan_Activity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class DonhangActivity extends Base_Activity {
    private ActivityDonhangBinding binding;
    Api_Polysmall api_polysmall;
//    CompositeDisposable compositeDisposable = new CompositeDisposable();
//    Adapter_Lichsu_donhang adapter;
    int totalItem = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonhangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
//        TabDonhang tabDonhang = new TabDonhang(this,getSupportFragmentManager());
        ViewPager viewPager = binding.ViewPagerDonhang;
//        viewPager.setAdapter(tabDonhang);
        TabLayout tabs = binding.tabLayoutDonhang;
        tabs.setupWithViewPager(viewPager);
        setabLayout();
        Giohang();
        PHANQUYENNAVI();
        phanQuyen();
        trangthaiDangnhap();
        initview();
    }

    void trangthaiDangnhap(){
        Paper.init(this);
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

    void initview() {
        api_polysmall = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_Polysmall.class);
    }

    void phanQuyen(){
        if (Utils.user_current.getStatus()== 1){
            return;
        }else {
            binding.title.setText("Đơn hàng");
            binding.FrameLayoutGiohang.setVisibility(View.GONE);
        }
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
        binding.navigationUser.setItemSelected(R.id.M_Lichsumua,true);
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
                case R.id.nguoidung_activity:{
                    navigate(Thongtincanhan_Activity.class);
                    overridePendingTransition(0,0);
                    finish();
                    break;
                }
            }
        });
    }

    void NavigatorBottomAdmin(){
        binding.navigationAdmin.setItemSelected(R.id.DonhangAdmin,true);
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
                case R.id.nguoidung_activity:{
                    navigate(Thongtincanhan_Activity.class);
                    overridePendingTransition(0,0);
                    finish();
                    break;
                }

            }
        });
    }


    void Giohang() {
        if (Utils.manggiohang == null){
            Utils.manggiohang = new ArrayList<>();
        }else {
            int totalItem = 0;
            for (int i = 0 ; i<Utils.manggiohang.size();i++){
                totalItem =  totalItem+Utils.manggiohang.get(i).getSoluong();
            }
            binding.menusl.setText(String.valueOf(totalItem));
        }
        binding.FrameLayoutGiohang.setOnClickListener(view -> {
            navigate(Giohang_Activity.class);
            finish();
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        // set tổng giá trị đơn hàng của giỏ h
        for (int i = 0; i< Utils.manggiohang.size(); i++){
            totalItem =  totalItem+Utils.manggiohang.get(i).getSoluong();
        }
        if (totalItem>0){
            binding.menusl.setVisibility(View.VISIBLE);
            binding.menusl.setText(String.valueOf(totalItem));
        }
    }


    private void setabLayout() {
        View root = binding.tabLayoutDonhang.getChildAt(0);
        if (root instanceof LinearLayout){
            ((LinearLayout)root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(R.drawable.tabdonhang);
            ((LinearLayout) root).setDividerPadding(5);
            ((LinearLayout) root).setDividerDrawable(drawable);

        }
    }
}