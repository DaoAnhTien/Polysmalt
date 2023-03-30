package com.example.polysmall.views.Doanhthu;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.polysmall.R;
import com.example.polysmall.controller.adapters.danhsach.AdapterDanhsachchitietDoanhthu;
import com.example.polysmall.controller.adapters.danhsach.AdapterDoanhthuDanhsach;
import com.example.polysmall.controller.adapters.danhsach.Adapter_Danhsach;
import com.example.polysmall.controller.adapters.doanhthu.AdapterDoanhthu;
import com.example.polysmall.controller.adapters.doanhthu.AdapterTongdoanhthu;
import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.models.Danhsach;
import com.example.polysmall.controller.models.Thongke;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.databinding.ActivityDoanhthuBinding;
import com.example.polysmall.views.Thongtincanhan_Activity;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Doanhthu_Activity extends Base_Activity {
    private ActivityDoanhthuBinding binding;
    Api_Polysmall api_polysmall;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    List<Danhsach> Array_danhsach;
    Adapter_Danhsach adapter;
    List<Thongke> arrayDoanhthu;
    AdapterDoanhthu adapterDoanhthu;
    AdapterDoanhthuDanhsach adapterDoanhthuDanhsach;
    AdapterTongdoanhthu adapterTongdoanhthu;
    AdapterDanhsachchitietDoanhthu adapterDanhsachchitietDoanhthu;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoanhthuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        API();
        Evenclick();
        ActionToolBar();
        binding.LinearLayout1.setVisibility(View.VISIBLE);
        binding.txtThongke.setTextColor(Color.WHITE);
        binding.txtDoanhthu.setTextColor(Color.BLACK);
        binding.txtThongke.setBackgroundResource(R.drawable.btn_background_canhan_tow);
        binding.txtDoanhthu.setBackgroundResource(R.drawable.btn_background_canhan);
        binding.buttonbanchaynhat.setBackgroundResource(R.drawable.background_logout);
        TopNHIEUNHAT();
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
    private void API() {
        api_polysmall = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_Polysmall.class);
        Array_danhsach = new ArrayList<>();
        arrayDoanhthu = new ArrayList<>();
        GET_DANHSACH_NHIEUNHAT();
    }
    @SuppressLint("ResourceAsColor")
    private void Evenclick() {
        // click chi tiết
        binding.txtDoanhthuchitiet.setOnClickListener(view -> {
            binding.Toolbar.setTitle("Chi tiết");
            binding.LinearLayout5.setVisibility(View.VISIBLE);
            binding.LinearLayout4.setVisibility(View.GONE);
            binding.LinearLayout1.setVisibility(View.GONE);
            binding.LinearLayoutTop.setVisibility(View.GONE);
            binding.LinearLayout3.setVisibility(View.GONE);
            getSumTongtien2();
            getListDoanthu();
            return;
        });
        // button card item
        binding.txtThongke.setOnClickListener(view -> {
            binding.LinearLayoutTop.setVisibility(View.VISIBLE);
            binding.txtThongke.setTextColor(Color.WHITE);
            binding.txtDoanhthu.setTextColor(Color.BLACK);
            binding.txtThongke.setBackgroundResource(R.drawable.btn_background_canhan_tow);
            binding.txtDoanhthu.setBackgroundResource(R.drawable.btn_background_canhan);

        });
        binding.txtDoanhthu.setOnClickListener(view -> {
            binding.txtThongke.setTextColor(Color.BLACK);
            binding.txtDoanhthu.setTextColor(Color.WHITE);
            binding.txtThongke.setBackgroundResource(R.drawable.btn_background_canhan);
            binding.txtDoanhthu.setBackgroundResource(R.drawable.btn_background_canhan_tow);
            binding.LinearLayoutTop.setVisibility(View.GONE);
            binding.Toolbar.setTitle("Doanh thu");
            binding.LinearLayout1.setVisibility(View.GONE);
            binding.LinearLayoutTop.setVisibility(View.GONE);
            binding.LinearLayout3.setVisibility(View.GONE);
            binding.LinearLayout4.setVisibility(View.VISIBLE);
            binding.LinearLayout5.setVisibility(View.GONE);
            DOANHTHU();
//            getBarEntries();
        });
        // button card item
        binding.buttonbanchaynhat.setOnClickListener(view -> {
            binding.Toolbar.setTitle("Thống kê");
            binding.LinearLayout1.setVisibility(View.VISIBLE);
            binding.LinearLayoutTop.setVisibility(View.GONE);
            binding.LinearLayout2.setVisibility(View.VISIBLE);
            binding.LinearLayout3.setVisibility(View.GONE);
            binding.LinearLayout4.setVisibility(View.GONE);
            binding.LinearLayout5.setVisibility(View.GONE);
            binding.buttonbanchaynhat.setBackgroundResource(R.drawable.background_logout);
            binding.buttonbanit.setBackgroundResource(R.drawable.btn_background_canhan_tow);
            TopNHIEUNHAT();
            return;
        });
        binding.buttonbanit.setOnClickListener(view -> {
            binding.Toolbar.setTitle("Thống kê");
            binding.buttonbanit.setBackgroundResource(R.drawable.background_logout);
            binding.buttonbanchaynhat.setBackgroundResource(R.drawable.btn_background_canhan_tow);
            binding.LinearLayout1.setVisibility(View.VISIBLE);
            binding.LinearLayoutTop.setVisibility(View.GONE);
            binding.LinearLayout2.setVisibility(View.GONE);
            binding.LinearLayout3.setVisibility(View.VISIBLE);
            binding.LinearLayout4.setVisibility(View.GONE);
            binding.LinearLayout5.setVisibility(View.GONE);
            TopITNHAT();
            return;

        });
    }

    void TopNHIEUNHAT() {
        GET_THONGKE_SANPHAM_NHIEUNHAT();
        GET_DANHSACH_NHIEUNHAT();
    }
    void TopITNHAT(){
        GET_THONGKE_SANPHAM_BANIT();
        GET_DANHSACH_ITNHAT();
    }
    void GET_THONGKE_SANPHAM_NHIEUNHAT() {
        List<PieEntry> listdata = new ArrayList<>();
        compositeDisposable.add(api_polysmall.GET_THONGKE_NHIEUNHAT()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongkeModel -> {
                            if (thongkeModel.isSuccess()){
                                for (int i = 0; i<thongkeModel.getResult().size();i++){
                                    String tensp = thongkeModel.getResult().get(i).getName_product();
                                    int tong = thongkeModel.getResult().get(i).getTong();
                                    listdata.add(new PieEntry(tong,tensp));
                                }
                                PieDataSet pieDataSet = new PieDataSet(listdata,"");
                                PieData data = new PieData();
                                data.setDrawValues(true);
                                data.setDataSet(pieDataSet);
                                data.setValueTextSize(12f);
                                data.setValueFormatter(new PercentFormatter());
                                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                                data.setValueTextColor(Color.WHITE);
                                binding.piechartnhieunhat.setData(data);
                                binding.piechartnhieunhat.animateXY(3500,3500);
                                binding.piechartnhieunhat.setUsePercentValues(true);
                                binding.piechartnhieunhat.getDescription().setEnabled(false);
                                binding.piechartnhieunhat.invalidate();
                            }
                        },
                        throwable -> {
                            Log.d("Log",throwable.getMessage());
                        }
                ));
    }
    void GET_DANHSACH_NHIEUNHAT() {
        compositeDisposable.add(api_polysmall.GET_DANHSACH_NHIEUNHAT()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        danhsachModel ->{
                            if (danhsachModel.isSuccess()){
                                Array_danhsach = danhsachModel.getResult();
                                adapter = new Adapter_Danhsach(getApplicationContext(),Array_danhsach);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
                                binding.RecyclerViewpiechartnhieunhat.setLayoutManager(layoutManager);
                                binding.RecyclerViewpiechartnhieunhat.setAdapter(adapter);
                            }
                        },throwable ->{}
                ));
    }
    void GET_THONGKE_SANPHAM_BANIT() {
        List<PieEntry> listitnhat = new ArrayList<>();
        compositeDisposable.add(api_polysmall.GET_THONGKE_ITNHAT()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongkeModel -> {
                            if (thongkeModel.isSuccess()){
                                for (int i = 0; i<thongkeModel.getResult().size();i++){
                                    String tensp = thongkeModel.getResult().get(i).getName_product();
                                    int tong = thongkeModel.getResult().get(i).getTong();
                                    listitnhat.add(new PieEntry(tong,tensp));
                                }
                                PieDataSet pieDataSet_itnhat = new PieDataSet(listitnhat,"");
                                PieData data_itnhat = new PieData();
                                data_itnhat.setDataSet(pieDataSet_itnhat);
                                data_itnhat.setValueTextSize(12f);
                                data_itnhat.setDrawValues(true);
                                data_itnhat.setValueTextColor(Color.WHITE);
                                data_itnhat.setValueFormatter(new PercentFormatter());
                                pieDataSet_itnhat.setColors(ColorTemplate.MATERIAL_COLORS);
                                binding.piechartitnhat.setData(data_itnhat);
                                binding.piechartitnhat.animateXY(3500,3500);
                                binding.piechartitnhat.setUsePercentValues(true);
                                binding.piechartitnhat.getDescription().setEnabled(false);
                                binding.piechartitnhat.invalidate();
                            }
                        },
                        throwable -> {
                            Log.d("Log",throwable.getMessage());
                        }
                ));
    }
    void GET_DANHSACH_ITNHAT() {
        compositeDisposable.add(api_polysmall.GET_DANHSACH_ITNHAT()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        danhsachModel ->{
                            if (danhsachModel.isSuccess()){
                                Array_danhsach = danhsachModel.getResult();
                                adapter = new Adapter_Danhsach(getApplicationContext(),Array_danhsach);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
                                binding.RecyclerViewpiechartitnhat.setLayoutManager(layoutManager);
                                binding.RecyclerViewpiechartitnhat.setAdapter(adapter);
                            }
                        },throwable ->{}
                ));
    }

    void DOANHTHU(){
        getBarEntries();
        Doanhthu_pieEntry();
        getRecyDoanhthu();
        getdanhsachname();
        getSumTongtien();
        getVip();
    }

    void Doanhthu_pieEntry() {
        List<PieEntry> listDoanhthu = new ArrayList<>();
        compositeDisposable.add(api_polysmall.Doanhthu()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongkeModel -> {
                            if (thongkeModel.isSuccess()){
                                for (int i = 0; i<thongkeModel.getResult().size();i++){
                                    int tong = thongkeModel.getResult().get(i).getTong();
                                    listDoanhthu.add(new PieEntry(tong));
                                }
                                PieDataSet pieDataSet_itnhat = new PieDataSet(listDoanhthu,"");
                                PieData datadoanthu = new PieData();
                                datadoanthu.setDataSet(pieDataSet_itnhat);
                                datadoanthu.setDataSet(pieDataSet_itnhat);
                                datadoanthu.setValueTextSize(12f);
                                datadoanthu.setDrawValues(true);
                                datadoanthu.setValueTextColor(Color.WHITE);
                                datadoanthu.setValueFormatter(new PercentFormatter());
                                pieDataSet_itnhat.setColors(ColorTemplate.COLORFUL_COLORS);
                                binding.BarChartTinhphantram.setData(datadoanthu);
                                binding.BarChartTinhphantram.animateXY(3500,3500);
                                binding.BarChartTinhphantram.setUsePercentValues(true);
                                binding.BarChartTinhphantram.getDescription().setEnabled(false);
                                binding.BarChartTinhphantram.invalidate();
                            }
                        },
                        throwable -> {
                            Log.d("Log",throwable.getMessage());
                        }
                ));
    }
    private void getBarEntries() {
        List<BarEntry> barEntries = new ArrayList<>();
        compositeDisposable.add(api_polysmall.Doanhthu()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongkeModel -> {
                            if (thongkeModel.isSuccess()){
                                for (int i = 0; i<thongkeModel.getResult().size();i++){
                                    int tong = thongkeModel.getResult().get(i).getTong();
                                    barEntries.add(new BarEntry(i,tong));
                                }
                                BarDataSet barDataSet = new BarDataSet(barEntries,"Doanh thu hôm nay");
                                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                                barDataSet.setValueTextColor(Color.BLACK);
                                barDataSet.setValueTextSize(4f);
                                BarData barData = new BarData(barDataSet);
                                binding.idBarChart.setFitBars(true);
                                binding.idBarChart.setData(barData);
                                binding.idBarChart.animateY(3000);
                            }else {
                                Log.d("Log",thongkeModel.getMessage());
                            }
                        },
                        throwable -> {
                            Log.d("Log",throwable.getMessage());
                        }
                ));
    }
    void getRecyDoanhthu() {
        compositeDisposable.add(api_polysmall.Doanhthu()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongkeModel ->{
                            if (thongkeModel.isSuccess()){
                                arrayDoanhthu = thongkeModel.getResult();
                                adapterDoanhthu = new AdapterDoanhthu(getApplicationContext(),arrayDoanhthu);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
                                binding.RecyclerViewchitietDoanhthu.setLayoutManager(layoutManager);
                                binding.RecyclerViewchitietDoanhthu.setAdapter(adapterDoanhthu);
                            }
                        },throwable ->{}
                ));
    }
    void getdanhsachname() {
        compositeDisposable.add(api_polysmall.Doanhthu()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongkeModel ->{
                            if (thongkeModel.isSuccess()){
                                arrayDoanhthu = thongkeModel.getResult();
                                adapterDoanhthuDanhsach = new AdapterDoanhthuDanhsach(getApplicationContext(),arrayDoanhthu);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
                                binding.danhsachname.setLayoutManager(layoutManager);
                                binding.danhsachname.setAdapter(adapterDoanhthuDanhsach);
                            }
                        },throwable ->{}
                ));
    }
    void getSumTongtien() {
        compositeDisposable.add(api_polysmall.SUMTONGTIENDOANHTHU()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongkeModel ->{
                            if (thongkeModel.isSuccess()){
                                arrayDoanhthu = thongkeModel.getResult();
                                adapterTongdoanhthu = new AdapterTongdoanhthu(getApplicationContext(),arrayDoanhthu);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
                                binding.rcTong.setLayoutManager(layoutManager);
                                binding.rcTong.setAdapter(adapterTongdoanhthu);
                            }
                        },throwable ->{}
                ));
    }
    void getVip() {
        compositeDisposable.add(api_polysmall.SUMVIP()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongkeModel ->{
                            if (thongkeModel.isSuccess()){
                                arrayDoanhthu = thongkeModel.getResult();
                                adapterTongdoanhthu = new AdapterTongdoanhthu(getApplicationContext(),arrayDoanhthu);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
                                binding.rcTongVip.setLayoutManager(layoutManager);
                                binding.rcTongVip.setAdapter(adapterTongdoanhthu);
                            }
                        },throwable ->{}
                ));
    }
    void getSumTongtien2() {
        compositeDisposable.add(api_polysmall.SUMTONGTIENDOANHTHU()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongkeModel ->{
                            if (thongkeModel.isSuccess()){
                                arrayDoanhthu = thongkeModel.getResult();
                                adapterTongdoanhthu = new AdapterTongdoanhthu(getApplicationContext(),arrayDoanhthu);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
                                binding.rcTong2.setLayoutManager(layoutManager);
                                binding.rcTong2.setAdapter(adapterTongdoanhthu);
                            }
                        },throwable ->{}
                ));
    }
    void getListDoanthu() {
        compositeDisposable.add(api_polysmall.GETDANHSACHTHONGKETONG()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        danhsachModel ->{
                            if (danhsachModel.isSuccess()){
                                Array_danhsach = danhsachModel.getResult();
                                adapterDanhsachchitietDoanhthu = new AdapterDanhsachchitietDoanhthu(getApplicationContext(),Array_danhsach);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
                                binding.rcAllchitietdoanhthu.setLayoutManager(layoutManager);
                                binding.rcAllchitietdoanhthu.setAdapter(adapterDanhsachchitietDoanhthu);
                            }
                        },throwable ->{}
                ));
    }

}