package com.example.polysmall.controller.adapters.sanpham;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class Adapter_All_id extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

    Context context;
    List<Sanpham>array;

    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    boolean isloading;
    public Adapter_All_id(Context context, List<Sanpham> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DATA){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham_id,parent,false);
            return new MyViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_product,parent,false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            Sanpham sanpham = array.get(position);
            myViewHolder.item_sanpham_name.setText(sanpham.getName_product().trim());
            myViewHolder.item_sanpham_gia.setText(decimalFormat.format(Double.parseDouble(sanpham.getPrice_product()))+" ₫");
            myViewHolder.item_sanpham_mota.setText(sanpham.getDescribe_product());
            if (sanpham.getImageview_product().contains("http")){
                Glide.with(context).load(sanpham.getImageview_product()).into(((MyViewHolder) holder).item_sanpham_img);
            }else {
                String hinh = Utils.BASE_URL+"images/"+sanpham.getImageview_product();
                Glide.with(context).load(hinh).into(((MyViewHolder) holder).item_sanpham_img);
            }
            myViewHolder.setItemClickListener((view, pos, isLongclick) -> {
                if (!isLongclick){
                    // click
                    Intent intent = new Intent(context, Chitiet_sanpham_Activity.class);
                    intent.putExtra("chitiet",sanpham);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }else {
            if (isloading==true){
                LoadingViewHolder loadingViewHolder  = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return array.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        return array.size();
    }
    public class  LoadingViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
            if (isloading==true){
                progressBar.setVisibility(View.VISIBLE);
            }else {
                progressBar.setVisibility(View.GONE);
            }
        }
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_sanpham_img;
        TextView item_sanpham_name,item_sanpham_gia,item_sanpham_mota;
        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_sanpham_img = itemView.findViewById(R.id.item_sanpham_img);
            item_sanpham_name = itemView.findViewById(R.id.item_sanpham_name);
            item_sanpham_gia = itemView.findViewById(R.id.item_sanpham_gia);
            item_sanpham_mota = itemView.findViewById(R.id.item_sanpham_mota);
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
