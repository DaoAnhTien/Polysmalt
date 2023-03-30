package com.example.polysmall.views.Muahangthanhcong;

import android.os.Bundle;
import android.view.View;

import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.models.GioHang;
import com.example.polysmall.controller.models.User;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.databinding.ActivityMuahangThanhcongBinding;
import com.example.polysmall.views.All_prduct_Activity;
import com.example.polysmall.views.LichsumuahangActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.paperdb.Paper;

public class MuahangThanhcong_Activity extends Base_Activity {
    private ActivityMuahangThanhcongBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMuahangThanhcongBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Paper.init(this);
        if (Paper.book().read("User.txt")!= null){
            User user = Paper.book().read("User.txt");
            Utils.user_current = user;
        }
        if (Utils.user_current.getStatus()==0){
            binding.btnDonmua.setText("Đơn hàng");
            binding.txtDonhangAndDonmua.setText("Bạn có thể tìm thấy trong đơn hàng");
            binding.btnDonmua.setOnClickListener(view1 -> {
                navigate(LichsumuahangActivity.class);
                finish();
            });
        }else {
            binding.btnDonmua.setText("Đơn mua");
            binding.btnDonmua.setOnClickListener(view1 -> {
                navigate(LichsumuahangActivity.class);
                finish();
            });
        }
        binding.btnSanpham.setOnClickListener(view1 -> {
            navigate(All_prduct_Activity.class);
            finish();
        });

        List<GioHang> gioHangs = Paper.book().read("cart");
        Utils.manggiohang = gioHangs;
        if(Utils.manggiohang != null){
            return;
        }else {
            return;
        }
    }
}