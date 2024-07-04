package com.example.bloggertest;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

public class LoadingDialog extends Dialog {
    public LoadingDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_loading_animation);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

}
