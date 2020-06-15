package com.example.jsoupdemo;

import android.app.Application;
import android.content.Context;

/**
 * application
 */
public class MyApplication extends Application {
    //保存该application的context，定义变量mAppContext
    public static Context  mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;//将当前对象赋给它
    }
}
//context是安卓程序运行的一个核心功能类，作用类似于工作环境
//代码借鉴https://blog.csdn.net/qq_31608451/article/details/78802391