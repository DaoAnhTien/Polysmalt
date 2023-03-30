package com.example.polysmall.controller.adapters;

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

public class Adapter_Timkiem extends RecyclerView.Adapter<Adapter_Timkiem.MyviewHolder> {

    Context context;
    List<Sanpham> array;

    public Adapter_Timkiem(Context context, List<Sanpham> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timkiem,parent,false);
        return new Adapter_Timkiem.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        Sanpham sanpham = array.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        if (sanpham.getImageview_product().contains("http")){
            Glide.with(context).load(sanpham.getImageview_product()).into(holder.item_timkiem_img);
        }else {
            String hinh = Utils.BASE_URL+"images/"+sanpham.getImageview_product();
            Glide.with(context).load(hinh).into(holder.item_timkiem_img);
        }
        holder.item_timkiem_name.setText(sanpham.getName_product());
        holder.item_timkiem_gia.setText(decimalFormat.format(Double.parseDouble(sanpham.getPrice_product()))+" ₫");

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
        ImageView item_timkiem_img;
        TextView item_timkiem_name,item_timkiem_gia;
        private ItemClickListener itemClickListener;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            item_timkiem_img = itemView.findViewById(R.id.item_timkiem_img);
            item_timkiem_name = itemView.findViewById(R.id.item_timkiem_name);
            item_timkiem_gia = itemView.findViewById(R.id.item_timkiem_gia);
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
