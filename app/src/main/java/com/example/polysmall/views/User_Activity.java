package com.example.polysmall.views;


import static android.net.Uri.parse;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.polysmall.R;
import com.example.polysmall.controller.adapters.base.Base_Activity;
import com.example.polysmall.controller.models.User;
import com.example.polysmall.controller.models.model.MessageModel;
import com.example.polysmall.controller.retrofit.RetrofitClient;
import com.example.polysmall.controller.utils.Api_Polysmall;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.databinding.ActivityUserBinding;
import com.example.polysmall.views.user.Setting_Activity;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Activity extends Base_Activity {
    private ActivityUserBinding binding;
    Api_Polysmall api_polysmall;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    String imageview = null;
    String mediaPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ActionToolBar();
        API();
        trangthaiDangnhap();
        UpdateProfile();
        DataUser();
        OnchangText();
    }

    void OnchangText() {
        binding.edtHo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.notificationErr.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        binding.edtTen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.notificationErr.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        binding.edtSodienthoai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.notificationErr.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        binding.edtDiachi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.notificationErr.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    void DataUser() {
        // show data
        binding.edtHo.setText(Utils.user_current.getHo());
        binding.edtTen.setText(Utils.user_current.getTen());
        binding.edtDiachi.setText(Utils.user_current.getDiachi());
        binding.edtSodienthoai.setText(Utils.user_current.getMobile());
        binding.txtHinhanh.setText(Utils.user_current.getImage_user());
        if (Utils.user_current.getImage_user() == imageview){
            binding.imgUser.setImageResource(R.drawable.user);
        }else {
            if (Utils.user_current.getImage_user().contains("http")){
                Glide.with(getApplicationContext()).load(Utils.user_current.getImage_user()).into(binding.imgUser);
            }else {
                String hinh = Utils.BASE_URL+"images/"+Utils.user_current.getImage_user();
                Glide.with(getApplicationContext()).load(hinh).into(binding.imgUser);
            }
        }
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
    }

    void ActionToolBar() {
        setSupportActionBar(binding.Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.Toolbar.setNavigationIcon(R.drawable.arrow_back_24);
        binding.Toolbar.setNavigationOnClickListener(view -> {
            navigate(Setting_Activity.class);
            finish();
        });
    }
    void UpdateProfile() {
        binding.btnThaydoi.setOnClickListener(view -> {
            UpdateUser();
        });
        binding.CameraUser.setOnClickListener(view -> {
            CAMERA_UPLOAD();
        });
    }
    void UpdateUser() {
        String SDT = "^(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})$";
        String ho = binding.edtHo.getText().toString().trim();
        String ten = binding.edtTen.getText().toString().trim();
        String diachi = binding.edtDiachi.getText().toString().trim();
        String mobile = binding.edtSodienthoai.getText().toString().trim();
        String image_user = binding.txtHinhanh.getText().toString().trim();
        if (TextUtils.isEmpty(ho)||TextUtils.isEmpty(ten)||TextUtils.isEmpty(diachi)||TextUtils.isEmpty(mobile)){
            binding.notificationErr.setVisibility(View.VISIBLE);
        }else if(TextUtils.isEmpty(image_user)){
            binding.notificationErr.setText("Vui lòng chọn ảnh đại diện");
            binding.notificationErr.setVisibility(View.VISIBLE);
        }else if(!mobile.matches(SDT)){
            binding.notificationErr.setText("Số điện thoại không hợp lệ");
            binding.notificationErr.setVisibility(View.VISIBLE);
        }else {
            compositeDisposable.add(api_polysmall.UPDATEPROFILE(ho,ten,diachi,mobile,image_user,Utils.user_current.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel ->{
                                if (messageModel.isSuccess()){
                                    VALIDATE_USER();
                                    Validatator(Gravity.CENTER);
                                }else {
                                    binding.notificationErr.setText(messageModel.getMessage());
                                    binding.notificationErr.setVisibility(View.VISIBLE);
                                }
                            },throwable -> {showMsg(throwable.getMessage());}
                    ));
        }
    }
    void CAMERA_UPLOAD() {
        try {
            ImagePicker.with(this).crop().compress(1024).maxResultSize(1080,1080). start();
        }catch (Exception e){
            e.printStackTrace();
        }
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
                binding.imgUser.setImageURI(parse(mediaPath));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
            Call<MessageModel> call = api_polysmall.UPLOAD_FILE_USER(fileToUpload1);
            call.enqueue(new Callback<MessageModel>() {
                @Override
                public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                    MessageModel serverResponse = response.body();
                    if (serverResponse != null) {
                        if (serverResponse.isSuccess()) {
                            binding.txtHinhanh.setText(serverResponse.getName());
                        }else {}
                    } else {}
                }
                @Override
                public void onFailure(Call<MessageModel> call, Throwable t) {}
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void VALIDATE_USER() {
        if (Utils.user_current.getImage_user() == imageview){
            binding.imgUser.setImageResource(R.drawable.user);
        }else {
            if (Utils.user_current.getImage_user().contains("http")){
                Glide.with(getApplicationContext()).load(Utils.user_current.getImage_user()).into(binding.imgUser);
            }else {
                String hinh = Utils.BASE_URL+"images/"+Utils.user_current.getImage_user();
                Glide.with(getApplicationContext()).load(hinh).into(binding.imgUser);
            }
        }
        DataUser();
    }
    void Validatator(int gravity) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogerror);
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
        ImageView dialog_imgCancel = dialog.findViewById(R.id.dialog_imgCancel);
        ImageView dialog_Img = dialog.findViewById(R.id.dialog_Img);
        Button dialog_btnHuy = dialog.findViewById(R.id.dialog_btnHuy);
        Button dialog_btnUpdate = dialog.findViewById(R.id.dialog_btnUpdate);
        TextView dialog_title = dialog.findViewById(R.id.dialog_title);
        TextView dialog_txtnotifi = dialog.findViewById(R.id.dialog_txtnotifi);
        // update text
        dialog_Img.setImageResource(R.drawable.thanhcong);
        dialog_title.setText("Thay đổi thông tin thành công");
        dialog_txtnotifi.setText("Bạn có muốn đăng xuất không ? \n cập nhật lại tất cả thông tin.");
        dialog_txtnotifi.setTextColor(Color.BLACK);
        dialog_btnHuy.setText("Hủy");
        dialog_btnUpdate.setText("xác nhận");
        // Delay 1s
        dialog_imgCancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog_btnHuy.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog_btnUpdate.setOnClickListener(view -> {
            navigate(Login_Activity.class);
            finish();
        });
        dialog.show();
    }
}