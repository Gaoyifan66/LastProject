package com.example.jsoupdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.jsoupdemo.adapter.MyAdapter;
import com.example.jsoupdemo.sql.Sql;
import com.example.jsoupdemo.utils.DataBean;
import com.example.jsoupdemo.utils.LoadingDialog;
import com.example.jsoupdemo.utils.NetUtils;
import com.example.jsoupdemo.utils.Utils;

import java.util.List;

public class MainActivity extends AppCompatActivity {
  private EditText editText;
  private Button   btn_search, btn_collect, btn_history;
  private NetUtils      utils;
  private LoadingDialog loading;
  private ListView      lv;
  private MyAdapter     mAdapter;
  private Sql           sql;//类的变量

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setTitle("房源搜索");
    editText = findViewById(R.id.Main_Edit);
    btn_search = findViewById(R.id.Main_Search);
    btn_collect = findViewById(R.id.Main_Collect);
    btn_history = findViewById(R.id.Main_History);
    lv = findViewById(R.id.lv);
    sql = new Sql(this);
    loading = new LoadingDialog(this);
    utils = new NetUtils();
    mAdapter = new MyAdapter(this);
    lv.setAdapter(mAdapter);

    //搜索点击事件
    btn_search.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String msg = editText.getText().toString();
        if (!msg.isEmpty()) {
          loading.show();
          sql.addData(msg, "", "", Sql.HISTORY);
          utils.getData(msg);
        } else
          Utils.showToast("输入不能为空！");
      }
    });

    //搜索成功回调
    utils.setSuccess(new NetUtils.OnSuccess() {
      @Override
      public void success(List<DataBean> list) {
        //取消加载框并刷新列表
        loading.dismiss();
        mAdapter.updateList(list);
      }
    });


    //列表单击查看详情
    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String url = mAdapter.getItem(position).getDetailsUrl();
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
      }
    });

    //列表长按收藏
    lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
      @Override
      public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        DataBean item = mAdapter.getItem(position);
        sql.addData(item.getTitle(), item.getAddress(), item.getDetailsUrl(), Sql.COLLECT);
        Utils.showToast("收藏成功！");
        return true;
      }
    });

    //去我的收藏
    btn_collect.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, CollectActivity.class);
        startActivity(intent);
      }
    });

    //去我的搜索历史
    btn_history.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        //调用可以接受返回数据的跳转方法
        startActivityForResult(intent, 1);
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    //接受我的搜索历史点击返回数据
    if (requestCode == 1 && resultCode == 2) {
      editText.setText(data.getStringExtra("data"));
    }
  }

  //加载右上角菜单
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.info, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    //右上角点击事件
    if (item.getItemId() == R.id.MyInfo) {
      Intent intent = new Intent(MainActivity.this, InfoActivity.class);
      startActivity(intent);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
