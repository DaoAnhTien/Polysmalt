package com.example.polysmall.controller.adapters.lichsus;

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
import com.example.polysmall.controller.models.lichsu.Item;
import com.example.polysmall.controller.utils.Utils;
import com.example.polysmall.views.Chitiet_sanpham_Activity;

import java.text.DecimalFormat;
import java.util.List;

public class Adapter_item_LS_DH extends RecyclerView.Adapter<Adapter_item_LS_DH.MyViewHolder>{
    Context context;
    List<Item> array;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    public Adapter_item_LS_DH(Context context, List<Item> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lichsu_donhang_chitiet,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Adapter_item_LS_DH.MyViewHolder holder, int position) {
        Item item = array.get(position);
        holder.txtten.setText("Tên sản phẩm: "+item.getName_product()+"");
        holder.txtsoluong.setText("Số lượng: "+item.getSoluong()+"");
        holder.item_chitiet_tongtien.setText("Tổng tiền: "+decimalFormat.format(item.getGia())+" ₫");
        if (item.getImageview_product().contains("http")){
            Glide.with(context).load(item.getImageview_product()).into(holder.imagechitiet);
        }else {
            String hinh = Utils.BASE_URL+"images/"+item.getImageview_product();
            Glide.with(context).load(hinh).into(holder.imagechitiet);
        }
        if (Utils.user_current.getStatus()==1){
            holder.itemMualai.setVisibility(View.GONE);
            holder.itemMualai.setOnClickListener(view -> {
                Intent intent = new Intent(context, Chitiet_sanpham_Activity.class);
                intent.putExtra("chitiet", String.valueOf(item));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });
        }else {
            holder.itemMualai.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imagechitiet;
        TextView txtten,txtsoluong,item_chitiet_tongtien,itemMualai;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagechitiet = itemView.findViewById(R.id.item_chitiet_imageview);
            txtten = itemView.findViewById(R.id.item_chitiet_tensanpham);
            txtsoluong = itemView.findViewById(R.id.item_chitiet_soluong);
            item_chitiet_tongtien = itemView.findViewById(R.id.item_chitiet_tongtien);
            itemMualai = itemView.findViewById(R.id.itemMualai);
            itemMualai.setOnClickListener(this);
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
