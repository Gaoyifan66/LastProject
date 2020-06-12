package com.example.jsoupdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.jsoupdemo.adapter.MyAdapter;
import com.example.jsoupdemo.sql.Sql;

/*
 * Package    :com.example.jsoupdemo
 * ClassName  :CollectActivity
 * Description:收藏界面
 * Data       :2020/6/11 15:12
 */
public class CollectActivity extends AppCompatActivity {
  private ListView  lv;
  private MyAdapter mAdapter;
  private Sql       sql;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_collect);
    setTitle("我的收藏");
    lv = findViewById(R.id.lv);
    sql = new Sql(this);
    mAdapter = new MyAdapter(this);
    lv.setAdapter(mAdapter);
    mAdapter.updateList(sql.getAllCollect());

    //ListView单击进入详情
    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String url = mAdapter.getItem(position).getDetailsUrl();
        Intent intent = new Intent(CollectActivity.this, DetailsActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
      }
    });

    //ListView长按取消收藏
    lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
      @Override
      public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        showDeleteDialog(mAdapter.getItem(position).getId(), position);
        return true;
      }
    });
  }


  //弹出取消收藏提示框
  private void showDeleteDialog(final int id, final int position) {
    AlertDialog.Builder builder = new AlertDialog.Builder(CollectActivity.this);
    builder.setTitle("提示");
    builder.setMessage("是否取消收藏？");
    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        sql.delete(id);
        mAdapter.remove(position);
      }
    });
    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {

      }
    });
    builder.create().show();
  }
}
