package com.example.polysmall.views;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.polysmall.R;
import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.models.GioHang;
import com.example.polysmall.controller.models.User;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.databinding.ActivityThanhToanBinding;
import com.example.polysmall.views.Muahangthanhcong.MuahangThanhcong_Activity;
import com.google.gson.Gson;
import java.text.DecimalFormat;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThanhToan_Activity extends Base_Activity {
    private ActivityThanhToanBinding binding;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    Api_Polysmall api_polysmall;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    long tongtien;
    int totalItem;
    boolean checkPay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThanhToanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Paper.init(this);
        API();
        Thanhtoan();
        countItem();
        ActionToolBar();
        trangthaiDangnhap();
        binding.edtDiachigiaohang.setFocusable(false);
        binding.edtHoten.setFocusable(false);
        binding.edtSodienthoai.setFocusable(false);
        binding.thaydoi.setOnClickListener(view1 -> {
            binding.edtDiachigiaohang.setFocusableInTouchMode(true);
            binding.edtHoten.setFocusableInTouchMode(true);
            binding.edtSodienthoai.setFocusableInTouchMode(true);
            binding.thaydoi.setVisibility(View.GONE);
            binding.luu.setVisibility(View.VISIBLE);
        });
        binding.luu.setOnClickListener(view1 -> {
            binding.edtDiachigiaohang.setFocusable(false);
            binding.edtHoten.setFocusable(false);
            binding.edtSodienthoai.setFocusable(false);
            binding.luu.setVisibility(View.GONE);
            binding.thaydoi.setVisibility(View.VISIBLE);
        });
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
    private void API() {
        api_polysmall = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_Polysmall.class);
    }
    void ActionToolBar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationIcon(R.drawable.arrow_back_24);
        binding.toolbar.setNavigationOnClickListener(view -> {
            navigate(Giohang_Activity.class);
            Utils.mangmuahang.clear();
            finish();
        });
    }

    void countItem() {
        totalItem = 0;
        for (int i = 0 ; i<Utils.mangmuahang.size();i++){
            totalItem =  totalItem+Utils.mangmuahang.get(i).getSoluong();
        }
    }

    @SuppressLint("SetTextI18n")
    void Thanhtoan() {
        String SDT = "^(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})$";
        tongtien = getIntent().getLongExtra("tongtien",0);
        binding.txttongtien.setText(decimalFormat.format(tongtien)+" ₫");
        binding.edtHoten.setText(Utils.user_current.getHo()+" "+ Utils.user_current.getTen());
        binding.edtSodienthoai.setText(Utils.user_current.getMobile());
        binding.edtDiachigiaohang.setText(Utils.user_current.getDiachi());
        binding.btnmuahangThanhtoan.setOnClickListener(view -> {
            String str_name = binding.edtHoten.getText().toString().trim();
            String str_phone = binding.edtSodienthoai.getText().toString().trim();
            String str_diachi = binding.edtDiachigiaohang.getText().toString().trim();
            if (TextUtils.isEmpty(str_name)||TextUtils.isEmpty(str_phone)||TextUtils.isEmpty(str_diachi)){
                Validatator(Gravity.CENTER);
            }else if(!str_phone.matches(SDT)){
                showMsg("Số điện thoại không hợp lệ");
            }else if (binding.checkbox1.isChecked()==checkPay) {
                showMsg("Vui lòng chọn phương thức thanh toán");
            }else {
                // post data
                int id  =Utils.user_current.getId();
                String ho = Utils.user_current.getHo();
                String name = Utils.user_current.getTen();
                String str_email = Utils.user_current.getEmail();
                Log.d("THANH TOAN",new Gson().toJson(Utils.mangmuahang));
                compositeDisposable.add(api_polysmall.POST_THANHTOAN(ho,name,str_email,str_phone,String.valueOf(tongtien),id,str_diachi,totalItem,new Gson().toJson(Utils.mangmuahang))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                messageModel -> {
                                    Utils.mangmuahang.clear();
                                    Paper.book().delete("cart");
                                    navigate(MuahangThanhcong_Activity.class);
                                    finish();
                                },
                                throwable -> {
                                    showMsg(throwable.getMessage());
                                }));
                        }
                });
            }

    void Validatator(int gravity) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogerror);
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
        // khai bao & anh xa
        ImageView dialog_imgCancel = dialog.findViewById(R.id.dialog_imgCancel);
        Button dialog_btnHuy = dialog.findViewById(R.id.dialog_btnHuy);
        Button dialog_btnUpdate = dialog.findViewById(R.id.dialog_btnUpdate);
        // Delay 1s
        dialog_imgCancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog_btnHuy.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog_btnUpdate.setOnClickListener(view -> {
           navigate(User_Activity.class);
           finish();
        });
        dialog.show();
    }
}
