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
import android.widget.LinearLayout;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

// 在识别手势功能中,显示手势识别的结果

public class ResultDialog extends DialogFragment {
    public Button btnOk;// 返回
    public LinearLayout gestureResult;// 手势识别结果

    public ArrayList<String> results;// 手势结果列表

    static public View view;
    static public FragmentManager fragmentManager;

    public void show(FragmentManager fragmentManager, String tag) {
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
        showResult();

        return view;
    }

    public void initButton() {
        btnOk = view.findViewById(R.id.button_1);

        // 返回
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void showResult() {// 显示手势匹配结果
        gestureResult = view.findViewById(R.id.gesture_result);// 结果列表
    }
}
