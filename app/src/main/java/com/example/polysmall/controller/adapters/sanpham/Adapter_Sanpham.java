package com.example.polysmall.controller.adapters.sanpham;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.views.Chitiet_sanpham_Activity;

import java.text.DecimalFormat;
import java.util.List;

public class Adapter_Sanpham extends RecyclerView.Adapter<Adapter_Sanpham.MyviewHolder> {
    Context context;
    List<Sanpham> array;
    Intent intent;

    public Adapter_Sanpham(Context context, List<Sanpham> array) {
        this.context = context;
        this.array = array;
    }


    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham,parent,false);
        return new MyviewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        Sanpham sanpham = array.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        if (sanpham.getImageview_product().contains("http")){
        Glide.with(context).load(sanpham.getImageview_product()).into(holder.item_imagesanpham);
        }else {
            String hinh = Utils.BASE_URL+"images/"+sanpham.getImageview_product();
            Glide.with(context).load(hinh).into(holder.item_imagesanpham);
        }
        holder.item_tensanpham.setText(sanpham.getName_product());
        holder.item_giasanpham.setText(decimalFormat.format(Double.parseDouble(sanpham.getPrice_product()))+" ₫");

        holder.setItemClickListener((view, pos, isLongclick) -> {
            if (!isLongclick){
                     intent = new Intent(context, Chitiet_sanpham_Activity.class);
                     intent.putExtra("chitiet",sanpham);
                     intent.putExtra("id_category",1);
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
        TextView item_tensanpham,item_giasanpham;
        ImageView item_imagesanpham;
        private ItemClickListener itemClickListener;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            item_tensanpham = itemView.findViewById(R.id.item_tensanpham);
            item_giasanpham = itemView.findViewById(R.id.item_giasanpham);
            item_imagesanpham = itemView.findViewById(R.id.item_imagesanpham);
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
