package com.example.polysmall.controller.adapters.doanhthu;

import android.content.Context;
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

public class AdapterTongdoanhthu extends RecyclerView.Adapter<AdapterTongdoanhthu.MyviewHolder> {

    Context context;
    List<Thongke> arr;

    public AdapterTongdoanhthu(Context context, List<Thongke> arr) {
        this.context = context;
        this.arr = arr;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tongdoanhthu,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        Thongke thongke = arr.get(position);
        holder.item_tong .setText(decimalFormat.format(Integer.parseInt(String.valueOf(thongke.getTong()))));
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{
        TextView item_tong;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            item_tong = itemView.findViewById(R.id.item_tong);
        }
    }
}
