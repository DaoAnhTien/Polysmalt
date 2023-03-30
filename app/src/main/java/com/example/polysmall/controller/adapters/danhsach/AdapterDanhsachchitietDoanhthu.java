package com.example.polysmall.controller.adapters.danhsach;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.polysmall.R;
import com.example.polysmall.controller.models.Danhsach;

import java.util.List;

public class AdapterDanhsachchitietDoanhthu extends RecyclerView.Adapter<AdapterDanhsachchitietDoanhthu.MyViewHolder>{
    Context context;
    List<Danhsach> list;

    public AdapterDanhsachchitietDoanhthu(Context context, List<Danhsach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doanhthu,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Danhsach danhsach = list.get(position);
        try {
            holder.nameDoanhthu.setText("Tên sản phẩm: "+danhsach.getName_product()+"");
            holder.tongDoanhthu.setText("Tổng tiền:"+danhsach.getTong()+"");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameDoanhthu,tongDoanhthu;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameDoanhthu = itemView.findViewById(R.id.nameDoanhthu);
            tongDoanhthu = itemView.findViewById(R.id.tongDoanhthu);
        }
    }
}
