package com.example.jsoupdemo.utils;

/*
 * Package    :com.example.jsoupdemo
 * ClassName  :DataBean
 * Description:具体房源信息
 * Data       :2020/6/11 14:26
 */
public class DataBean {
  private int    id;
  private String title;
  private String address;
  private String detailsUrl;

  public DataBean(int id, String title) {
    this.id = id;
    this.title = title;
  }

  public DataBean(int id, String title, String address, String detailsUrl) {
    this.id = id;
    this.title = title;
    this.address = address;
    this.detailsUrl = detailsUrl;
  }

  public DataBean(String title, String address, String detailsUrl) {
    this.title = title;
    this.address = address;
    this.detailsUrl = detailsUrl;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAddress() {
    return address;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getDetailsUrl() {
    return detailsUrl;
  }

  public void setDetailsUrl(String detailsUrl) {
    this.detailsUrl = detailsUrl;
  }
}
