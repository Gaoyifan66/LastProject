package com.example.jsoupdemo.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.jsoupdemo.MyApplication;


/**
 * 共享参数工具类
 */
public class SpUtils {

    @SuppressLint("ApplySharedPref")
    public static void putBoolean(String name, boolean msg) {
        SharedPreferences Sp = MyApplication.mAppContext.getSharedPreferences("Sp", Context.MODE_PRIVATE);
        Sp.edit().putBoolean(name, msg).commit();
    }

    public static boolean getBoolean(String name, boolean def) {
        SharedPreferences Sp = MyApplication.mAppContext.getSharedPreferences("Sp", Context.MODE_PRIVATE);
        return Sp.getBoolean(name, def);
    }

    @SuppressLint("ApplySharedPref")
    public static void putString(String name, String msg) {
        SharedPreferences Sp = MyApplication.mAppContext.getSharedPreferences("Sp", Context.MODE_PRIVATE);
        Sp.edit().putString(name, msg).commit();
    }

    public static String getString(String name, String def) {
        SharedPreferences Sp = MyApplication.mAppContext.getSharedPreferences("Sp", Context.MODE_PRIVATE);
        if (Sp.getString(name, def).isEmpty())
            return def;
        return Sp.getString(name, def);
    }

}
