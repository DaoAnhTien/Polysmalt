package com.example.polysmall.controller.adapters.sanpham;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.polysmall.R;
import com.example.polysmall.controller.interfaces.ItemClickListener;
import com.example.polysmall.controller.models.Evenrbus.SuaXoaEvent;
import com.example.polysmall.controller.models.Sanpham;
import com.example.polysmall.controller.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class Adapter_quanly_sanpham extends RecyclerView.Adapter<Adapter_quanly_sanpham.MyviewHolder>{
    Context context;
    List<Sanpham> array;

    public Adapter_quanly_sanpham(Context context, List<Sanpham> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quanly_sanpham,parent,false);
        return new Adapter_quanly_sanpham.MyviewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        Sanpham sanpham = array.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
       if (sanpham == null){
           return;
       }else {
           if (sanpham.getImageview_product().contains("http")){
               Glide.with(context).load(sanpham.getImageview_product()).into(holder.item_quanlysp_imageview);
           }else {
               String hinh = Utils.BASE_URL+"images/"+sanpham.getImageview_product();
               Glide.with(context).load(hinh).into(holder.item_quanlysp_imageview);
           }
           holder.item_quanlysp_tensanpham.setText(sanpham.getName_product());
           holder.item_quanlysp_giasanpham.setText(decimalFormat.format(Double.parseDouble(sanpham.getPrice_product()))+" ₫");
           holder.setItemClickListener((view, pos, isLongclick) -> {
               if (!isLongclick){
                   Log.d("TAG_ID",sanpham.getId()+"");
                   Log.d("TAG_NAME",sanpham.getName_product());
               }else {
                   EventBus.getDefault().postSticky(new SuaXoaEvent(sanpham));
               }
           });
       }
    }

    @Override
    public int getItemCount() {
        if (array != null){
            return array.size();
        }
        return 0;
    }


    public class MyviewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnClickListener, View.OnLongClickListener {
        TextView item_quanlysp_tensanpham,item_quanlysp_giasanpham;
        ImageView item_quanlysp_imageview;

        private ItemClickListener itemClickListener;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            item_quanlysp_imageview = itemView.findViewById(R.id.item_quanlysp_imageview);
            item_quanlysp_tensanpham = itemView.findViewById(R.id.item_quanlysp_tensanpham);
            item_quanlysp_giasanpham = itemView.findViewById(R.id.item_quanlysp_giasanpham);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(0,0,getAdapterPosition(),"Cập nhật");
            contextMenu.add(0,1,getAdapterPosition(),"Xóa");
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), true);
            return false;
        }
    }
}
