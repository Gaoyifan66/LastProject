package com.example.jsoupdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.jsoupdemo.utils.SpUtils;
import com.example.jsoupdemo.utils.Utils;

/*
 * Package    :com.example.jsoupdemo
 * ClassName  :InfoActivity
 * Description:个人信息设置
 * Data       :2020/6/14 15:39
 */
public class InfoActivity extends AppCompatActivity {
  private EditText ed_money_start, ed_money_end, ed_address;
  private Spinner  spinner;
  private String[] args = new String[]{"单租", "整租"};
  private String   arg  = args[0];
  private Button   btn_clean, btn_save;
  private static final String ADDRESS = "address";
  private static final String START   = "start";
  private static final String END     = "end";
  private static final String WAY     = "way";

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_info);
    setTitle("个人信息");
    spinner = findViewById(R.id.Info_Way);
    ed_money_start = findViewById(R.id.Info_Money_Start);
    ed_money_end = findViewById(R.id.Info_Money_End);
    ed_address = findViewById(R.id.Info_Address);
    btn_clean = findViewById(R.id.Info_Clean);
    btn_save = findViewById(R.id.Info_Save);

    spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, args));
    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        arg = args[position];
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    ed_address.setText(SpUtils.getString(ADDRESS, ""));
    ed_money_start.setText(SpUtils.getString(START, ""));
    ed_money_end.setText(SpUtils.getString(END, ""));
    if (SpUtils.getString(WAY, "").equals(args[0]))
      spinner.setSelection(0);
    else
      spinner.setSelection(1);

    //清空点击事件
    btn_clean.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SpUtils.putString(ADDRESS, "");
        SpUtils.putString(START, "");
        SpUtils.putString(END, "");
        SpUtils.putString(WAY, args[0]);

        ed_address.setText("");
        ed_money_start.setText("");
        ed_money_end.setText("");
        spinner.setSelection(0);

      }
    });

    //保存
    btn_save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        String address = ed_address.getText().toString();
        String start = ed_money_start.getText().toString();
        String end = ed_money_end.getText().toString();
        if (address.isEmpty() || start.isEmpty() || end.isEmpty()) {
          Utils.showToast("输入不能为空！");
        } else {
          SpUtils.putString(ADDRESS, address);
          SpUtils.putString(START, start);
          SpUtils.putString(END, end);
          SpUtils.putString(WAY, arg);
          Utils.showToast("保存成功！");
        }
      }
    });
  }
}
