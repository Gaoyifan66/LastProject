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
 * Description:历史界面
 * Data       :2020/6/11 15:12
 */
public class HistoryActivity extends AppCompatActivity {
  private ListView  lv;
  private MyAdapter mAdapter;
  private Sql       sql;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_collect);
    setTitle("搜索历史");
    lv = findViewById(R.id.lv);
    sql = new Sql(this);
    mAdapter = new MyAdapter(this);
    lv.setAdapter(mAdapter);
    mAdapter.updateList(sql.getAllHistory());

    //ListView单击返回搜索
    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("data", mAdapter.getItem(position).getTitle());
        setResult(2, intent);
        finish();
      }
    });

    //ListView长按删除历史记录
    lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
      @Override
      public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        showDeleteDialog(mAdapter.getItem(position).getId(), position);
        return true;
      }
    });
  }


  //弹出删除历史记录提示框
  private void showDeleteDialog(final int id, final int position) {
    AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
    builder.setTitle("提示");
    builder.setMessage("是否删除历史记录？");
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
