package com.example.polysmall.controller.adapters.sanpham;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.polysmall.R;
import com.example.polysmall.controller.interfaces.ItemClickListener;
import com.example.polysmall.controller.models.Sanpham;
import com.example.polysmall.controller.models.model.SanphamModel;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.views.Chitiet_sanpham_Activity;

import java.text.DecimalFormat;
import java.util.List;

public class Adapter_Allproduct extends RecyclerView.Adapter<Adapter_Allproduct.MyviewHolder> {

    Context context;
    List<Sanpham> array;

    public Adapter_Allproduct(Context context, List<Sanpham> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allsanpham,parent,false);
        return new MyviewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        Sanpham sanpham = array.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        if (sanpham.getImageview_product().contains("http")){
            Glide.with(context).load(sanpham.getImageview_product()).into(holder.item_imageAll);
        }else {
            String hinh = Utils.BASE_URL+"images/"+sanpham.getImageview_product();
            Glide.with(context).load(hinh).into(holder.item_imageAll);
        }
        holder.item_tenAll.setText(sanpham.getName_product());
        holder.item_giaAll .setText(decimalFormat.format(Double.parseDouble(sanpham.getPrice_product()))+" ₫");


        holder.setItemClickListener((view, pos, isLongclick) -> {
            if (!isLongclick){
                Intent intent = new Intent(context, Chitiet_sanpham_Activity.class);
                intent.putExtra("chitiet",sanpham);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView item_tenAll,item_giaAll;
        ImageView item_imageAll;
        private ItemClickListener itemClickListener;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            item_tenAll = itemView.findViewById(R.id.item_tenAll);
            item_giaAll = itemView.findViewById(R.id.item_giaAll);
            item_imageAll = itemView.findViewById(R.id.item_imageAll);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);
        }
    }
}
