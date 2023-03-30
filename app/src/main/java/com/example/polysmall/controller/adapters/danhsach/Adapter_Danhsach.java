package com.example.polysmall.controller.adapters.danhsach;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.polysmall.R;
import com.example.polysmall.controller.models.Danhsach;
import java.util.List;

public class Adapter_Danhsach extends RecyclerView.Adapter<Adapter_Danhsach.MyViewHolder>{
    Context context;
    List<Danhsach> list;

    public Adapter_Danhsach(Context context, List<Danhsach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danhsachtop,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Danhsach danhsach = list.get(position);
        holder.nametop.setText(danhsach.getName_product()+"");
        holder.tongtop.setText(danhsach.getTong()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nametop,tongtop;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nametop = itemView.findViewById(R.id.nametop);
            tongtop = itemView.findViewById(R.id.tongtop);
        }
    }
}
