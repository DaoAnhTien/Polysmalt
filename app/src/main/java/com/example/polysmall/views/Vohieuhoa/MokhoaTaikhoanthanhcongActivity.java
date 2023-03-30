package com.example.polysmall.views.Vohieuhoa;

import android.os.Bundle;
import android.view.View;
import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.databinding.ActivityMokhoaTaikhoanthanhcongBinding;
import com.example.polysmall.views.Login_Activity;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MokhoaTaikhoanthanhcongActivity extends Base_Activity {
    private ActivityMokhoaTaikhoanthanhcongBinding binding;
    Api_Polysmall api_polysmall;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMokhoaTaikhoanthanhcongBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Paper.init(this);
        API();
        initView();
    }
    void API() {
        api_polysmall = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_Polysmall.class);
    }
    void initView(){
        binding.btnxacNhan.setOnClickListener(view -> {
            compositeDisposable.add(api_polysmall.HUYBOVOHIEUHOA(Utils.user_current.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                      userModel -> {
                       if (userModel.isSuccess()){
                           Paper.book().delete("User.txt");
                           Paper.book().delete("cart");
                        navigate(Login_Activity.class);
                        finish();
                       }else {
                            showMsg(userModel.getMessage());
                        }
                     }, throwable -> {
                          showMsg(throwable.getMessage());
                   }
            ));
        });
    }
    // check
    @Override
    protected void onResume() {
        super.onResume();
    }
}