package com.example.polysmall.controller.adapters.doanhthu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polysmall.R;
import com.example.polysmall.controller.models.Thongke;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterDoanhthu extends RecyclerView.Adapter<AdapterDoanhthu.MyviewHolder> {

    Context context;
    List<Thongke> arr;

    public AdapterDoanhthu(Context context, List<Thongke> arr) {
        this.context = context;
        this.arr = arr;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doanhthu,parent,false);
        return new MyviewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        Thongke thongke = arr.get(position);
        holder.nameDoanhthu.setText(thongke.getName_product()+"");
        holder.tongDoanhthu .setText(decimalFormat.format(Integer.parseInt(String.valueOf(thongke.getTong()))));
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{
        TextView nameDoanhthu,tongDoanhthu;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            nameDoanhthu = itemView.findViewById(R.id.nameDoanhthu);
            tongDoanhthu = itemView.findViewById(R.id.tongDoanhthu);
        }
    }
}
