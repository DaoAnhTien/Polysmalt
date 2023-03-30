package com.example.polysmall.controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.polysmall.R;
import com.example.polysmall.controller.models.Danhmuc;

import java.util.List;

public class Adapter_Danhmuc extends BaseAdapter {
    List<Danhmuc> array;
    Context context;

    public Adapter_Danhmuc(List<Danhmuc> array, Context context) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
        return array.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        TextView txt_danhmuc;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_danhmuc,null);

            viewHolder.txt_danhmuc = view.findViewById(R.id.category_name);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
    }
        viewHolder.txt_danhmuc.setText(array.get(i).getName_category());
        return view;
    }
}
