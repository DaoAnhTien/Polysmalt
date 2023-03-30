package com.example.polysmall.views;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.polysmall.MainActivity;
import com.example.polysmall.R;
import com.example.polysmall.controller.adapters.lichsus.Adapter_Lichsu_donhang;
import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.models.Evenrbus.LichsuEvenrbus;
import com.example.polysmall.controller.models.GioHang;
import com.example.polysmall.controller.models.User;
import com.example.polysmall.controller.models.lichsu.Lichsu;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.databinding.ActivityLichsumuahangBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LichsumuahangActivity extends Base_Activity implements SwipeRefreshLayout.OnRefreshListener {
    private ActivityLichsumuahangBinding binding;
    Api_Polysmall api_polysmall;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Adapter_Lichsu_donhang adapter;
    int totalItem = 0;
    Dialog dialog;
    int tinhtrang;
    Lichsu lichsu;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLichsumuahangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initview();
        Giohang();
        PHANQUYENNAVI();
        phanQuyen();
        trangthaiDangnhap();
        _refreshLayout();
    }
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
        }else {
            Giohang();
            return;
        }
    }

    void initview() {
        api_polysmall = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_Polysmall.class);
    }

    void phanQuyen(){
        if (Utils.user_current.getStatus()== 1){
            binding.choxacnhan.setTextColor(Color.WHITE);
            binding.choxacnhan.setBackgroundResource(R.drawable.btn_background_canhan_tow);
            GetUsertrangthai0();
            binding.RecyclerViewChoxacnhan.setVisibility(View.VISIBLE);
            EvenClickUser();
        }else {
            binding.title.setText("Đơn hàng");
            binding.choxacnhan.setTextColor(Color.WHITE);
            binding.choxacnhan.setBackgroundResource(R.drawable.btn_background_canhan_tow);
            binding.FrameLayoutGiohang.setVisibility(View.GONE);
            GetAdmintrangthai0();
            binding.RecyclerViewChoxacnhan.setVisibility(View.VISIBLE);
            EvenClickAdmin();
        }
    }

    void EvenClickUser(){
        binding.choxacnhan.setOnClickListener(view -> {
            // set backgound
            binding.choxacnhan.setBackgroundResource(R.drawable.btn_background_canhan_tow);
            binding.cholayhang.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.danggiao.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.dagiao.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.dahuy.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.tatca.setBackgroundResource(R.drawable.btn_background_canhan);
            // set mau chử
            binding.choxacnhan.setTextColor(Color.WHITE);
            binding.cholayhang.setTextColor(Color.BLACK);
            binding.danggiao.setTextColor(Color.BLACK);
            binding.dagiao.setTextColor(Color.BLACK);
            binding.dahuy.setTextColor(Color.BLACK);
            binding.tatca.setTextColor(Color.BLACK);
            // set layout tương ứng
            binding.RecyclerViewChoxacnhan.setVisibility(View.VISIBLE);
            binding.RecyclerViewCholayhang.setVisibility(View.GONE);
            binding.RecyclerViewDanggiao.setVisibility(View.GONE);
            binding.RecyclerViewDagiao.setVisibility(View.GONE);
            binding.RecyclerViewDahuy.setVisibility(View.GONE);
            binding.RecyclerViewLichsu.setVisibility(View.GONE);
            GetUsertrangthai0();
        });
        binding.cholayhang.setOnClickListener(view -> {
            // set backgound
            binding.choxacnhan.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.cholayhang.setBackgroundResource(R.drawable.btn_background_canhan_tow);
            binding.danggiao.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.dagiao.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.dahuy.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.tatca.setBackgroundResource(R.drawable.btn_background_canhan);
            //set  màu chử
            binding.choxacnhan.setTextColor(Color.BLACK);
            binding.cholayhang.setTextColor(Color.WHITE);
            binding.danggiao.setTextColor(Color.BLACK);
            binding.dagiao.setTextColor(Color.BLACK);
            binding.dahuy.setTextColor(Color.BLACK);
            binding.tatca.setTextColor(Color.BLACK);
            // set layout tương ứng
            binding.RecyclerViewChoxacnhan.setVisibility(View.GONE);
            binding.RecyclerViewCholayhang.setVisibility(View.VISIBLE);
            binding.RecyclerViewDanggiao.setVisibility(View.GONE);
            binding.RecyclerViewDagiao.setVisibility(View.GONE);
            binding.RecyclerViewDahuy.setVisibility(View.GONE);
            binding.RecyclerViewLichsu.setVisibility(View.GONE);
            GetUsertrangthai1();
        });
        binding.danggiao.setOnClickListener(view -> {
            // set backgound
            binding.choxacnhan.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.cholayhang.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.danggiao.setBackgroundResource(R.drawable.btn_background_canhan_tow);
            binding.dagiao.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.dahuy.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.tatca.setBackgroundResource(R.drawable.btn_background_canhan);
            // set màu chử
            binding.choxacnhan.setTextColor(Color.BLACK);
            binding.cholayhang.setTextColor(Color.BLACK);
            binding.danggiao.setTextColor(Color.WHITE);
            binding.dagiao.setTextColor(Color.BLACK);
            binding.dahuy.setTextColor(Color.BLACK);
            binding.tatca.setTextColor(Color.BLACK);
            //set layout tương ứng
            binding.RecyclerViewChoxacnhan.setVisibility(View.GONE);
            binding.RecyclerViewCholayhang.setVisibility(View.GONE);
            binding.RecyclerViewDanggiao.setVisibility(View.VISIBLE);
            binding.RecyclerViewDagiao.setVisibility(View.GONE);
            binding.RecyclerViewDahuy.setVisibility(View.GONE);
            binding.RecyclerViewLichsu.setVisibility(View.GONE);
            GetUsertrangthai2();
        });
        binding.dagiao.setOnClickListener(view -> {
            // set backgound
            binding.choxacnhan.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.cholayhang.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.danggiao.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.dagiao.setBackgroundResource(R.drawable.btn_background_canhan_tow);
            binding.dahuy.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.tatca.setBackgroundResource(R.drawable.btn_background_canhan);
            // set màu chử
            binding.choxacnhan.setTextColor(Color.BLACK);
            binding.cholayhang.setTextColor(Color.BLACK);
            binding.danggiao.setTextColor(Color.BLACK);
            binding.dagiao.setTextColor(Color.WHITE);
            binding.dahuy.setTextColor(Color.BLACK);
            binding.tatca.setTextColor(Color.BLACK);
            // set layout tương ứng
            binding.RecyclerViewChoxacnhan.setVisibility(View.GONE);
            binding.RecyclerViewCholayhang.setVisibility(View.GONE);
            binding.RecyclerViewDanggiao.setVisibility(View.GONE);
            binding.RecyclerViewDagiao.setVisibility(View.VISIBLE);
            binding.RecyclerViewDahuy.setVisibility(View.GONE);
            binding.RecyclerViewLichsu.setVisibility(View.GONE);
            GetUsertrangthai3();
        });
        binding.dahuy.setOnClickListener(view -> {
            // set backgound
            binding.choxacnhan.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.cholayhang.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.danggiao.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.dagiao.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.dahuy.setBackgroundResource(R.drawable.btn_background_canhan_tow);
            binding.tatca.setBackgroundResource(R.drawable.btn_background_canhan);
            // set màu chử
            binding.choxacnhan.setTextColor(Color.BLACK);
            binding.cholayhang.setTextColor(Color.BLACK);
            binding.danggiao.setTextColor(Color.BLACK);
            binding.dagiao.setTextColor(Color.BLACK);
            binding.dahuy.setTextColor(Color.WHITE);
            binding.tatca.setTextColor(Color.BLACK);
            binding.RecyclerViewChoxacnhan.setVisibility(View.GONE);
            binding.RecyclerViewCholayhang.setVisibility(View.GONE);
            binding.RecyclerViewDanggiao.setVisibility(View.GONE);
            binding.RecyclerViewDagiao.setVisibility(View.GONE);
            binding.RecyclerViewDahuy.setVisibility(View.VISIBLE);
            binding.RecyclerViewLichsu.setVisibility(View.GONE);
            GetUsertrangthai4();
        });
        binding.tatca.setOnClickListener(view -> {
            // set backgound
            binding.choxacnhan.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.cholayhang.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.danggiao.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.dagiao.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.dahuy.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.tatca.setBackgroundResource(R.drawable.btn_background_canhan_tow);
            // set màu chử
            binding.choxacnhan.setTextColor(Color.BLACK);
            binding.cholayhang.setTextColor(Color.BLACK);
            binding.danggiao.setTextColor(Color.BLACK);
            binding.dagiao.setTextColor(Color.BLACK);
            binding.dahuy.setTextColor(Color.BLACK);
            binding.tatca.setTextColor(Color.WHITE);
            binding.RecyclerViewChoxacnhan.setVisibility(View.GONE);
            binding.RecyclerViewCholayhang.setVisibility(View.GONE);
            binding.RecyclerViewDanggiao.setVisibility(View.GONE);
            binding.RecyclerViewDagiao.setVisibility(View.GONE);
            binding.RecyclerViewDahuy.setVisibility(View.GONE);
            binding.RecyclerViewLichsu.setVisibility(View.VISIBLE);
            getLichsuUser();
        });
    }

    void EvenClickAdmin(){
        binding.choxacnhan.setOnClickListener(view -> {
            binding.choxacnhan.setBackgroundResource(R.drawable.btn_background_canhan_tow);
            binding.cholayhang.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.danggiao.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.dagiao.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.dahuy.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.tatca.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.choxacnhan.setTextColor(Color.WHITE);
            binding.cholayhang.setTextColor(Color.BLACK);
            binding.danggiao.setTextColor(Color.BLACK);
            binding.dagiao.setTextColor(Color.BLACK);
            binding.dahuy.setTextColor(Color.BLACK);
            binding.tatca.setTextColor(Color.BLACK);
            binding.RecyclerViewChoxacnhan.setVisibility(View.VISIBLE);
            binding.RecyclerViewCholayhang.setVisibility(View.GONE);
            binding.RecyclerViewDanggiao.setVisibility(View.GONE);
            binding.RecyclerViewDagiao.setVisibility(View.GONE);
            binding.RecyclerViewDahuy.setVisibility(View.GONE);
            binding.RecyclerViewLichsu.setVisibility(View.GONE);
            GetAdmintrangthai0();

        });
        binding.cholayhang.setOnClickListener(view -> {
            binding.choxacnhan.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.cholayhang.setBackgroundResource(R.drawable.btn_background_canhan_tow);
            binding.danggiao.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.dagiao.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.dahuy.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.tatca.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.choxacnhan.setTextColor(Color.BLACK);
            binding.cholayhang.setTextColor(Color.WHITE);
            binding.danggiao.setTextColor(Color.BLACK);
            binding.dagiao.setTextColor(Color.BLACK);
            binding.dahuy.setTextColor(Color.BLACK);
            binding.tatca.setTextColor(Color.BLACK);
            GetAdmintrangthai1();
            binding.RecyclerViewChoxacnhan.setVisibility(View.GONE);
            binding.RecyclerViewCholayhang.setVisibility(View.VISIBLE);
            binding.RecyclerViewDanggiao.setVisibility(View.GONE);
            binding.RecyclerViewDagiao.setVisibility(View.GONE);
            binding.RecyclerViewDahuy.setVisibility(View.GONE);
            binding.RecyclerViewLichsu.setVisibility(View.GONE);
        });
        binding.danggiao.setOnClickListener(view -> {
            // set backgound
            binding.choxacnhan.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.cholayhang.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.danggiao.setBackgroundResource(R.drawable.btn_background_canhan_tow);
            binding.dagiao.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.dahuy.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.tatca.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.choxacnhan.setTextColor(Color.BLACK);
            binding.cholayhang.setTextColor(Color.BLACK);
            binding.danggiao.setTextColor(Color.WHITE);
            binding.dagiao.setTextColor(Color.BLACK);
            binding.dahuy.setTextColor(Color.BLACK);
            binding.tatca.setTextColor(Color.BLACK);
            GetAdmintrangthai2();
            binding.RecyclerViewChoxacnhan.setVisibility(View.GONE);
            binding.RecyclerViewCholayhang.setVisibility(View.GONE);
            binding.RecyclerViewDanggiao.setVisibility(View.VISIBLE);
            binding.RecyclerViewDagiao.setVisibility(View.GONE);
            binding.RecyclerViewDahuy.setVisibility(View.GONE);
            binding.RecyclerViewLichsu.setVisibility(View.GONE);
        });
        binding.dagiao.setOnClickListener(view -> {
            // set backgound
            binding.choxacnhan.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.cholayhang.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.danggiao.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.dagiao.setBackgroundResource(R.drawable.btn_background_canhan_tow);
            binding.dahuy.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.tatca.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.choxacnhan.setTextColor(Color.BLACK);
            binding.cholayhang.setTextColor(Color.BLACK);
            binding.danggiao.setTextColor(Color.BLACK);
            binding.dagiao.setTextColor(Color.WHITE);
            binding.dahuy.setTextColor(Color.BLACK);
            binding.tatca.setTextColor(Color.BLACK);
            GetAdmintrangthai3();
            binding.RecyclerViewChoxacnhan.setVisibility(View.GONE);
            binding.RecyclerViewCholayhang.setVisibility(View.GONE);
            binding.RecyclerViewDanggiao.setVisibility(View.GONE);
            binding.RecyclerViewDagiao.setVisibility(View.VISIBLE);
            binding.RecyclerViewDahuy.setVisibility(View.GONE);
            binding.RecyclerViewLichsu.setVisibility(View.GONE);
        });
        binding.dahuy.setOnClickListener(view -> {
            // set backgound
            binding.choxacnhan.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.cholayhang.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.danggiao.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.dagiao.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.dahuy.setBackgroundResource(R.drawable.btn_background_canhan_tow);
            binding.tatca.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.choxacnhan.setTextColor(Color.BLACK);
            binding.cholayhang.setTextColor(Color.BLACK);
            binding.danggiao.setTextColor(Color.BLACK);
            binding.dagiao.setTextColor(Color.BLACK);
            binding.dahuy.setTextColor(Color.WHITE);
            binding.tatca.setTextColor(Color.BLACK);
            GetAdmintrangthai4();
            binding.RecyclerViewChoxacnhan.setVisibility(View.GONE);
            binding.RecyclerViewCholayhang.setVisibility(View.GONE);
            binding.RecyclerViewDanggiao.setVisibility(View.GONE);
            binding.RecyclerViewDagiao.setVisibility(View.GONE);
            binding.RecyclerViewDahuy.setVisibility(View.VISIBLE);
            binding.RecyclerViewLichsu.setVisibility(View.GONE);
        });
        binding.tatca.setOnClickListener(view -> {
            binding.choxacnhan.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.cholayhang.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.danggiao.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.dagiao.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.dahuy.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.tatca.setBackgroundResource(R.drawable.btn_background_canhan_tow);
            binding.choxacnhan.setTextColor(Color.BLACK);
            binding.cholayhang.setTextColor(Color.BLACK);
            binding.danggiao.setTextColor(Color.BLACK);
            binding.dagiao.setTextColor(Color.BLACK);
            binding.dahuy.setTextColor(Color.BLACK);
            binding.tatca.setTextColor(Color.WHITE);
            GETDonhangAdmin();
            binding.RecyclerViewChoxacnhan.setVisibility(View.GONE);
            binding.RecyclerViewCholayhang.setVisibility(View.GONE);
            binding.RecyclerViewDanggiao.setVisibility(View.GONE);
            binding.RecyclerViewDagiao.setVisibility(View.GONE);
            binding.RecyclerViewDahuy.setVisibility(View.GONE);
            binding.RecyclerViewLichsu.setVisibility(View.VISIBLE);
        });
    }

    // user
    void GetUsertrangthai0() {
        compositeDisposable.add(api_polysmall.TRANGTHAI0(Utils.user_current.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        lichsuModel -> {
                            if(lichsuModel.isSuccess()){
                                adapter = new Adapter_Lichsu_donhang(getApplicationContext(),lichsuModel.getResult());
                                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                                binding.RecyclerViewChoxacnhan.setLayoutManager(layoutManager);
                                binding.RecyclerViewChoxacnhan.setAdapter(adapter);
                                binding.line2.setVisibility(View.GONE);
                            }else {
                                binding.RecyclerViewChoxacnhan.setVisibility(View.GONE);
                                binding.line2.setVisibility(View.VISIBLE);
                                binding.btncuahang.setVisibility(View.GONE);
                                binding.textNote.setText("Không có đơn hàng");
                            }
                        },
                        throwable -> {
                        }));
    }
    void GetUsertrangthai1() {
        compositeDisposable.add(api_polysmall.TRANGTHAI1(Utils.user_current.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        lichsuModel -> {
                            if(lichsuModel.isSuccess()){
                                adapter = new Adapter_Lichsu_donhang(getApplicationContext(),lichsuModel.getResult());
                                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                                binding.RecyclerViewCholayhang.setLayoutManager(layoutManager);
                                binding.RecyclerViewCholayhang.setAdapter(adapter);
                                binding.line2.setVisibility(View.GONE);
                            }else {
                                binding.RecyclerViewCholayhang.setVisibility(View.GONE);
                                binding.line2.setVisibility(View.VISIBLE);
                                binding.btncuahang.setVisibility(View.GONE);
                                binding.textNote.setText("Không có đơn hàng");
                            }
                        },
                        throwable -> {
                        }));
    }

    void GetUsertrangthai2() {
        compositeDisposable.add(api_polysmall.TRANGTHAI2(Utils.user_current.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        lichsuModel -> {
                            if(lichsuModel.isSuccess()){
                                adapter = new Adapter_Lichsu_donhang(getApplicationContext(),lichsuModel.getResult());
                                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                                binding.RecyclerViewDanggiao.setLayoutManager(layoutManager);
                                binding.RecyclerViewDanggiao.setAdapter(adapter);
                                binding.line2.setVisibility(View.GONE);
                            }else {
                                binding.RecyclerViewDanggiao.setVisibility(View.GONE);
                                binding.line2.setVisibility(View.VISIBLE);
                                binding.btncuahang.setVisibility(View.GONE);
                                binding.textNote.setText("Không có đơn hàng");
                            }
                        },
                        throwable -> {
                        }));
    }

    void GetUsertrangthai3() {
        compositeDisposable.add(api_polysmall.TRANGTHAI3(Utils.user_current.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        lichsuModel -> {
                            if(lichsuModel.isSuccess()){
                                adapter = new Adapter_Lichsu_donhang(getApplicationContext(),lichsuModel.getResult());
                                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                                binding.RecyclerViewDagiao.setLayoutManager(layoutManager);
                                binding.RecyclerViewDagiao.setAdapter(adapter);
                                binding.line2.setVisibility(View.GONE);
                            }else {
                                binding.RecyclerViewDagiao.setVisibility(View.GONE);
                                binding.line2.setVisibility(View.VISIBLE);
                                binding.btncuahang.setVisibility(View.GONE);
                                binding.textNote.setText("Không có đơn hàng");
                            }
                        },
                        throwable -> {
                        }));
    }
    void GetUsertrangthai4() {
        compositeDisposable.add(api_polysmall.TRANGTHAI4(Utils.user_current.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        lichsuModel -> {
                            if(lichsuModel.isSuccess()){
                                adapter = new Adapter_Lichsu_donhang(getApplicationContext(),lichsuModel.getResult());
                                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                                binding.RecyclerViewDahuy.setLayoutManager(layoutManager);
                                binding.RecyclerViewDahuy.setAdapter(adapter);
                                binding.line2.setVisibility(View.GONE);
                            }else {
                                binding.RecyclerViewDahuy.setVisibility(View.GONE);
                                binding.line2.setVisibility(View.VISIBLE);
                                binding.btncuahang.setVisibility(View.GONE);
                                binding.textNote.setText("Không có đơn hàng");
                            }
                        },
                        throwable -> {
                        }));
    }
    void getLichsuUser() {
        compositeDisposable.add(api_polysmall.POST_LICHSU_DONHANG(Utils.user_current.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        lichsuModel -> {
                            if (lichsuModel.isSuccess()){
                                adapter = new Adapter_Lichsu_donhang(getApplicationContext(),lichsuModel.getResult());
                                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                                binding.RecyclerViewLichsu.setLayoutManager(layoutManager);
                                binding.RecyclerViewLichsu.setAdapter(adapter);
                                binding.line2.setVisibility(View.GONE);
                            }else {
                                binding.line2.setVisibility(View.VISIBLE);
                                binding.btncuahang.setVisibility(View.GONE);
                                binding.textNote.setText("Không có đơn hàng");
                            }
                        },
                        throwable -> {
                        }));
    }
    // admin
    void GetAdmintrangthai0() {
        compositeDisposable.add(api_polysmall.TRANGTHAI0(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        lichsuModel -> {
                            if(lichsuModel.isSuccess()){
                                adapter = new Adapter_Lichsu_donhang(getApplicationContext(),lichsuModel.getResult());
                                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                                binding.RecyclerViewChoxacnhan.setLayoutManager(layoutManager);
                                binding.RecyclerViewChoxacnhan.setAdapter(adapter);
                                binding.line2.setVisibility(View.GONE);
                            }else {
                                binding.RecyclerViewChoxacnhan.setVisibility(View.GONE);
                                binding.line2.setVisibility(View.VISIBLE);
                                binding.btncuahang.setVisibility(View.GONE);
                                binding.textNote.setText("Không có đơn hàng");
                            }
                        },
                        throwable -> {
                        }));
    }
    void GetAdmintrangthai1() {
        compositeDisposable.add(api_polysmall.TRANGTHAI1(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        lichsuModel -> {
                            if(lichsuModel.isSuccess()){
                                adapter = new Adapter_Lichsu_donhang(getApplicationContext(),lichsuModel.getResult());
                                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                                binding.RecyclerViewCholayhang.setLayoutManager(layoutManager);
                                binding.RecyclerViewCholayhang.setAdapter(adapter);
                                binding.line2.setVisibility(View.GONE);
                            }else {
                                binding.RecyclerViewCholayhang.setVisibility(View.GONE);
                                binding.line2.setVisibility(View.VISIBLE);
                                binding.btncuahang.setVisibility(View.GONE);
                                binding.textNote.setText("Không có đơn hàng");
                            }
                        },
                        throwable -> {
                        }));
    }
    void GetAdmintrangthai2() {
        compositeDisposable.add(api_polysmall.TRANGTHAI2(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        lichsuModel -> {
                            if(lichsuModel.isSuccess()){
                                adapter = new Adapter_Lichsu_donhang(getApplicationContext(),lichsuModel.getResult());
                                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                                binding.RecyclerViewDanggiao.setLayoutManager(layoutManager);
                                binding.RecyclerViewDanggiao.setAdapter(adapter);
                                binding.line2.setVisibility(View.GONE);
                            }else {
                                binding.RecyclerViewDanggiao.setVisibility(View.GONE);
                                binding.line2.setVisibility(View.VISIBLE);
                                binding.btncuahang.setVisibility(View.GONE);
                                binding.textNote.setText("Không có đơn hàng");
                            }
                        },
                        throwable -> {
                        }));
    }

    void GetAdmintrangthai3() {
        compositeDisposable.add(api_polysmall.TRANGTHAI3(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        lichsuModel -> {
                            if(lichsuModel.isSuccess()){
                                adapter = new Adapter_Lichsu_donhang(getApplicationContext(),lichsuModel.getResult());
                                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                                binding.RecyclerViewDagiao.setLayoutManager(layoutManager);
                                binding.RecyclerViewDagiao.setAdapter(adapter);
                                binding.line2.setVisibility(View.GONE);
                            }else {
                                binding.RecyclerViewDagiao.setVisibility(View.GONE);
                                binding.line2.setVisibility(View.VISIBLE);
                                binding.btncuahang.setVisibility(View.GONE);
                                binding.textNote.setText("Không có đơn hàng");
                            }
                        },
                        throwable -> {
                        }));
    }

    void GetAdmintrangthai4() {
        compositeDisposable.add(api_polysmall.TRANGTHAI4(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        lichsuModel -> {
                            if(lichsuModel.isSuccess()){
                                adapter = new Adapter_Lichsu_donhang(getApplicationContext(),lichsuModel.getResult());
                                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                                binding.RecyclerViewDahuy.setLayoutManager(layoutManager);
                                binding.RecyclerViewDahuy.setAdapter(adapter);
                                binding.line2.setVisibility(View.GONE);
                            }else {
                                binding.RecyclerViewDahuy.setVisibility(View.GONE);
                                binding.line2.setVisibility(View.VISIBLE);
                                binding.btncuahang.setVisibility(View.GONE);
                                binding.textNote.setText("Không có đơn hàng");
                            }
                        },
                        throwable -> {
                        }));
    }

    void GETDonhangAdmin() {
        compositeDisposable.add(api_polysmall.POST_LICHSU_DONHANG(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        lichsuModel -> {
                            if(lichsuModel.isSuccess()){
                                adapter = new Adapter_Lichsu_donhang(getApplicationContext(),lichsuModel.getResult());
                                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                                binding.RecyclerViewLichsu.setLayoutManager(layoutManager);
                                binding.RecyclerViewLichsu.setAdapter(adapter);
                                binding.line2.setVisibility(View.GONE);
                            }else {
                                binding.line2.setVisibility(View.VISIBLE);
                                binding.btncuahang.setVisibility(View.GONE);
                                binding.textNote.setText("Không có đơn hàng");
                            }
                        },
                        throwable -> {
                        }));
    }

    void showCustumDialog(int gravity) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_donhang);
        Spinner spinner = dialog.findViewById(R.id.spinner_dialog);
        AppCompatButton btn_dongy = dialog.findViewById(R.id.dongy_dialog);
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
        List<String> list = new ArrayList<>();
        list.add("Chờ xác nhận");
        list.add("Chờ lấy hàng");
        list.add("Đang giao");
        list.add("Đã giao");
        list.add("Đã hủy");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);
        // vị trí cần chọn
        spinner.setSelection(lichsu.getTrangthai());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tinhtrang = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        btn_dongy.setOnClickListener(view1 -> {
            capNhatdonhang();
        });

        dialog.show();
    }
    void HUYDONMUA(int gravity){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_huy);
        Button dialogdongy = dialog.findViewById(R.id.dialogdongy);
        Button dialogHuy = dialog.findViewById(R.id.dialogHuy);
        Window window = dialog.getWindow();
        if (window == null){
            dialog.dismiss();
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
        // vị trí cần chọn

        dialogdongy.setOnClickListener(view -> {
            HuyDonHangUser();
        });
        dialogHuy.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }
    void HuyDonHangUser() {
        compositeDisposable.add(api_polysmall.HUYDONMUA(lichsu.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            GetUsertrangthai0();
                            dialog.dismiss();
                        },
                        throwable -> {
                        }));
    }
    void capNhatdonhang() {
        compositeDisposable.add(api_polysmall.UPDATE_TRANGTHAI_ADMIN(lichsu.getId(), tinhtrang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            GetAdmintrangthai0();
                            GetAdmintrangthai1();
                            GetAdmintrangthai2();
                            GetAdmintrangthai3();
                            GetAdmintrangthai4();
                            GETDonhangAdmin();
                            dialog.dismiss();
                        },
                        throwable -> {

                        }));
    }

    void PHANQUYENNAVI() {
        if(Utils.user_current.getStatus()==1){
            binding.navigationAdmin.setVisibility(View.GONE);
            NavigatorBottomUser();
        }else {
            binding.navigationUser.setVisibility(View.GONE);
            NavigatorBottomAdmin();
        }
    }
    void NavigatorBottomUser(){
        binding.navigationUser.setItemSelected(R.id.M_Lichsumua,true);
        binding.navigationUser.setOnItemSelectedListener(i -> {
            switch (i){
                case R.id.Home_activity:{
                    navigate(MainActivity.class);
                    overridePendingTransition(0,0);
                    finish();
                    break;
                }
                case R.id.All_product:{
                    navigate(All_prduct_Activity.class);
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
        binding.navigationAdmin.setItemSelected(R.id.DonhangAdmin,true);
        binding.navigationAdmin.setOnItemSelectedListener(i -> {
            switch (i){
                case R.id.Home_activity:{
                    navigate(MainActivity.class);
                    overridePendingTransition(0,0);
                    finish();
                    break;
                }
                case R.id.All_product:{
                    navigate(All_prduct_Activity.class);
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
            binding.menusl.setText(String.valueOf(totalItem));
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
        for (int i = 0; i< Utils.manggiohang.size(); i++){
            totalItem =  totalItem+Utils.manggiohang.get(i).getSoluong();
        }
        if (totalItem>0){
            binding.menusl.setVisibility(View.VISIBLE);
            binding.menusl.setText(String.valueOf(totalItem));
        }
    }

    // admin
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void evenLichsu(LichsuEvenrbus event) {
        if (event != null) {
            lichsu = event.getLichsu();
            if (Utils.user_current.getStatus()==1){
                  HUYDONMUA(Gravity.CENTER);
            }else {
                showCustumDialog(Gravity.CENTER);
            }
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
            if (Utils.user_current.getStatus()==0){
                GetAdmintrangthai0();
                GetAdmintrangthai1();
                GetAdmintrangthai2();
                GetAdmintrangthai3();
                GetAdmintrangthai4();
                GETDonhangAdmin();
            }else {
                GetUsertrangthai0();
                GetUsertrangthai1();
                GetUsertrangthai2();
                GetUsertrangthai3();
                GetUsertrangthai4();
                getLichsuUser();
            }
            adapter.notifyDataSetChanged();
            handler.postDelayed(() -> binding.RefeshLayout.setRefreshing(false),1500);
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
}