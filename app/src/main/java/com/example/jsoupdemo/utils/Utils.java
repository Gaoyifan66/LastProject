package com.example.jsoupdemo.utils;

import android.widget.Toast;

import com.example.jsoupdemo.MyApplication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/*
 * Package    :com.example.jsoupdemo
 * ClassName  :Utils
 * Description:工具类
 * Data       :2020/6/11 11:01
 */
public class Utils {

  /**
   * InputStream转换String
   * @param inputStream
   * @return
   */
  public static String parsingInput(InputStream inputStream) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    try {
      byte[] bytes = new byte[1024];
      int len = 0;
      while ((len = inputStream.read(bytes)) != -1) {
        byteArrayOutputStream.write(bytes, 0, len);
      }
    } catch (IOException e) {

    }
    return byteArrayOutputStream.toString();
  }


  private static Toast toast = null;

  //弹出Toast提示
  public static void showToast(String msg) {
    if (toast == null)
      toast = Toast.makeText(MyApplication.mAppContext, msg, Toast.LENGTH_SHORT);
    else
      toast.setText(msg);
    toast.show();
  }

  public static void cancelToast() {
    if (toast != null)
      toast.cancel();
  }

}

