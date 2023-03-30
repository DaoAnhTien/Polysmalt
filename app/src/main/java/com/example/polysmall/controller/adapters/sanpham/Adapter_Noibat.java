package com.example.polysmall.controller.adapters.sanpham;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.polysmall.R;
import com.example.polysmall.controller.interfaces.ItemClickListener;
import com.example.polysmall.controller.models.GioHang;
import com.example.polysmall.controller.models.Sanpham;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.views.Chitiet_sanpham_Activity;

import java.text.DecimalFormat;
import java.util.List;

public class Adapter_Noibat extends RecyclerView.Adapter<Adapter_Noibat.MyviewHolder> {

    Context context;
    List<Sanpham> array;

    public Adapter_Noibat(Context context, List<Sanpham> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noibat,parent,false);
        return new MyviewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        Sanpham sanpham = array.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        if (sanpham.getImageview_product().contains("http")){
        Glide.with(context).load(sanpham.getImageview_product()).into(holder.image_noibat);
        }else {
            String hinh = Utils.BASE_URL+"images/"+sanpham.getImageview_product();
            Glide.with(context).load(hinh).into(holder.image_noibat);
        }
        holder.txt_nameproduc_noibat.setText(sanpham.getName_product());
        holder.txt_price_noibat.setText(decimalFormat.format(Double.parseDouble(sanpham.getPrice_product()))+" ₫");
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
        TextView txt_nameproduc_noibat,txt_price_noibat;
        ImageView image_noibat;
        private ItemClickListener itemClickListener;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nameproduc_noibat = itemView.findViewById(R.id.txt_nameproduc_noibat);
            txt_price_noibat = itemView.findViewById(R.id.txt_price_noibat);
            image_noibat = itemView.findViewById(R.id.image_noibat);

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

