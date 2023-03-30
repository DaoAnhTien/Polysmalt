package com.example.polysmall.views;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;

import com.example.polysmall.R;
import com.example.polysmall.controller.adapters.Adapter_khachhang;
import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.models.User;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.databinding.ActivityKhachhangBinding;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Khachhang_Activity extends Base_Activity implements SwipeRefreshLayout.OnRefreshListener{

    private ActivityKhachhangBinding binding;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Api_Polysmall api_polysmall;
    List<User> Array_user;
    Adapter_khachhang adapter;
    Dialog dialog;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKhachhangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        API();
        trangthaiDangnhap();
        dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        _refreshLayout();
    }
    void trangthaiDangnhap(){
        Paper.init(this);
        if (Paper.book().read("User.txt")!= null){
            User user = Paper.book().read("User.txt");
            Utils.user_current = user;
        }
    }
    private void API() {
        api_polysmall = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_Polysmall.class);
        Array_user = new ArrayList<>();
        GET_KHACHHANG();
        ActionToolBar();
    }

    void ActionToolBar() {
        setSupportActionBar(binding.Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.Toolbar.setNavigationIcon(R.drawable.arrow_back_24);
        binding.Toolbar.setNavigationOnClickListener(view -> {
            navigate(Thongtincanhan_Activity.class);
            finish();
        });
    }
    private void GET_KHACHHANG() {
        compositeDisposable.add(api_polysmall.GET_KHACHHANG()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel ->{
                            if (userModel.isSuccess()){
                                Array_user = userModel.getResult();
                                adapter = new Adapter_khachhang(getApplicationContext(),Array_user);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
                                binding.Recyclerview.setLayoutManager(layoutManager);
                                binding.Recyclerview.setAdapter(adapter);
                            }
                        },throwable ->{}
                ));
    }
    void _refreshLayout() {
        binding.RefeshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
      if (adapter==null){
          handler.postDelayed(() -> binding.RefeshLayout.setRefreshing(false),1500);
      }else {
          GET_KHACHHANG();
          adapter.notifyDataSetChanged();
          handler.postDelayed(() -> binding.RefeshLayout.setRefreshing(false),1500);
      }
    }
}