package com.example.polysmall.views;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.example.polysmall.MainActivity;
import com.example.polysmall.R;

import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.models.GioHang;
import com.example.polysmall.controller.models.Sanpham;
import com.example.polysmall.controller.models.User;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.databinding.ActivityChitietSanphamBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class Chitiet_sanpham_Activity extends Base_Activity{
    private ActivityChitietSanphamBinding binding;
    Sanpham sanpham = new Sanpham();
    Api_Polysmall api_polysmall;
    Dialog dialog;
    int id_category;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    GioHang gioHang = new GioHang();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChitietSanphamBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        API();
        itemview();
        initData();
        initControl();
        Checksoluong();
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
        }
    }

    void API() {
        api_polysmall = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_Polysmall.class);
        id_category = getIntent().getIntExtra("id_category",1);

    }
    void itemview() {
        /// chuyen layout khi click
        binding.FrameLayoutGiohang.setOnClickListener(view -> {
            navigate(Giohang_Activity.class);
            finish(); //  ket thuc khi click vaof framelayoutgiohang
        });
        binding.ImgBtnQuaylai.setOnClickListener(view -> {
            navigate(MainActivity.class);
            finish();
        });
    }
    // viết lại bằng API
    void themGioHang() {
        int soluong = Integer.parseInt(binding.spinner.getSelectedItem().toString());
        if (Utils.manggiohang.size()>0){
            boolean flag  = false;
            for (int i = 0 ; i< Utils.manggiohang.size(); i++){
                if (Utils.manggiohang.get(i).getIdsp() == sanpham.getId()){
                    Utils.manggiohang.get(i).setSoluong(soluong + Utils.manggiohang.get(i).getSoluong());
                    Integer dongia = Integer.parseInt(sanpham.getPrice_product());
                    Utils.manggiohang.get(i).setGiasp(Long.parseLong(sanpham.getPrice_product()));
                    Utils.manggiohang.get(i).setDongia(dongia);
                    Paper.book().write("cart",Utils.manggiohang);
                    flag = true;
                }
            }
            if (flag == false){
                Integer dongia = Integer.parseInt(sanpham.getPrice_product());
                gioHang.setGiasp(Long.parseLong(sanpham.getPrice_product()));
                gioHang.setSoluong(soluong);
                gioHang.setIdsp(sanpham.getId());
                gioHang.setTensp(sanpham.getName_product());
                gioHang.setHinhsp(sanpham.getImageview_product());
                gioHang.setDongia(dongia);
                Utils.manggiohang.add(gioHang);
                Paper.book().write("cart",Utils.manggiohang);
            }

        }else {
            Integer dongia = Integer.parseInt(sanpham.getPrice_product());
            gioHang.setGiasp(Long.parseLong(sanpham.getPrice_product()));
            gioHang.setDongia(dongia);
            gioHang.setSoluong(soluong);
            gioHang.setIdsp(sanpham.getId());
            gioHang.setTensp(sanpham.getName_product());
            gioHang.setHinhsp(sanpham.getImageview_product());
            Utils.manggiohang.add(gioHang);
            Paper.book().write("cart",Utils.manggiohang);
        }
       Checksoluong();
    }

    void Checksoluong() {
        if (Utils.manggiohang == null){
            Utils.manggiohang = new ArrayList<>();
        }else {
            int totalItem = 0;
            for (int i = 0 ; i<Utils.manggiohang.size();i++){
                totalItem =  totalItem+Utils.manggiohang.get(i).getSoluong();
            }
            if(totalItem>0){
                binding.menusl.setText(String.valueOf(totalItem));
                binding.menusl.setVisibility(View.VISIBLE);
            }
        }
    }
    // viết lại bằng API

    void initData() {
        sanpham = (Sanpham) getIntent().getSerializableExtra("chitiet");
        binding.txttensp.setText(sanpham.getName_product());
        binding.titleName.setText(sanpham.getName_product());
        binding.txtmota.setText(sanpham.getDescribe_product());
        if (sanpham.getImageview_product().contains("http")){
            Glide.with(getApplicationContext()).load(sanpham.getImageview_product()).into(binding.imgChitiet);
        }else {
            String hinh = Utils.BASE_URL+"images/"+sanpham.getImageview_product();
            Glide.with(getApplicationContext()).load(hinh).into(binding.imgChitiet);
        }
        // arr số lượng sản phâm  mảng từ 1 -> 10 và ngược lại
        binding.txtgiasp.setText("Giá: "+decimalFormat.format(Double.parseDouble(sanpham.getPrice_product())));
        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterspnier = new ArrayAdapter<>(this, io.paperdb.R.layout.support_simple_spinner_dropdown_item,so);
        binding.spinner.setAdapter(adapterspnier);

    }

    void initControl() {
        binding.btnthemvaogiohang.setOnClickListener(view -> {
            themGioHang();
            DIALOG_ADDTHANHCONG(Gravity.CENTER);
            binding.menusl.setVisibility(View.VISIBLE);
        });
    }

    void DIALOG_ADDTHANHCONG(int gravity) {
        dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogthemvaogiohang);
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
        },2000);
        dialog.show();
    }
}
