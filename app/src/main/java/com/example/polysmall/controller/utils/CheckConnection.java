package com.example.polysmall.controller.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class CheckConnection {
//    static  = co the truy cap nhieu man hinh
    public static boolean haveNetworkConnection(Context context){
        boolean haveConnectWifi = false;
        boolean haveConnectMobile = false;
        @SuppressLint("ServiceCast")
        ConnectivityManager cm  = (ConnectivityManager) context.getSystemService(Context.CONSUMER_IR_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo){
            if (ni.getTypeName().equalsIgnoreCase("WIFI"));
            if (ni.isConnected())
                haveConnectWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"));
            if (ni.isConnected())
                haveConnectMobile = true;
        }
        return haveConnectWifi || haveConnectMobile;
    }
    public static void showTost_Short(Context context,String thongbao){
        Toast.makeText(context,thongbao,Toast.LENGTH_SHORT).show();
    }

}
