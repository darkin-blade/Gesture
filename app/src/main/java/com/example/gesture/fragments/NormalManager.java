package com.example.gesture.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.gesture.MainActivity;
import com.example.gesture.R;

import java.io.File;

public class NormalManager extends DialogFragment {
    public Button yes;
    public Button cancel;

    public int item_height = 130;
    public int type_padding = 20;
    public int name_padding = 40;

    public TextView curPath;// 当前路径

    static public String path;// 打开的库的路径

    @Override
    public void show(FragmentManager fragmentManager, String tag) {
        super.show(fragmentManager, tag);
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme);// 关闭背景(点击外部不能取消)
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("fuck", "on create view");
        View view = inflater.inflate(R.layout.library_add, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));// 背景透明

        initPath(view);// 初始化路径
        initButton(view);// 绑定按钮事件

        // 调用文件管理器
        Activity activity = getActivity();
        readPath(activity.getExternalFilesDir("").getAbsolutePath(), view);
        return view;
    }

    public void initPath(View view) {
        curPath = view.findViewById(R.id.cur_path);// 路径框
        MainActivity.infoLog("curpath: " + (curPath == null));
    }

    public void initButton(View view) {
        ;
    }

    public void readPath(final String dirPath, View manager) {
        // 特判根目录
        if (dirPath == null) {
            MainActivity.infoToast(getContext(), "can't access this path");
            dismiss();// 强制返回
            return;
        }

        // 清空并显示父目录
        LinearLayout layout = manager.findViewById(R.id.item_list);
        layout.removeAllViews();
        createItem(2, "..", dirPath, manager);// 父目录

        // 遍历文件夹
        File dir = new File(dirPath);
        File[] items = dir.listFiles();
        if (items != null) {
            for (int i = 0; i < items.length ; i++) {
                if (items[i].isDirectory()) {
                    createItem(1, items[i].getName(), dirPath, manager);
                } else {
                    createItem(0, items[i].getName(), dirPath, manager);
                }
            }
        }

        // 显示路径
        curPath.setText(dirPath);// TODO 简化路径
    }



    public LinearLayout createItem(int itemType, final String itemName, final String itemPath, final View manager) {// 创建图标
        return null;
    }
}
