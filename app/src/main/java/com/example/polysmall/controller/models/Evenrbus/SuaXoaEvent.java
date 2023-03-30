package com.example.polysmall.controller.models.Evenrbus;

import com.example.polysmall.controller.models.Sanpham;

public class SuaXoaEvent {
    Sanpham sanpham;

    public SuaXoaEvent(Sanpham sanpham) {
        this.sanpham = sanpham;
    }

    public Sanpham getSanPham() {
        return sanpham;
    }

    public void setSanPhamMoi(Sanpham sanpham) {
        this.sanpham = sanpham;
    }
}
