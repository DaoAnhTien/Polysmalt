package com.example.polysmall.views.CRUD_Product;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.polysmall.R;
import com.example.polysmall.controller.adapters.sanpham.Adapter_quanly_sanpham;
import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.models.Evenrbus.SuaXoaEvent;
import com.example.polysmall.controller.models.Sanpham;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.Utils;

import com.example.polysmall.databinding.ActivityHiensanphamBinding;
import com.example.polysmall.views.Thongtincanhan_Activity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Hiensanpham_Activity extends Base_Activity implements SwipeRefreshLayout.OnRefreshListener {
    private ActivityHiensanphamBinding binding;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Api_Polysmall api_polysmall;
    List<Sanpham> Array_sanpham;
    Adapter_quanly_sanpham adapter;
    Sanpham sanpham;
    Dialog dialog;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHiensanphamBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initview();
        API();
        ActionToolBar();
        GET_QUANLYSANPHAM();
        dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        _refreshLayout();
    }

    private void API() {
        api_polysmall = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_Polysmall.class);
        Array_sanpham = new ArrayList<>();
    }

    void initview() {
        binding.btnThemsanpham.setOnClickListener(view -> {
            navigate(Them_sua_Activity.class);
            finish();
        });
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

    void GET_QUANLYSANPHAM() {
        compositeDisposable.add(api_polysmall.GET_QUANLYSANPHAM()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanphamModel ->{
                            if (sanphamModel.isSuccess()){
                                Array_sanpham = sanphamModel.getResult();
                                adapter = new Adapter_quanly_sanpham(getApplicationContext(),Array_sanpham);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
                                binding.Recyclerview.setLayoutManager(layoutManager);
                                binding.Recyclerview.setAdapter(adapter);
                            }
                        },throwable ->{}
                ));
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("Cập nhật")){
            UPDATE_PRODUCT();
        }else if (item.getTitle().equals("Xóa")){
            Dialogxoa(Gravity.CENTER);
        }
        return super.onContextItemSelected(item);
    }

    void UPDATE_PRODUCT() {
        Intent intent = new Intent(getApplicationContext(),Them_sua_Activity.class);
        intent.putExtra("Cập nhật",sanpham);
        startActivity(intent);
        finish();
    }
    void Dialogxoa(int gravity) {
        dialog.setContentView(R.layout.dialog_xoa);
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
        // xu ly code
        // khai bao & anh xa
        Button dialogHuy = dialog.findViewById(R.id.dialogHuy);
        Button dialogXoa = dialog.findViewById(R.id.dialogXoa);
        dialogXoa.setOnClickListener(view -> {
            DELETE_PRODUCT();
        });
        dialogHuy.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    void DELETE_PRODUCT() {
        compositeDisposable.add(api_polysmall.DELETE_PRODUCT(sanpham.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            if (messageModel.isSuccess()){
                                showMsg("Xóa thành công");
                                GET_QUANLYSANPHAM();
                                dialog.dismiss();
                            }else {
                                showMsg("Xóa thất bại");
                            }
                        },
                        throwable -> {
                            showMsg(throwable.getMessage());
                        }
                ));
    }


    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void evenSuaXoa(SuaXoaEvent event){
        if (event != null){
            sanpham = event.getSanPham();
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
    void _refreshLayout() {
        binding.RefeshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        if (adapter==null){
            handler.postDelayed(() -> binding.RefeshLayout.setRefreshing(false),1500);
        }else {
            GET_QUANLYSANPHAM();
            adapter.notifyDataSetChanged();
            handler.postDelayed(() -> binding.RefeshLayout.setRefreshing(false),1500);
        }
    }
}