package com.example.polysmall.controller.adapters.base;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.polysmall.controller.adapters.Adapter_Danhmuc;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Base_Activity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Api_Polysmall api_polysmall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api_polysmall = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_Polysmall.class);
    }
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    public <T> void navigate(Class<T> name) {
        startActivity(new Intent(this, name));
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

}
