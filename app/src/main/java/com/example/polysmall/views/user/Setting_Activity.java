package com.example.polysmall.views.user;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.polysmall.R;
import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.models.User;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.databinding.ActivitySettingBinding;
import com.example.polysmall.views.Login_Activity;
import com.example.polysmall.views.Thongtincanhan_Activity;
import com.example.polysmall.views.User_Activity;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Setting_Activity extends Base_Activity {
    private ActivitySettingBinding binding;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Api_Polysmall api_polysmall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        API();
        ActionToolBar();
        EvenClick();
        trangthaiDangnhap();
    }
    void trangthaiDangnhap(){
        Paper.init(this);
        if (Paper.book().read("User.txt")!= null){
            User user = Paper.book().read("User.txt");
            Utils.user_current = user;
        }
    }
    void API() {
        api_polysmall = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_Polysmall.class);
    }
    void ActionToolBar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationIcon(R.drawable.arrow_back_24);
        binding.toolbar.setNavigationOnClickListener(view -> {
            navigate(Thongtincanhan_Activity.class);
            finish();
        });
    }
    void EvenClick() {
        binding.btnChinhsuaThongtin.setOnClickListener(view -> {
            navigate(User_Activity.class);
            finish();
        });
        binding.btnDatlaimatkhau.setOnClickListener(view -> {
            navigate(Doipassword_Activity.class);
            finish();
        });
        binding.btnVohieuhoataikhoan.setOnClickListener(view -> {
            DialogVohieuhoa(Gravity.CENTER);
        });
    }

    void DialogVohieuhoa(int gravity) {
        boolean ischeck = false;
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogvohieuhoatow);
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
        Button dialog_btnHuy = dialog.findViewById(R.id.dialog_btnHuytow);
        Button dialog_btndongy = dialog.findViewById(R.id.dialog_btndongytow);
        ImageView dialog_imgCancel = dialog.findViewById(R.id.dialog_imgCanceltow);
        CheckBox dialogcheckbox = dialog.findViewById(R.id.dialogcheckboxtow);
        LinearLayout LinearLayoutCheck = dialog.findViewById(R.id.LinearLayoutChecktow);
        dialogcheckbox.setOnClickListener(view -> {
            if (dialogcheckbox.isChecked()==ischeck){
                LinearLayoutCheck.setVisibility(View.GONE);
            }else {
                LinearLayoutCheck.setVisibility(View.VISIBLE);
            }
        });

        dialog_btnHuy.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog_imgCancel.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog_btndongy.setOnClickListener(view -> {
            compositeDisposable.add(api_polysmall.VOHIEUHOA(Utils.user_current.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            userModel -> {
                                if (userModel.isSuccess()){
                                    showMsg("Tài khoản đã bị vô hiệu hóa tạm thời");
                                    Paper.book().delete("User.txt");
                                    Paper.book().delete("cart");
                                    navigate(Login_Activity.class);
                                    finish();
                                    dialog.dismiss();
                                }else {
                                    showMsg(userModel.getMessage());
                                }
                            }, throwable -> {
                                showMsg(throwable.getMessage());
                            }
                    ));
        });
        dialog.show();
    }
}