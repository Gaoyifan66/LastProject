package com.example.jsoupdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/*
 * Package    :com.example.jsoupdemo
 * ClassName  :DetailsActivity
 * Description:房源详情界面
 * Data       :2020/6/11 14:38
 */
public class DetailsActivity extends AppCompatActivity {
  private WebView     webView;
  private ProgressBar progressBar;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_details);
    setTitle("房源详情");
    webView = findViewById(R.id.Details_Web);
    progressBar = findViewById(R.id.Details_Loading);
    String url = getIntent().getStringExtra("url");
    webView.loadUrl(url);
    webView.getSettings().setJavaScriptEnabled(true);

    webView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
      }
    });

    webView.setWebChromeClient(new WebChromeClient() {

      @Override
      public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress == 100) {
          progressBar.setVisibility(View.GONE);//加载完网页进度条消失
        } else {
          progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
          progressBar.setProgress(newProgress);//设置进度值
        }
      }
    });
  }


  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      if (webView.canGoBack())
        webView.goBack();
      else
        return super.onKeyDown(keyCode, event);
    }
    return super.onKeyDown(keyCode, event);
  }
}
