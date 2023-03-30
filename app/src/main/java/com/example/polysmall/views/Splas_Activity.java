package com.example.polysmall.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.polysmall.MainActivity;
import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.databinding.ActivitySplasBinding;
import io.paperdb.Paper;

public class Splas_Activity extends Base_Activity {
    private ActivitySplasBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplasBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Paper.init(this);
        Thread thread= new Thread(){
            public void run(){
                try {
                    sleep(3000);
                }catch (Exception ex){
                    Log.d("TAGLogin","Lỗi");
                }finally {
//                     nếu đăng xuất user thì khi khởi chạy app sẻ chạy màn hình đăng nhập
                    if (Paper.book().read("User.txt")==null){
                        navigate(Login_Activity.class);
                    }else {
                        navigate(MainActivity.class);
                    }
                    finish();
                }
            }
        };
        thread.start();
    }
}