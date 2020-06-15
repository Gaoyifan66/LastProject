package com.example.jsoupdemo.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jsoupdemo.utils.DataBean;

import java.util.ArrayList;
import java.util.List;

/*
 * Package    :com.example.jsoupdemo.sql
 * ClassName  :Sql
 * Description:数据库建表
 * Date       :2020/6/10 14:52
 */
//利用所学的SQLite知识建立数据库
//引用网络代码https://blog.csdn.net/android_zyf/article/details/53420267?utm_medium=distribute.pc_relevant.none-task-blog-baidujs-2
//在遍历数据库时引用有关Cursor光标的使用
public class Sql extends SQLiteOpenHelper {
  //类型-收藏
  public static final String COLLECT = "1";
  //类型-历史
  public static final String HISTORY = "2";


  public Sql(Context context) {
    super(context, "demo.db", null, 1);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("create table if not exists data(_id integer primary key autoincrement," +
      "title text,address text,url text,time long,type text)");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }


  /**
   * 新增一条收藏
   * @param title
   *   标题
   * @param address
   *   地址
   * @param url
   *   详情URL
   * @param type
   *   类型
   */
  public void addData(String title, String address, String url, String type) {
    if (isExists(title)) {//这条数据存在，刷新时间
      SQLiteDatabase writableDatabase = getWritableDatabase();
      writableDatabase.execSQL("update data set time=? where time=?", new String[]{System.currentTimeMillis() + "", title});
      writableDatabase.close();
    } else {//这条记录不存在，新增数据
      SQLiteDatabase writableDatabase = getWritableDatabase();
      writableDatabase.execSQL("insert into data(title,address,url,time,type) values(?,?,?,?,?)"
        , new String[]{title, address, url, System.currentTimeMillis() + "", type});
      writableDatabase.close();
    }
  }


  //判断一条数据是否存在，根据标题判断
  private boolean isExists(String title) {
    SQLiteDatabase readableDatabase = getReadableDatabase();
    Cursor cursor = readableDatabase.rawQuery("select * from data where title=?", new String[]{title});
    boolean b = cursor.moveToFirst();
    cursor.close();
    readableDatabase.close();
    return b;
  }

  //获取全部收藏
  public List<DataBean> getAllCollect() {
    SQLiteDatabase readableDatabase = getReadableDatabase();
    List<DataBean> list = new ArrayList<>();
    Cursor cursor = readableDatabase.rawQuery("select * from data where type = ? order by time desc", new String[]{COLLECT});
    while (cursor.moveToNext()) {
      int id = cursor.getInt(cursor.getColumnIndex("_id"));
      String title = cursor.getString(cursor.getColumnIndex("title"));
      String address = cursor.getString(cursor.getColumnIndex("address"));
      String url = cursor.getString(cursor.getColumnIndex("url"));
      list.add(new DataBean(id, title, address, url));
    }
    cursor.close();
    readableDatabase.close();
    return list;
  }

  //获取全部搜索历史
  public List<DataBean> getAllHistory() {
    SQLiteDatabase readableDatabase = getReadableDatabase();
    List<DataBean> list = new ArrayList<>();
    Cursor cursor = readableDatabase.rawQuery("select * from data where type = ? order by time desc", new String[]{HISTORY});
    while (cursor.moveToNext()) {
      int id = cursor.getInt(cursor.getColumnIndex("_id"));
      String title = cursor.getString(cursor.getColumnIndex("title"));
      list.add(new DataBean(id, title));
    }
    cursor.close();
    readableDatabase.close();
    return list;
  }

  //删除收藏或者搜索历史
  public void delete(int id) {
    SQLiteDatabase writableDatabase = getWritableDatabase();
    writableDatabase.execSQL("delete from data where _id=?", new String[]{id + ""});
    writableDatabase.close();
  }
}
