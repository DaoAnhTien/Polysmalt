package com.example.polysmall.views;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.polysmall.MainActivity;
import com.example.polysmall.controller.adapters.Adapter_Timkiem;
import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.models.Sanpham;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.databinding.ActivityTimkiemsanphamBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Timkiemsanpham_Activity extends Base_Activity {

    private ActivityTimkiemsanphamBinding binding;
    Adapter_Timkiem adapter;
    List<Sanpham> sanphamList;
    Api_Polysmall api_polysmall;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTimkiemsanphamBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initView();
        TIMKIEM();
        GET_ALLSANPHAM();
        binding.restetform.setOnClickListener(view1 -> {
            binding.edtTimkiem.setText("");
            binding.restetform.setVisibility(View.GONE);
        });
        OnchageText();
    }

    void OnchageText() {
        binding.edtTimkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.restetform.setVisibility(View.VISIBLE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void initView() {
        binding.imageBack.setOnClickListener(view -> {
            navigate(MainActivity.class);
            finish();
        });
    }
    void GET_ALLSANPHAM() {
        compositeDisposable.add(api_polysmall.GET_PRODUCT_TIMKIEM()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanphamModel ->{
                            if (sanphamModel.isSuccess()){
                                sanphamList = sanphamModel.getResult();
                                adapter = new Adapter_Timkiem(getApplicationContext(),sanphamList);
                                binding.RecyclerView.setHasFixedSize(true);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                                binding.RecyclerView.setLayoutManager(layoutManager);
                                binding.RecyclerView.setAdapter(adapter);
                            }
                        },throwable ->{}
                ));
    }
    private void TIMKIEM() {
        sanphamList = new ArrayList<>();
        api_polysmall = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_Polysmall.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.RecyclerView.setHasFixedSize(true);
        binding.RecyclerView.setLayoutManager(layoutManager);
        binding.edtTimkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()==0){
                    adapter = new Adapter_Timkiem(getApplicationContext(),sanphamList);
                    sanphamList.clear();
                    binding.RecyclerView.setAdapter(adapter);
                    GET_ALLSANPHAM();
                }else{
                    getDataSearch(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void getDataSearch(String s) {
        sanphamList.clear();
        compositeDisposable.add(api_polysmall.POST_TIMKIEM(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    sanphamModel ->{
                        if (sanphamModel.isSuccess()){
                            sanphamList = sanphamModel.getResult();
                            adapter = new Adapter_Timkiem(getApplicationContext(),sanphamList);
                            binding.RecyclerView.setAdapter(adapter);
                        }

                    }, throwable -> {
                            showMsg(throwable.getMessage());
                    }
                )
        );
    }
}