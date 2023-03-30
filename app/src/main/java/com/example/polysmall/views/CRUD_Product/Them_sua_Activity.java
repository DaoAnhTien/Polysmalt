package com.example.polysmall.views.CRUD_Product;

import static android.net.Uri.parse;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.polysmall.R;
import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.models.Sanpham;
import com.example.polysmall.controller.models.model.MessageModel;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.databinding.ActivityThemSuaBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Them_sua_Activity extends Base_Activity {
    private ActivityThemSuaBinding binding;
    Api_Polysmall api_polysmall;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int loaisanpham = 0;
    String mediaPath;
    Sanpham sanpham_update;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThemSuaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initData();
        initView();
        ActionToolBar();
        CAMERA();
        Intent intent = getIntent();
        sanpham_update = (Sanpham) intent.getSerializableExtra("Cập nhật");
        if (sanpham_update == null){
            // them moi
            flag = false;
            binding.btnThemSua.setBackgroundResource(R.drawable.background_btn);
        }else {
            // cap nhat
            flag = true;
            // show hint && button thay doi
            binding.Toolbar.setTitle("Cập nhật sản phẩm");
            binding.btnThemSua.setText("Cập nhật");
            binding.btnThemSua.setBackgroundResource(R.drawable.background_logout);
            // show data
            binding.inputName.setText(sanpham_update.getName_product());
            binding.inputGia.setText(sanpham_update.getPrice_product()+"");
            binding.txtHinhanh.setText(sanpham_update.getImageview_product());
            if (sanpham_update.getImageview_product().contains("http")){
                Glide.with(getApplicationContext()).load(sanpham_update.getImageview_product()).into(binding.cameraHinhanh);
            }else {
                String hinh = Utils.BASE_URL+"images/"+sanpham_update.getImageview_product();
                Glide.with(getApplicationContext()).load(hinh).into(binding.cameraHinhanh);
            }
            binding.inputMota.setText(sanpham_update.getDescribe_product());
            // show spiner
            binding.spDanhmuc.setSelection(sanpham_update.getId_category());
        }
    }

    void initData() {
        SPINNER();
        BUTTON();
    }

    void SPINNER() {
        List<String> stringList = new ArrayList<>();
        stringList.add("Vui lòng chọn loại danh mục");
        stringList.add("IPHONE"); stringList.add("SAMSUNG");stringList.add("OPPO");stringList.add("XIAOMI"); stringList.add("VIVO");
        stringList.add("realme"); stringList.add("NOKIA"); stringList.add("mobell");stringList.add("Itel");stringList.add("Masstel");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,stringList);
        binding.spDanhmuc.setAdapter(adapter);
        binding.spDanhmuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loaisanpham = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    void BUTTON() {
        binding.btnThemSua.setOnClickListener(view -> {
            if (flag == false){
                ADD_SANPHAM();
            }else {
                UPDATE_SANPHAM();
            }
        });
    }

    void ADD_SANPHAM(){
        String str_ten = binding.inputName.getText().toString().trim();
        String str_gia = binding.inputGia.getText().toString().trim();
        String str_hinhanh = binding.txtHinhanh.getText().toString().trim();
        String str_mota = binding.inputMota.getText().toString().trim();
        if (TextUtils.isEmpty(str_ten)||TextUtils.isEmpty(str_gia)||TextUtils.isEmpty(str_mota)){
            showMsg("Vui lòng nhập đầy đủ thông tin");
        }else if (TextUtils.isEmpty(str_hinhanh)){
            binding.namehinhAnh.setTextColor(Color.RED);
        }else if (loaisanpham==0) {
            showMsg("Vui lòng nhập chọn loại sản phẩm");
        }else {
                compositeDisposable.add(api_polysmall.ADD_SANPHAM(str_ten,str_gia,str_hinhanh,str_mota,(loaisanpham))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                messageModel ->{
                                    if (messageModel.isSuccess()){
                                        navigate(Hiensanpham_Activity.class);
                                        finish();
                                    }else {
                                        showMsg(messageModel.getMessage());
                                    }
                                },throwable -> {
                                    showMsg(throwable.getMessage());
                                }
                        ));

            }
        }

    void UPDATE_SANPHAM() {
        String str_ten = binding.inputName.getText().toString().trim();
        String str_gia = binding.inputGia.getText().toString().trim();
        String str_hinhanh = binding.txtHinhanh.getText().toString().trim();
        String str_mota = binding.inputMota.getText().toString().trim();
        if (TextUtils.isEmpty(str_ten)||TextUtils.isEmpty(str_gia)||TextUtils.isEmpty(str_mota)|| loaisanpham== 0){
            showMsg("Vui lòng nhập đầy đủ thông tin");
        }else if (TextUtils.isEmpty(str_hinhanh)){
            binding.namehinhAnh.setTextColor(Color.RED);
//            showMsg("Vui lòng nhập chọn hình ảnh sản phẩm");
        }else if (loaisanpham==0) {
            showMsg("Vui lòng nhập chọn loại sản phẩm");
        }else {
            compositeDisposable.add(api_polysmall.UPDATE_SANPHAM(str_ten,str_gia,str_hinhanh,str_mota,loaisanpham, sanpham_update.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel ->{
                                if (messageModel.isSuccess()){
                                    navigate(Hiensanpham_Activity.class);
                                    finish();
                                }else {
                                    showMsg(messageModel.getMessage());
                                }
                            },throwable -> {
                                showMsg(throwable.getMessage());
                            }
                    ));
        }
    }
    @SuppressLint("ResourceType")
    void CAMERA() {
        binding.cameraHinhanh.setOnClickListener(view -> {
            binding.namehinhAnh.setTextColor(Color.BLACK);
            try {
                ImagePicker.with(Them_sua_Activity.this).crop().compress(1024).maxResultSize(1080,1080). start();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mediaPath = data.getDataString();
        uploadMultipleFiles();
        try {
            if (mediaPath == null){
                return;
            }else {
                binding.cameraHinhanh.setImageURI(Uri.parse(mediaPath));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("TAG2","onActivityResult"+mediaPath);
    }
    // upload hình ảnh
    private String getPath(Uri uri) {
        String result;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }

    void uploadMultipleFiles(){
        try {
            File file;
            Uri uri;
            uri = parse(mediaPath);
            file = new File(getPath(uri));
            RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("file", file.getName(), requestBody1);
            Call<MessageModel> call = api_polysmall.uploadFile(fileToUpload1);
            call.enqueue(new Callback<MessageModel>() {
                @Override
                public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                    MessageModel serverResponse = response.body();
                    if (serverResponse != null) {
                        if (serverResponse.isSuccess()) {
//                            showMsg("Thành Công");
                            Log.d("IMG","TRUE");
                            binding.txtHinhanh.setText(serverResponse.getName());

                        } else {
                            Log.d("IMG","FALSE");
                        }
                    } else {
                        Log.v("Response", serverResponse.toString());
                    }
                }
                @Override
                public void onFailure(Call<MessageModel> call, Throwable t) {
                    Log.d("TAG", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TAG", "lỗi camera");
        }
    }

    void ActionToolBar() {
        setSupportActionBar(binding.Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.Toolbar.setNavigationIcon(R.drawable.arrow_back_24);
        binding.Toolbar.setNavigationOnClickListener(view -> {
            navigate(Hiensanpham_Activity.class);
            finish();
        });
    }
    void initView() {
        api_polysmall = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_Polysmall.class);
    }
}