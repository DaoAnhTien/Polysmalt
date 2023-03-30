package com.example.polysmall.views;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.example.polysmall.MainActivity;
import com.example.polysmall.R;
import com.example.polysmall.controller.adapters.AdapterGiohang;
import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.models.Evenrbus.TinhTongEvent;
import com.example.polysmall.controller.models.GioHang;
import com.example.polysmall.controller.models.User;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.databinding.ActivityGiohangBinding;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

import io.paperdb.Paper;

public class Giohang_Activity extends Base_Activity implements SwipeRefreshLayout.OnRefreshListener{
    private ActivityGiohangBinding binding;
    AdapterGiohang adapter;
    long tongtiensanpham;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGiohangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initControler();
        Tinhtongtien();
        check_button();
        ActionToolBar();
        trangthaiDangnhap();
        Paper.init(this);
        _refreshLayout();
        binding.btnMuangay.setOnClickListener(view1 -> {
            navigate(All_prduct_Activity.class);
            finish();
        });
    }
    void trangthaiDangnhap(){
        if (Paper.book().read("User.txt")!= null){
            User user = Paper.book().read("User.txt");
            Utils.user_current = user;
        }
    }
    @SuppressLint("SetTextI18n")
    void Tinhtongtien() {
        tongtiensanpham = 0;
        for (int i = 0;i< Utils.mangmuahang.size();i++){
            tongtiensanpham = tongtiensanpham + (Utils.mangmuahang.get(i).getGiasp()*Utils.mangmuahang.get(i).getSoluong());
        }
        binding.txttongtien.setText(decimalFormat.format(tongtiensanpham)+" ₫");
    }
    @SuppressLint("ResourceAsColor")
    int check_button(){
        int i = 0;
        binding.btnmuahang.setOnClickListener(view -> {
            if (tongtiensanpham == i){
                NOTIFICATION(Gravity.CENTER);
            }else {
                    Intent intent = new Intent(getApplicationContext(),ThanhToan_Activity.class);
                    intent.putExtra("tongtien",tongtiensanpham);
                    Utils.manggiohang.clear();
                    startActivity(intent);
                    finish();
            }
        });
        return i;
    }

    void NOTIFICATION(int gravity) {
        Dialog dialog = new Dialog(this);
        Window window = dialog.getWindow();
        dialog.setContentView(R.layout.dialog_muahang);
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
        // xu ly code
        // khai bao & anh xa

        TextView dialogName = dialog.findViewById(R.id.dialogName);
        dialogName.setText("Bạn vẫn chưa chọn sản phẩm nào \n để thanh toán");
        dialogName.setTextColor(Color.BLACK);
        new Handler().postDelayed(() -> {
            dialog.dismiss();
        },2000);
        dialog.show();
    }

    void ActionToolBar() {
        setSupportActionBar(binding.Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.Toolbar.setNavigationIcon(R.drawable.arrow_back_24);
        binding.Toolbar.setNavigationOnClickListener(view -> {
            Utils.mangmuahang.clear();
            navigate(MainActivity.class);
            finish();
        });
    }
    void initControler() {
            List<GioHang> gioHangs = Paper.book().read("cart");
            Utils.manggiohang = gioHangs;
            if(Utils.manggiohang != null){
                binding.RecyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                binding.RecyclerView.setLayoutManager(layoutManager);
                adapter = new AdapterGiohang(getApplicationContext(),Utils.manggiohang);
                binding.RecyclerView.setAdapter(adapter);
            }else {
                binding.LinearLayoutGiohang.setVisibility(View.VISIBLE);
                binding.LinearLayoutMuahang.setVisibility(View.GONE);
            }
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

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventTinhtien(TinhTongEvent event){
        if (event != null){
            Tinhtongtien();
        }
    }
    void _refreshLayout() {
        binding.RefeshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        if (adapter==null){
            handler.postDelayed(() -> binding.RefeshLayout.setRefreshing(false),1500);
        }else {
            initControler();
            Paper.book().delete("cart");
            adapter.notifyDataSetChanged();
            handler.postDelayed(() -> binding.RefeshLayout.setRefreshing(false),1500);
        }
    }
}