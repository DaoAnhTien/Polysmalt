package com.example.polysmall;

import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.polysmall.controller.adapters.Adapter_Danhmuc;
import com.example.polysmall.controller.adapters.sanpham.Adapter_Noibat;
import com.example.polysmall.controller.adapters.sanpham.Adapter_Sanpham;
import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.models.Danhmuc;
import com.example.polysmall.controller.models.GioHang;
import com.example.polysmall.controller.models.Sanpham;
import com.example.polysmall.controller.models.User;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.CheckConnection;
import com.example.polysmall.controller.utils.Utils;

import com.example.polysmall.databinding.ActivityMainBinding;
import com.example.polysmall.views.All_prduct_Activity;
import com.example.polysmall.views.Danhmuc.Danhmuc_Activity;
import com.example.polysmall.views.Giohang_Activity;
import com.example.polysmall.views.LichsumuahangActivity;
import com.example.polysmall.views.Login_Activity;
import com.example.polysmall.views.Thongtincanhan_Activity;
import com.example.polysmall.views.Timkiemsanpham_Activity;
import com.example.polysmall.views.Vohieuhoa.MokhoaActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends Base_Activity implements SwipeRefreshLayout.OnRefreshListener {
    private ActivityMainBinding binding;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String url = "http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png";
    private String url1 = "http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png";
    private String url2 = "http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-big-ky-nguyen-800-300.jpg";
//    private String
    Api_Polysmall api_polysmall;
    List<Danhmuc> Array_danhmuc;
    List<Sanpham > Array_sanpham;
    Adapter_Danhmuc adapter_danhmuc;
    Adapter_Sanpham adapter_sanpham;
    Adapter_Noibat adapter_noibat;
    Intent intent;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        // TAO PRIVATE
        API();
        initView();
        GET_EnvenClick();
        ActionBar();
        Giohang();
        ActionViewFlipper();
        PHANQUYENNAVI();
        _quangCaoMuaVip(Gravity.CENTER);
        trangthaiDangnhap();
        _maHoataikhoan();
        _refreshLayout();
    }
    void _maHoataikhoan() {
        if (Utils.user_current.getVohieuhoa()==0){
            binding.Mahoa0.setVisibility(View.VISIBLE);
            binding.Mahoa1.setVisibility(View.GONE);
            return;
        }else {
            binding.Mahoa0.setVisibility(View.GONE);
            binding.Mahoa1.setVisibility(View.VISIBLE);
        }
        DATA();
        binding.mokhoataikhoan.setOnClickListener(view -> {
            DIALOGMOKHOATAIKHOAN(Gravity.CENTER);
        });
        binding.btnDangxuat.setOnClickListener(view -> {
            // xóa key user và giỏ hàng
            Paper.book().delete("User.txt");
            Paper.book().delete("cart");
            navigate(Login_Activity.class);
            finish();
        });
    }

    void DIALOGMOKHOATAIKHOAN(int gravity) {
        boolean ischeck = false;
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogvohieuhoa);
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
            dialog.setCancelable(false);
        }
        // khai bao & anh xa
        Button dialog_btnHuy = dialog.findViewById(R.id.dialog_btnHuy);
        Button dialog_btndongy = dialog.findViewById(R.id.dialog_btndongy);
        ImageView dialog_imgCancel = dialog.findViewById(R.id.dialog_imgCancel);
        CheckBox dialogcheckbox = dialog.findViewById(R.id.dialogcheckbox);
        LinearLayout LinearLayoutCheck = dialog.findViewById(R.id.LinearLayoutCheck);

        dialogcheckbox.setOnClickListener(view -> {
            if (dialogcheckbox.isChecked()==ischeck){
                LinearLayoutCheck.setVisibility(View.GONE);
            }else {
                LinearLayoutCheck.setVisibility(View.VISIBLE);
            }
        });

        dialog_btnHuy.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog_imgCancel.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog_btndongy.setOnClickListener(view -> {
            navigate(MokhoaActivity.class);
            finish();
        });
        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    void DATA() {
        String ErrorData = null;
        binding.emailUser.setText("Email: "+Utils.user_current.getEmail()+"");
        binding.hoTenUser.setText("Họ và tên: "+Utils.user_current.getHo()+" "+Utils.user_current.getTen());
        if (Utils.user_current.getImage_user() == ErrorData){
            binding.imgUser.setImageResource(R.drawable.user);
        }else {
            if (Utils.user_current.getImage_user().contains("http")){
                Glide.with(getApplicationContext()).load(Utils.user_current.getImage_user()).into(binding.imgUser);
            }else {
                String hinh = Utils.BASE_URL+"images/"+Utils.user_current.getImage_user();
                Glide.with(getApplicationContext()).load(hinh).into(binding.imgUser);
            }
        }
        if (Utils.user_current.getMobile()==ErrorData){
            binding.soDienThoaiUser.setText(R.string.validate_sdt);
            binding.soDienThoaiUser.setTextColor(Color.RED);
        }else {
            binding.soDienThoaiUser.setText("Số điện thoại: "+Utils.user_current.getMobile());
        }
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
    void ActionViewFlipper() {
        Animation slide_in,slide_out;
        List<String>manquangcao = new ArrayList<>();
        manquangcao.add(url);
        manquangcao.add(url1);
        manquangcao.add(url2);
        for (int i = 0; i<manquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(manquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            binding.ViewFlipper.addView(imageView);
        }
        binding.ViewFlipper.setFlipInterval(3000);
        binding.ViewFlipper.setAutoStart(true);
        slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        binding.ViewFlipper.setInAnimation(slide_in);
        binding.ViewFlipper.setOutAnimation(slide_out);
    }

    void ActionBar() {
        setSupportActionBar(binding.Toolbar2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.Toolbar2.setNavigationIcon(R.drawable.menu);
        binding.Toolbar2.setNavigationOnClickListener(view -> {
            binding.DrawerLayout.openDrawer(GravityCompat.START);
        });
        binding.imgBack.setOnClickListener(view -> {
            binding.DrawerLayout.closeDrawer(GravityCompat.START);
        });

    }
    void initView() {
        binding.itemTimkiem.setOnClickListener(view -> {
            navigate(Timkiemsanpham_Activity.class);
            finish();
        });
    }

    void API() {
    api_polysmall = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_Polysmall.class);
    Array_danhmuc = new ArrayList<>();
    Array_sanpham = new ArrayList<>();
    GET_CATEGORY();
    GET_NOIBAT();
    GET_ALLSANPHAM();
    }

    void GET_EnvenClick(){
        binding.ListView.setOnItemClickListener((adapterView, view, i, l) -> {
            switch (i){
                case 0:
                    intent = new Intent(getApplicationContext(), Danhmuc_Activity.class);
                    intent.putExtra("id_category",1);
                    startActivity(intent);
                    break;
                case 1:
                        intent = new Intent(getApplicationContext(), Danhmuc_Activity.class);
                        intent.putExtra("id_category",2);
                        startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(getApplicationContext(), Danhmuc_Activity.class);
                    intent.putExtra("id_category",3);
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(getApplicationContext(), Danhmuc_Activity.class);
                    intent.putExtra("id_category",4);
                    startActivity(intent);
                    break;
                case 4:
                    intent = new Intent(getApplicationContext(), Danhmuc_Activity.class);
                    intent.putExtra("id_category",5);
                    startActivity(intent);
                    break;
                case 5:
                    intent = new Intent(getApplicationContext(), Danhmuc_Activity.class);
                    intent.putExtra("id_category",6);
                    startActivity(intent);
                    break;
                case 6:
                    intent = new Intent(getApplicationContext(), Danhmuc_Activity.class);
                    intent.putExtra("id_category",7);
                    startActivity(intent);
                    break;
                case 7:
                    intent = new Intent(getApplicationContext(), Danhmuc_Activity.class);
                    intent.putExtra("id_category",8);
                    startActivity(intent);
                    break;
                case 8:
                    intent = new Intent(getApplicationContext(), Danhmuc_Activity.class);
                    intent.putExtra("id_category",9);
                    startActivity(intent);
                    break;
                case 9:
                    intent = new Intent(getApplicationContext(), Danhmuc_Activity.class);
                    intent.putExtra("id_category",10);
                    startActivity(intent);
                    break;
            }
        });
    }


    void GET_CATEGORY() {
        compositeDisposable.add(api_polysmall.GET_DANHMUC()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    danhmucModel ->{
                        if (danhmucModel.isSuccess()){
                            Array_danhmuc = danhmucModel.getResult();
                            adapter_danhmuc = new Adapter_Danhmuc(Array_danhmuc,getApplicationContext());
                            binding.ListView.setAdapter(adapter_danhmuc);
                        }
                    },throwable ->{}
                ));
    }

    void GET_NOIBAT() {
        compositeDisposable.add(api_polysmall.GET_NOIBAT()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanphamModel ->{
                            if (sanphamModel.isSuccess()){
                                Array_sanpham = sanphamModel.getResult();
                                adapter_noibat = new Adapter_Noibat(getApplicationContext(),Array_sanpham);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
                                binding.RecyclerviewNoibat.setLayoutManager(layoutManager);
                                binding.RecyclerviewNoibat.setAdapter(adapter_noibat);
                            }
                        },throwable ->{}
                ));
    }

    void GET_ALLSANPHAM() {
        compositeDisposable.add(api_polysmall.GET_PRODUCT()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanphamModel ->{
                            if (sanphamModel.isSuccess()){
                                Array_sanpham = sanphamModel.getResult();
                                adapter_sanpham = new Adapter_Sanpham(getApplicationContext(),Array_sanpham);
                                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2); // set layout 2
                                binding.RecyclerviewSanphammoinhat.setLayoutManager(layoutManager);
                                binding.RecyclerviewSanphammoinhat.setAdapter(adapter_sanpham);
                            }
                        },throwable ->{}
                ));
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
        binding.navigationUser.setItemSelected(R.id.Home_activity,true);
        binding.navigationUser.setOnItemSelectedListener(i -> {
            switch (i){
            case R.id.All_product:{
                navigate(All_prduct_Activity.class);
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
        binding.navigationAdmin.setItemSelected(R.id.Home_activity,true);
        binding.navigationAdmin.setOnItemSelectedListener(i -> {
            switch (i){
                case R.id.All_product:{
                    navigate(All_prduct_Activity.class);
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

    void Giohang() {
        if (Utils.manggiohang == null){
            Utils.manggiohang = new ArrayList<>();
        }else {
            int totalItem = 0;
            for (int i = 0 ; i<Utils.manggiohang.size();i++){
                totalItem =  totalItem+Utils.manggiohang.get(i).getSoluong();
            }
            if(totalItem>0){
                binding.menusl.setText(String.valueOf(totalItem));
                binding.menusl.setVisibility(View.VISIBLE);
            }
        }

        binding.FrameLayoutGiohang.setOnClickListener(view -> {
            navigate(Giohang_Activity.class);
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

    void _quangCaoMuaVip(int gravity) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_quangcao);
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
        // khai bao & anh xa
        Button dialogmuasanpham = dialog.findViewById(R.id.dialogmuasanpham);
        // Delay 1s
        if (Utils.user_current.getVIP()==0){
            dialog.dismiss();
        }else {
           if (Utils.user_current.getVohieuhoa()==1){
               dialog.dismiss();
           }else {
               dialog.show();
           }
            dialogmuasanpham.setOnClickListener(view1 -> {
                navigate(All_prduct_Activity.class);
                finish();
            });
        }
    }

    void _refreshLayout() {
        binding.RefeshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        if (adapter_noibat==null){
            handler.postDelayed(() -> binding.RefeshLayout.setRefreshing(false),1500);
        }else if (adapter_danhmuc==null){
            handler.postDelayed(() -> binding.RefeshLayout.setRefreshing(false),1500);
        }else if (adapter_sanpham==null){
            handler.postDelayed(() -> binding.RefeshLayout.setRefreshing(false),1500);
        }else {
            GET_CATEGORY();
            GET_ALLSANPHAM();
            GET_NOIBAT();
            adapter_noibat.notifyDataSetChanged();
            adapter_danhmuc.notifyDataSetChanged();
            adapter_sanpham.notifyDataSetChanged();
            handler.postDelayed(() -> binding.RefeshLayout.setRefreshing(false),1500);
        }
    }
}