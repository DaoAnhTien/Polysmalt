package com.example.polysmall.views;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.polysmall.MainActivity;
import com.example.polysmall.R;
import com.example.polysmall.controller.adapters.sanpham.Adapter_Allproduct;
import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.models.GioHang;
import com.example.polysmall.controller.models.Sanpham;
import com.example.polysmall.controller.models.User;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.databinding.ActivityAllPrductBinding;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class All_prduct_Activity extends Base_Activity implements SwipeRefreshLayout.OnRefreshListener{
    private ActivityAllPrductBinding binding;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Api_Polysmall api_polysmall;
    List<Sanpham> Array_sanpham;
    Adapter_Allproduct adapter_allproduct;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllPrductBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        PHANQUYENNAVI();
        API();
        trangthaiDangnhap();
        _refreshLayout();
    }
    // Check login
    void trangthaiDangnhap(){
        Paper.init(this);
        if (Paper.book().read("User.txt")!= null){
            User user = Paper.book().read("User.txt");
            Utils.user_current = user;
        }
        if (Paper.book().read("cart")!=null){
            List<GioHang> list =Paper.book().read("cart");
            Utils.manggiohang = list;
            Giohang();
        }
    }
    @SuppressLint("ResourceAsColor")
    private void API() {
        api_polysmall = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_Polysmall.class);
        Array_sanpham = new ArrayList<>();
        GET_MoiNhat();
        binding.btnGetall.setTextColor(Color.WHITE);
        binding.btnGetall.setBackgroundResource(R.color.colorAccent);
        Giohang();
        binding.btnGetall.setOnClickListener(view -> {
            GET_MoiNhat();
            binding.ClickGiasapxep.setText(R.string.gia);
            binding.ClickGia2sapxep.setText(R.string.gia);
            binding.ClickGiasapxep.setTextColor(Color.BLACK);
            binding.ClickGia2sapxep.setTextColor(Color.BLACK);
            binding.btnGetall.setTextColor(Color.WHITE);
            binding.btnGetall.setBackgroundResource(R.color.colorAccent);
            binding.btncunhat.setTextColor(Color.BLACK);
            binding.btncunhat.setBackgroundResource(R.color.white);
            return;
        });

        binding.btncunhat.setOnClickListener(view -> {
            GET_CuNhat();
            binding.ClickGiasapxep.setText(R.string.gia);
            binding.ClickGia2sapxep.setText(R.string.gia);
            binding.ClickGiasapxep.setTextColor(Color.BLACK);
            binding.ClickGia2sapxep.setTextColor(Color.BLACK);
            binding.btnGetall.setTextColor(Color.BLACK);
            binding.btnGetall.setBackgroundResource(R.color.white);
            binding.btncunhat.setTextColor(Color.WHITE);
            binding.btncunhat.setBackgroundResource(R.color.colorAccent);
        });

        binding.ClickGiasapxep.setOnClickListener(view -> {
            binding.CardGia3.setVisibility(View.VISIBLE);
            binding.ClickGia2sapxep.setVisibility(View.VISIBLE);
            binding.card1.setVisibility(View.GONE);
            binding.ClickGiasapxep.setVisibility(View.GONE);
            return;
        });
        binding.ClickGia2sapxep.setOnClickListener(view -> {
            binding.CardGia3.setVisibility(View.VISIBLE);
            binding.ClickGiasapxep.setVisibility(View.VISIBLE);
            binding.card1.setVisibility(View.VISIBLE);
            binding.ClickGia2sapxep.setVisibility(View.GONE);
            return;
        });
        
        binding.txtGiathapdencao.setOnClickListener(view -> {
            GET_TANGDAN();
            binding.CardGia3.setVisibility(View.GONE);
            binding.ClickGiasapxep.setText(R.string.gia1);
            binding.ClickGia2sapxep.setText(R.string.gia1);
            binding.ClickGiasapxep.setTextColor(getResources().getColor(R.color.colorAccent));
            binding.ClickGia2sapxep.setTextColor(getResources().getColor(R.color.colorAccent));
            binding.btnGetall.setTextColor(Color.BLACK);
            binding.btncunhat.setTextColor(Color.BLACK);
            binding.btnGetall.setBackgroundResource(R.color.white);
            binding.btncunhat.setBackgroundResource(R.color.white);
            return;
        });
        binding.txtGiacaodenthap.setOnClickListener(view -> {
            GET_GIAMDAN();
            binding.CardGia3.setVisibility(View.GONE);
            binding.ClickGiasapxep.setText(R.string.gia2);
            binding.ClickGia2sapxep.setText(R.string.gia2);
            binding.ClickGiasapxep.setTextColor(Color.RED);
            binding.ClickGia2sapxep.setTextColor(Color.RED);
            binding.btnGetall.setTextColor(Color.BLACK);
            binding.btncunhat.setTextColor(Color.BLACK);
            binding.btncunhat.setBackgroundResource(R.color.white);
            binding.btnGetall.setBackgroundResource(R.color.white);
            return;
        });

    }
    void GET_GIAMDAN(){
        compositeDisposable.add(api_polysmall.GET_SAPXEP_GIAMDAN()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanphamModel ->{
                            if (sanphamModel.isSuccess()){
                                Array_sanpham = sanphamModel.getResult();
                                adapter_allproduct = new Adapter_Allproduct(getApplicationContext(),Array_sanpham);
                                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3); // set layout 2
                                binding.RecyclerView.setLayoutManager(layoutManager);
                                binding.RecyclerView.setAdapter(adapter_allproduct);
                            }
                        },throwable ->{}
                ));
    }

    void  GET_TANGDAN(){
        compositeDisposable.add(api_polysmall.GET_SAPXEP_TANGDAN()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanphamModel ->{
                            if (sanphamModel.isSuccess()){
                                Array_sanpham = sanphamModel.getResult();
                                adapter_allproduct = new Adapter_Allproduct(getApplicationContext(),Array_sanpham);
                                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3); // set layout 2
                                binding.RecyclerView.setLayoutManager(layoutManager);
                                binding.RecyclerView.setAdapter(adapter_allproduct);
                            }
                        },throwable ->{}
                ));
    }

    void GET_CuNhat(){
        compositeDisposable.add(api_polysmall.GET_CUNHAT()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanphamModel ->{
                            if (sanphamModel.isSuccess()){
                                Array_sanpham = sanphamModel.getResult();
                                adapter_allproduct = new Adapter_Allproduct(getApplicationContext(),Array_sanpham);
                                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3); // set layout 2
                                binding.RecyclerView.setLayoutManager(layoutManager);
                                binding.RecyclerView.setAdapter(adapter_allproduct);
                            }
                        },throwable ->{}
                ));
    }
    void GET_MoiNhat() {
        compositeDisposable.add(api_polysmall.GET_MOINHAT()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanphamModel ->{
                            if (sanphamModel.isSuccess()){
                                Array_sanpham = sanphamModel.getResult();
                                adapter_allproduct = new Adapter_Allproduct(getApplicationContext(),Array_sanpham);
                                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3); // set layout 2
                                binding.RecyclerView.setLayoutManager(layoutManager);
                                binding.RecyclerView.setAdapter(adapter_allproduct);
                            }
                        },throwable ->{}
                ));
    }

    void PHANQUYENNAVI() {
        if(Utils.user_current.getStatus()==0){
            binding.navigationAdmin.setVisibility(View.GONE);
            NavigatorBottomUser();
        }else {
            binding.navigationUser.setVisibility(View.GONE);
            NavigatorBottomAdmin();
        }
    }

    void NavigatorBottomUser(){
        binding.navigationUser.setItemSelected(R.id.All_product,true);
        binding.navigationUser.setOnItemSelectedListener(i -> {
            switch (i){
                case R.id.Home_activity:{
                    navigate(MainActivity.class);
                    overridePendingTransition(0,0);
                    finish();
                    break;
                }
                case R.id.M_Lichsumua:{
                    navigate(LichsumuahangActivity.class);
                    overridePendingTransition(0,0);
                    finish();
                    break;
                }
                case R.id.nguoidung_activity:{
                    navigate(Thongtincanhan_Activity.class);
                    overridePendingTransition(0,0);
                    finish();
                    break;
                }

            }
    });
}
    void NavigatorBottomAdmin(){
        binding.navigationAdmin.setItemSelected(R.id.All_product,true);
        binding.navigationAdmin.setOnItemSelectedListener(i -> {
            switch (i){
                case R.id.Home_activity:{
                    navigate(MainActivity.class);
                    overridePendingTransition(0,0);
                    finish();
                    break;
                }
                case R.id.DonhangAdmin:{
                    navigate(LichsumuahangActivity.class);
                    overridePendingTransition(0,0);
                    finish();
                    break;
                }
                case R.id.nguoidung_activity:{
                    navigate(Thongtincanhan_Activity.class);
                    overridePendingTransition(0,0);
                    finish();
                    break;
                }

            }
        });
    }

    // xử lý giỏ hàng

    void Giohang() {
        if (Utils.manggiohang == null){
            Utils.manggiohang = new ArrayList<>();
        }else {
            int totalItem = 0;
            for (int i = 0 ; i<Utils.manggiohang.size();i++){
                totalItem =  totalItem+Utils.manggiohang.get(i).getSoluong();
            }
            if (totalItem>0){
                binding.menusl.setVisibility(View.VISIBLE);
                binding.menusl.setText(String.valueOf(totalItem));
            }

        }
        binding.FrameLayoutGiohang.setOnClickListener(view -> {
            navigate(Giohang_Activity.class);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // set tổng giá trị đơn hàng của giỏ h
        int totalItem = 0;
        for (int i = 0 ; i<Utils.manggiohang.size();i++){
            totalItem =  totalItem+Utils.manggiohang.get(i).getSoluong();
        }
        binding.menusl.setText(String.valueOf(totalItem));
    }

    void _refreshLayout() {
        binding.RefeshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
      if (adapter_allproduct==null){
          handler.postDelayed(() -> binding.RefeshLayout.setRefreshing(false),1500);
      }else {
          GET_GIAMDAN();
          GET_TANGDAN();
          GET_CuNhat();
          GET_MoiNhat();
          adapter_allproduct.notifyDataSetChanged();
          handler.postDelayed(() -> binding.RefeshLayout.setRefreshing(false),1500);
      }
    }
}
