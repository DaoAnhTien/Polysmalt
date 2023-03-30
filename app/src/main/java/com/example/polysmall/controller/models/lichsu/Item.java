package com.example.polysmall.controller.models.lichsu;

import java.util.Date;

public class Item {
    int idsp,soluong,gia;
    String name_product,imageview_product;
    Date ngaythanhtoan;


    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public Date getNgaythanhtoan() {
        return ngaythanhtoan;
    }

    public void setNgaythanhtoan(Date ngaythanhtoan) {
        this.ngaythanhtoan = ngaythanhtoan;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getImageview_product() {
        return imageview_product;
    }

    public void setImageview_product(String imageview_product) {
        this.imageview_product = imageview_product;
    }
}
