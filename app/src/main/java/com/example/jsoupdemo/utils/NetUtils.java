package com.example.jsoupdemo.utils;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Package    :com.example.jsoupdemo
 * ClassName  :NetUtils
 * Description:网络请求工具类
 * Data       :2020/6/11 10:49
 */
public class NetUtils {

  private static final int                 SUCCESS = 1;
  private static final int                 FAILURE = 2;
  private              OnSuccess           success;
  private              Map<String, String> map;
  //主线程回调
  private              Handler             handler = new Handler(new Handler.Callback() {
    @Override
    public boolean handleMessage(@NonNull Message msg) {
      if (msg.what == SUCCESS) {
        if (success != null)
          success.success((List<DataBean>) msg.obj);
      } else {
        Utils.showToast("网络请求失败，请稍后重试！");
      }
      return true;
    }
  });

  public NetUtils() {
    //初始化headers，模仿浏览器请求
    map = new HashMap<>();
    map.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
    map.put("Accept-Encoding", "gzip, deflate, sdch");
    map.put("Accept-Charset", "utf-8");
    map.put("Accept-Language", "zh-CN,zh;q=0.8");
    map.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
  }

  //子线程通过Jsoup读取安居客列表书库
  //key输入框输入内容
  //Jsoup使用教程：https://www.jianshu.com/p/fd5caaaa950d
  public void getData(final String key) {
    new Thread() {
      @Override
      public void run() {
        try {
          List<DataBean> list = new ArrayList<>();
          Document doc = Jsoup.connect("https://cd.zu.anjuke.com/?t=1&from=0&comm_exist=on&kw=" + key)
            .headers(map)
            .get();
          Element body = doc.body();
          Elements elementsByClass = body.select("div.zu-itemmod");
          for (int i = 0; i < elementsByClass.size(); i++) {
            if (i > 19)
              return;
            Element element = elementsByClass.get(i);
            String link = element.attr("link");
            String address = element.select("address.details-item").text();
            Elements select = element.select("b.strongbox");
            Log.e("tag", "详情地址：" + link);
            Log.e("tag", "标题：" + select.get(0).text());
            Log.e("tag", "地址：" + address);
            list.add(new DataBean(select.get(0).text(), address, link));
            //            for (int j = 0; j < select.size(); j++) {
            //              Log.e("tag", "具体信息：" + select.get(j).text());
            //            }

            Log.e("tag", "\n");
            handler.obtainMessage(SUCCESS, list).sendToTarget();
          }


        } catch (IOException e) {
          e.printStackTrace();
          handler.obtainMessage(FAILURE).sendToTarget();
        }
      }
    }.start();

  }

  public void setSuccess(OnSuccess success) {
    this.success = success;
  }


  public interface OnSuccess {
    void success(List<DataBean> list);
  }
}
