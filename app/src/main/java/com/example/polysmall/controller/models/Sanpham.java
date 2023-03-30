package com.example.polysmall.controller.models;

import java.io.Serializable;

public class Sanpham  implements Serializable {

    int id,id_category;
    String name_product,imageview_product,describe_product,price_product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public String getImageview_product() {
        return imageview_product;
    }

    public void setImageview_product(String imageview_product) {
        this.imageview_product = imageview_product;
    }

    public String getDescribe_product() {
        return describe_product;
    }

    public void setDescribe_product(String describe_product) {
        this.describe_product = describe_product;
    }

    public String getPrice_product() {
        return price_product;
    }

    public void setPrice_product(String price_product) {
        this.price_product = price_product;
    }
}
