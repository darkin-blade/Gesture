package com.example.gesture;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

// 在新建手势功能中,提示保存方式的dialog

public class SaveDialog extends DialogFragment {
    public NewLibrary newLibrary;// 新建手势库的文件管理器
    public AddLibrary addLibrary;// 加入至已有手写库

    public Button btnNew;// 新建手势库
    public Button btnSave;// 保存至现有手势库
    public Button btnBack;// 返回
    public ImageView gestureShow;// 手势预览

    static public View view;
    static public FragmentManager fragmentManager;

    @Override
    public void show(FragmentManager fragmentManager, String tag) {// TODO 窗口切换
        super.show(fragmentManager, tag);

        this.fragmentManager = fragmentManager;
        newLibrary = new NewLibrary();
        addLibrary = new AddLibrary();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme);// 关闭背景(点击外部不能取消)
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_save, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));// 背景透明

        initButton();// 绑定按钮事件
        displayGesture();// 预览手势

        return view;
    }

    public void initButton() {
        btnNew = view.findViewById(R.id.button_3);
        btnSave = view.findViewById(R.id.button_2);
        btnBack = view.findViewById(R.id.button_1);

        // 新建手势库
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newLibrary.show(fragmentManager, "new library");
                dismiss();
            }
        });

        // 选择现有的手势库
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLibrary.show(fragmentManager, "add to library");
                dismiss();
            }
        });

        // 返回
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void displayGesture() {
        gestureShow = view.findViewById(R.id.gesture_show);// 手势预览
        Bitmap bitmap = AddGesture.gesture.toBitmap(128, 128, 10, 0xff30f030);
        MainActivity.infoLog((bitmap == null) + "");
        gestureShow.setImageBitmap(bitmap);
    }
}
