package com.example.polysmall.views.Danhmuc;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.polysmall.R;
import com.example.polysmall.controller.adapters.sanpham.Adapter_All_id;
import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.models.Sanpham;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.databinding.ActivityIphoneBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Danhmuc_Activity extends Base_Activity implements SwipeRefreshLayout.OnRefreshListener{

    private ActivityIphoneBinding binding;
    Api_Polysmall api_polysmall;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page = 1;
    int id_category;

    Adapter_All_id adapter;
    List<Sanpham> sanphamList;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIphoneBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        GET_API();
        getData(page);
        addEventLoad();
        ActionToolBar();
        _refreshLayout();
    }

    private void GET_API() {
        api_polysmall = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_Polysmall.class);
        id_category = getIntent().getIntExtra("id_category",1);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.RecyclerView.setLayoutManager(linearLayoutManager);
        binding.RecyclerView.setHasFixedSize(true);
        sanphamList = new ArrayList<>();
    }
    private void addEventLoad() {
        binding.RecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLoading == false){
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == sanphamList.size()-1){
                        isLoading = true;
                        loadMore();
                    }
                }
            }
        });
    }

    void loadMore() {
        handler.post(() -> {
            // add null
            sanphamList.add(null);
            adapter.notifyItemInserted(sanphamList.size()-1);
        });
        handler.postDelayed(() -> {
            // remover null
            sanphamList.remove(sanphamList.size()-1);
            adapter.notifyItemRemoved(sanphamList.size());
            page = page+1;
            getData(page);
            adapter.notifyDataSetChanged();
            isLoading = false;
        },1000);
    }
    void _refreshLayout() {
        binding.RefeshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        if (adapter==null){
            handler.postDelayed(() -> binding.RefeshLayout.setRefreshing(false),1500);
        }else {
            getData(page);
            adapter.notifyDataSetChanged();
            handler.postDelayed(() -> binding.RefeshLayout.setRefreshing(false),1500);
        }
    }
    private void getData(int page) {
        compositeDisposable.add(api_polysmall.GET_SANPHAM_ID(page,id_category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanphamModel -> {
                            if (sanphamModel.isSuccess()){
                                if (adapter == null){
                                    sanphamList = sanphamModel.getResult();
                                    adapter = new Adapter_All_id(getApplicationContext(),sanphamList);
                                    binding.RecyclerView.setAdapter(adapter);
                                }else {
                                    int vitri = sanphamList.size()-1;
                                    int soluongadd = sanphamModel.getResult().size();
                                    for (int i = 0 ; i<soluongadd; i++){
                                        sanphamList.add(sanphamModel.getResult().get(i));
                                    }
                                    adapter.notifyItemRangeInserted(vitri,soluongadd);
                                }
                            }
                        }, throwable -> {
                            Toast.makeText(getApplicationContext(),"không thể kết nối sever",Toast.LENGTH_LONG).show();

                        }
                ));
    }



    void ActionToolBar() {
        setSupportActionBar(binding.Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.Toolbar.setNavigationIcon(R.drawable.arrow_back_24);
        binding.Toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
       if (id_category == 2){
            binding.Toolbar.setTitle("SAMSUNG");
            Log.d("NUULL","NULL");
        }
        else  if (id_category == 3){
            binding.Toolbar.setTitle("OPPO");
        }
        else if (id_category == 4){
            binding.Toolbar.setTitle("XIAOMI");
        }else if (id_category == 5){
            binding.Toolbar.setTitle("VIVO");
        }else if (id_category == 6){
            binding.Toolbar.setTitle("realme");
        }else if (id_category == 7){
            binding.Toolbar.setTitle("NOKIA");
        }else if (id_category == 8){
            binding.Toolbar.setTitle("mobeli");
        }else if (id_category == 9){
            binding.Toolbar.setTitle("itei");
        }else if (id_category == 10){
            binding.Toolbar.setTitle("Masstel");
        }
        return;
    }

}