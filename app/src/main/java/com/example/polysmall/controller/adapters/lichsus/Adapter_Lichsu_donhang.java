package com.example.polysmall.controller.adapters.lichsus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polysmall.R;
import com.example.polysmall.controller.interfaces.ItemClickListener;
import com.example.polysmall.controller.models.Evenrbus.LichsuEvenrbus;
import com.example.polysmall.controller.models.User;
import com.example.polysmall.controller.models.lichsu.Lichsu;
import com.example.polysmall.controller.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

import io.paperdb.Paper;

public class Adapter_Lichsu_donhang extends RecyclerView.Adapter<Adapter_Lichsu_donhang.MyviewHolder>{

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<Lichsu> lichsuList;

    public Adapter_Lichsu_donhang(Context context, List<Lichsu> lichsuList) {
        this.context = context;
        this.lichsuList = lichsuList;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lichsu_donhang,parent,false);
            return new MyviewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Adapter_Lichsu_donhang.MyviewHolder holder, int position) {
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            Lichsu lichsu = lichsuList.get(position);
               holder.txtdonhang.setText("Đơn hàng: "+lichsu.getId()+"");
               holder.txttinhtrang.setText(trangthaiDon(lichsu.getTrangthai()));
               holder.thongtinUsername.setText("Họ và tên: "+lichsu.getHo()+" "+lichsu.getTen()+"");
               holder.thongtinSodienthoai.setText("Số điện thoại: "+lichsu.getMobile()+"");
               holder.diachigiaohang.setText("Địa chỉ giao hàng: "+lichsu.getDiachi()+"");
               if (Utils.user_current.getStatus()==0){
                   holder.txtTongtienthanhtoan.setText("Tổng tiền đơn hàng: "+decimalFormat.format(lichsu.getTongtien())+" ₫");
               }else {
                   holder.txtTongtienthanhtoan.setText("Tổng tiền đơn mua: "+decimalFormat.format(lichsu.getTongtien())+" ₫");
               }
               //thay doi mau trang thai don
               final Lichsu item = lichsuList.get(position);
               if (item.getTrangthai() == 0){
                   holder.txttinhtrang.setTextColor(Color.BLACK);
                   holder.Linearlayoutdonhanghuy.setVisibility(View.GONE);
                   holder.recyclerview_chitiet.setVisibility(View.VISIBLE);
               }else if (item.getTrangthai() == 1){
                   holder.txttinhtrang.setTextColor(Color.BLACK);
                   holder.Linearlayoutdonhanghuy.setVisibility(View.GONE);
                   holder.recyclerview_chitiet.setVisibility(View.VISIBLE);
               }else if (item.getTrangthai() == 2) {
                   holder.txttinhtrang.setTextColor(Color.BLACK);
                   holder.Linearlayoutdonhanghuy.setVisibility(View.GONE);
                   holder.recyclerview_chitiet.setVisibility(View.VISIBLE);
               }else if (item.getTrangthai() == 3) {
                   holder.txtdonhang.setTextColor(Color.BLUE);
                   holder.txttinhtrang.setTextColor(Color.BLUE);
                   holder.thongtinUsername.setTextColor(Color.BLUE);
                   holder.thongtinSodienthoai.setTextColor(Color.BLUE);
                   holder.diachigiaohang.setTextColor(Color.BLUE);
                   holder.titledathang.setTextColor(Color.BLUE);
                   holder.titlePhuongthuc.setTextColor(Color.BLUE);
                   holder.itemphuongthuc.setTextColor(Color.BLUE);
                   holder.Linearlayoutdonhanghuy.setVisibility(View.GONE);
                   holder.recyclerview_chitiet.setVisibility(View.VISIBLE);

               }else if (item.getTrangthai() == 4) {
                   holder.txttinhtrang.setTextColor(Color.RED);
                   holder.txtdonhang.setTextColor(Color.RED);
                   holder.txtTongtienthanhtoan.setTextColor(Color.RED);
                   holder.thongtinUsername.setTextColor(Color.RED);
                   holder.thongtinSodienthoai.setTextColor(Color.RED);
                   holder.diachigiaohang.setTextColor(Color.RED);
                   holder.titledathang.setTextColor(Color.RED);
                   holder.titlePhuongthuc.setTextColor(Color.RED);
                   holder.itemphuongthuc.setTextColor(Color.RED);
                   holder.Linearlayoutdonhanghuy.setVisibility(View.VISIBLE);
                   holder.recyclerview_chitiet.setVisibility(View.GONE);
                   holder.txtTongtienthanhtoan.setVisibility(View.GONE);
               }
               LinearLayoutManager layoutManager = new LinearLayoutManager(holder.recyclerview_chitiet.getContext(), LinearLayoutManager.VERTICAL,false);
               layoutManager.setInitialPrefetchItemCount(lichsu.getItem().size());
               // adapter chi tiét
               Adapter_item_LS_DH adapter_item_ls_dh = new Adapter_item_LS_DH(context,lichsu.getItem());
               holder.recyclerview_chitiet.setLayoutManager(layoutManager);
               holder.recyclerview_chitiet.setAdapter(adapter_item_ls_dh);
               holder.recyclerview_chitiet.setRecycledViewPool(viewPool);
               // băt sự kiện click
               // post su kien qua xem don hang
            if (Utils.user_current.getStatus()==0){
                holder.setListener((view, pos, isLongclick) -> {
                    if (isLongclick){
                        EventBus.getDefault().postSticky(new LichsuEvenrbus(lichsu));
                    }
                });
            }else {
                if (item.getTrangthai()==0){
                    holder.setListener((view, pos, isLongclick) -> {
                        if (isLongclick){
                            EventBus.getDefault().postSticky(new LichsuEvenrbus(lichsu));
                        }
                    });
                }else {
                    holder.setListener((view, pos, isLongclick) -> {
                        if (isLongclick){
                            Log.d("Donhang","Khong the huy don hang");
                        }
                    });
                    return;
                }
            }
  }

    private String trangthaiDon(int status){
        String result= "";
        switch (status){
            case 0:
                result = "Chờ xác nhận";
                break;
            case 1:
                result = "Chờ lấy hàng";
                break;
            case 2:
                result = "Đang giao";
                break;
            case 3:
                result = "Đã giao";
                break;
            case 4:
                result = "Đã hủy";
                break;
        }
        return result;
    }

    @Override
    public int getItemCount() {
        return lichsuList.size();
    }
    public class MyviewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView txtdonhang,txttinhtrang,txtTongtienthanhtoan,thongtinUsername,thongtinSodienthoai,diachigiaohang,titlePhuongthuc,titledathang,itemphuongthuc;
        RecyclerView recyclerview_chitiet;
        LinearLayout itemlichsu_admin,Linearlayoutdonhanghuy;
        View itemViewAdmin;
        ItemClickListener listener;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            txtdonhang = itemView.findViewById(R.id.iddonhang);
            txttinhtrang = itemView.findViewById(R.id.txttinhtrang);
            txtTongtienthanhtoan = itemView.findViewById(R.id.txtTongtienthanhtoan);
            recyclerview_chitiet = itemView.findViewById(R.id.recyclerview_chitiet);
            itemlichsu_admin = itemView.findViewById(R.id.itemlichsu_admin);
            Linearlayoutdonhanghuy = itemView.findViewById(R.id.Linearlayoutdonhanghuy);
            itemViewAdmin = itemView.findViewById(R.id.itemViewAdmin);
            thongtinUsername = itemView.findViewById(R.id.thongtinUsername);
            thongtinSodienthoai = itemView.findViewById(R.id.thongtinSodienthoai);
            diachigiaohang = itemView.findViewById(R.id.diachigiaohang);
            titlePhuongthuc = itemView.findViewById(R.id.titlePhuongthuc);
            titledathang = itemView.findViewById(R.id.titledathang);
            itemphuongthuc = itemView.findViewById(R.id.itemphuongthuc);
            itemView.setOnLongClickListener(this);
            if (Utils.user_current.getStatus()==1){
                itemlichsu_admin.setVisibility(View.GONE);
                itemViewAdmin.setVisibility(View.GONE);
            }else{
                itemlichsu_admin.setVisibility(View.VISIBLE);
                itemViewAdmin.setVisibility(View.VISIBLE);
            }
        }

        public void setListener(ItemClickListener listener) {
            this.listener = listener;
        }
        @Override
        public boolean onLongClick(View view) {
            listener.onClick(view,getAdapterPosition(),true);
            return false;
        }
    }
}
