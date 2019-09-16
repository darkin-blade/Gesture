package com.example.gesture;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

// 在新建手势功能中,提示保存方式的dialog

public class ResultDialog extends DialogFragment {public Button btnNew;// 新建手势库
    public Button btnOk;// 保存至现有手势库

    static public View view;
    static public FragmentManager fragmentManager;

    @Override
    public void show(FragmentManager fragmentManager, String tag) {// TODO 窗口切换
        super.show(fragmentManager, tag);

        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme);// 关闭背景(点击外部不能取消)
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("fuck", "on create view");
        view = inflater.inflate(R.layout.dialog_result, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));// 背景透明

        initButton();// 绑定按钮事件

        return view;
    }

    public void initButton() {
        btnNew = view.findViewById(R.id.button_1);

        // 返回
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
