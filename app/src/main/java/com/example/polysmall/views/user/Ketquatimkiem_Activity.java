package com.example.polysmall.views.user;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.polysmall.R;
import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.databinding.ActivityKetquatimkiemBinding;
import com.example.polysmall.views.Login_Activity;
import com.example.polysmall.views.Quenmk_Activity;
import com.example.polysmall.views.otp.PhoneActivity;


public class Ketquatimkiem_Activity extends Base_Activity {
    private ActivityKetquatimkiemBinding binding;
    Api_Polysmall api_polysmall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKetquatimkiemBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        API();
        VALIDATE_USER();
        Evenclick();
    }


    void Evenclick() {
        binding.ketquatimkiemdangnhap.setOnClickListener(view -> {
            navigate(Login_Activity.class);
            finish();
        });
        binding.ketquatimkiemquenmatkhau.setOnClickListener(view -> {
            navigate(Quenmk_Activity.class);
            finish();
        });
    }

    private void API() {
        api_polysmall = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_Polysmall.class);
    }

    void DIALOGSENDOTP(int gravity) {
        Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_send_otp);
        CardView cardviewEmail = dialog.findViewById(R.id.cardviewEmail);
        CardView cardviewOTP = dialog.findViewById(R.id.cardviewOTP);
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
        cardviewEmail.setOnClickListener(view -> {
            navigate(Quenmk_Activity.class);
            finish();
        });
        cardviewOTP.setOnClickListener(view -> {
            navigate(PhoneActivity.class);
            finish();
        });

        dialog.show();
    }
    void VALIDATE_USER() {
        String imageview = null;
        if (Utils.user_current.getImage_user() == imageview){
            binding.ketquatimkiemhinhanh.setImageResource(R.drawable.user);
        }else {
            if (Utils.user_current.getImage_user().contains("http")){
                Glide.with(getApplicationContext()).load(Utils.user_current.getImage_user()).into(binding.ketquatimkiemhinhanh);
            }else {
                String hinh = Utils.BASE_URL+"images/"+Utils.user_current.getImage_user();
                Glide.with(getApplicationContext()).load(hinh).into(binding.ketquatimkiemhinhanh);
            }
        }
        binding.ketquaHoten.setText("Họ và tên: "+Utils.user_current.getHo()+" " + Utils.user_current.getTen());
        binding.ketquaSodienthoai.setText("Số diện thoại: "+Utils.user_current.getMobile()+"");
        binding.ketquaEmail.setText("Email  : "+Utils.user_current.getEmail());
        return;
    }
}