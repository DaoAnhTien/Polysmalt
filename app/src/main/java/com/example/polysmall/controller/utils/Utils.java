package com.example.polysmall.controller.utils;
import com.example.polysmall.controller.models.GioHang;
import com.example.polysmall.controller.models.Sanpham;
import com.example.polysmall.controller.models.User;
import com.example.polysmall.controller.models.lichsu.Lichsu;

import java.util.ArrayList;
import java.util.List;

public class Utils {
//    public static final String ID = "172.16.19.239";
//    public static final String BASE_URL ="http://"+ID+"/polysmart/";
    public static final String BASE_URL = "https://polysmart.000webhostapp.com/";
    public static User user_current = new User();
    public static Sanpham sanpham = new Sanpham()   ;
    public static List<GioHang> manggiohang = new ArrayList<>();
    public static List<GioHang> mangmuahang = new ArrayList<>();
}