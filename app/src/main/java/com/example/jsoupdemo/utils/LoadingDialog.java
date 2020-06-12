package com.example.jsoupdemo.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.jsoupdemo.R;

/**
 * Package    : com.example.myapplication.Dialog
 * ClassName  : PostScoreDialog
 * Description: ${TODO}
 * Date       : 2019/4/13 13:57
 */
public class LoadingDialog {

  private       TextView    textView;
  private final AlertDialog dialog;

  public LoadingDialog(Context context) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    View view = View.inflate(context, R.layout.dialog_loading, null);
    textView = view.findViewById(R.id.Dialog_Score);
    builder.setView(view);
    dialog = builder.create();
    dialog.setCancelable(true);
    dialog.setCanceledOnTouchOutside(false);
  }

  public void show() {
    if (textView == null || dialog == null)
      return;
    textView.setText("加载中，请稍后 ");
    dialog.show();
  }

  public void dismiss() {
    if (dialog != null)
      dialog.dismiss();
  }
}
