package com.example.jsoupdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jsoupdemo.utils.DataBean;
import com.example.jsoupdemo.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {
  private List<DataBean> mList;
  private Context        mContext;

  public MyAdapter(Context context) {
    mList = new ArrayList<>();
    mContext = context;
  }

  @Override
  public int getCount() {
    return mList.size();
  }

  @Override
  public DataBean getItem(int position) {
    return mList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    MyHolder vh = null;
    if (convertView == null) {
      vh = new MyHolder();
      convertView = View.inflate(mContext, R.layout.item, null);
      vh.tv_title = (TextView) convertView.findViewById(R.id.Item_Title);
      vh.tv_address = (TextView) convertView.findViewById(R.id.Item_Address);
      convertView.setTag(vh);
    } else {
      vh = (MyHolder) convertView.getTag();
    }

    vh.tv_title.setText(mList.get(position).getTitle());
    String address = mList.get(position).getAddress();
    //让历史和收藏共用一个适配器，如果没有地址就隐藏掉地址控件
    if (address == null || address.isEmpty()) {
      vh.tv_address.setVisibility(View.GONE);
    } else {
      vh.tv_address.setVisibility(View.VISIBLE);
      vh.tv_address.setText(mList.get(position).getAddress());
    }


    return convertView;
  }

  //刷新数据
  public void updateList(List<DataBean> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  //移除一条数据并刷新界面
  public void remove(int position) {
    mList.remove(position);
    notifyDataSetChanged();
  }

  private class MyHolder {
    TextView tv_title;
    TextView tv_address;

  }
}
