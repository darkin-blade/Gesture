package com.example.gesture;

import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.io.File;

public class ViewLibrary extends DialogFragment {
    public OpenLibrary openLibrary;

    public Button btnOpen,
            btnDelete,// 删除选中项
            btnBack;
    public TextView pathLibrary;// 打开的手势库路径

    static public View view;
    static public FragmentManager fragmentManager;

    public GestureLibrary gestureLibrary;// 打开的手势库

    @Override
    public void show(FragmentManager fragmentManager, String tag) {
        super.show(fragmentManager, tag);
        MainActivity.window_num = MainActivity.VIEW_GESTURE;// TODO

        this.fragmentManager = fragmentManager;
        pathLibrary = null;
        gestureLibrary = null;
        openLibrary = new OpenLibrary();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme);// 关闭背景(点击外部不能取消)
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.library_view, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));// 背景透明

        initButton();// 绑定按钮事件
        initPath();

        return view;
    }

    public void initButton() {
        btnOpen = view.findViewById(R.id.button_3);
        btnDelete = view.findViewById(R.id.button_2);
        btnBack = view.findViewById(R.id.button_1);

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLibrary.show(fragmentManager, "open");
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void initPath() {
        pathLibrary = view.findViewById(R.id.library_name);// 手势库路径
    }

    public void loadLibrary() {// TODO 加载手势库
        gestureLibrary = GestureLibraries.fromFile(openLibrary.nameLibrary);// 打开手势库

        // 检查手势库
        if (gestureLibrary == null) {
            MainActivity.infoToast(getContext(), "you haven't open any library");
            return;
        }

        // 加载手势库
        if (gestureLibrary.load() == false) {
            MainActivity.infoToast(getContext(), "can't load this library");
            return;
        }

        File tempLibrary = new File(openLibrary.nameLibrary);
        pathLibrary.setText("current library: " + tempLibrary.getName());
    }
}
