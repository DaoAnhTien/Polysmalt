package com.example.polysmall.controller.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.polysmall.R;
import com.example.polysmall.controller.interfaces.ItemClickListener;
import com.example.polysmall.controller.models.Evenrbus.SuaXoaEvent;
import com.example.polysmall.controller.models.Evenrbus.UserEvenrbus;
import com.example.polysmall.controller.models.User;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.views.Chitiet_sanpham_Activity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class Adapter_khachhang extends RecyclerView.Adapter<Adapter_khachhang.MyviewHolder> {

    Context context;
    List<User>array;

    public Adapter_khachhang(Context context, List<User> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_khachhang,parent,false);
        return new MyviewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        User user = array.get(position);
        String Ernull = null;
        if (user.getMobile()==Ernull){
            holder.item_khachhang_imageview.setImageResource(R.drawable.user);
            holder.item_khachhang_hoten.setText(user.getHo()+" "+user.getTen());
            holder.item_khachhang_sodienthoai.setText("Khách hàng chưa cập nhật số điện thoại");
            holder.item_khachhang_sodienthoai.setTextColor(Color.RED);
            if (user.getVohieuhoa()==0){
                holder.vohieuhoa.setVisibility(View.GONE);
            }else {
                holder.vohieuhoa.setVisibility(View.VISIBLE);
            }
        }else if (user.getImage_user()==Ernull){
            holder.item_khachhang_imageview.setImageResource(R.drawable.user);
            holder.item_khachhang_hoten.setText(user.getHo()+" "+user.getTen());
            holder.item_khachhang_sodienthoai.setText("Khách hàng chưa cập nhật số điện thoại");
            holder.item_khachhang_sodienthoai.setTextColor(Color.RED);
            if (user.getVohieuhoa()==0){
                holder.vohieuhoa.setVisibility(View.GONE);
            }else {
                holder.vohieuhoa.setVisibility(View.VISIBLE);
            }
        }else {
            if (user.getImage_user().contains("http")){
                Glide.with(context).load(user.getImage_user()).into(holder.item_khachhang_imageview);
            }else {
                String hinh = Utils.BASE_URL+"images/"+user.getImage_user();
                Glide.with(context).load(hinh).into(holder.item_khachhang_imageview);
            }

        }
        holder.item_khachhang_hoten.setText(user.getHo()+" "+user.getTen());
        holder.item_khachhang_sodienthoai.setText(user.getMobile()+"");
        holder.item_khachhang_sodienthoai.setTextColor(Color.BLACK);
        if (user.getVohieuhoa()==0){
            holder.vohieuhoa.setVisibility(View.GONE);
        }else {
            holder.vohieuhoa.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (array != null){
            return array.size();
        }
        return 0;
    }

public class MyviewHolder extends RecyclerView.ViewHolder{
        TextView item_khachhang_hoten,item_khachhang_sodienthoai,item_vip,vohieuhoa;
        ImageView item_khachhang_imageview;
        FrameLayout FrameLayoutKhachhang;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            item_khachhang_hoten = itemView.findViewById(R.id.item_khachhang_hoten);
            item_khachhang_sodienthoai = itemView.findViewById(R.id.item_khachhang_sodienthoai);
            item_khachhang_imageview = itemView.findViewById(R.id.item_khachhang_imageview);
            item_vip = itemView.findViewById(R.id.item_vip);
            vohieuhoa = itemView.findViewById(R.id.vohieuhoa);
            FrameLayoutKhachhang = itemView.findViewById(R.id.FrameLayoutKhachhang);
        }
    }
}
