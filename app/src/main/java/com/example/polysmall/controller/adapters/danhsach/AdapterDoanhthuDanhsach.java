package com.example.polysmall.controller.adapters.danhsach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polysmall.R;
import com.example.polysmall.controller.models.Thongke;

import java.util.List;

public class AdapterDoanhthuDanhsach extends RecyclerView.Adapter<AdapterDoanhthuDanhsach.MyviewHolder> {

    Context context;
    List<Thongke> arr;

    public AdapterDoanhthuDanhsach(Context context, List<Thongke> arr) {
        this.context = context;
        this.arr = arr;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_namedoanhthu,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        Thongke thongke = arr.get(position);
        holder.nameDoanhthutow.setText(thongke.getName_product()+"");
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{
        TextView nameDoanhthutow;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            nameDoanhthutow = itemView.findViewById(R.id.nameDoanhthutow);
        }
    }
}
