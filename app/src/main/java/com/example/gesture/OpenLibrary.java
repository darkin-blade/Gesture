package com.example.gesture;

import android.app.Activity;
import android.gesture.GestureLibraries;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.gesture.fragments.NormalManager;

import java.io.File;

public class OpenLibrary extends NormalManager {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.library_open, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));// 背景透明

        initPath(myView);// 初始化路径
        initButton(myView);// 绑定按钮事件

        // 调用文件管理器
        Activity activity = getActivity();
        readPath(activity.getExternalFilesDir("").getAbsolutePath());
        return myView;
    }

    public void initButton(View view) {
        cancel = view.findViewById(R.id.cancel_button);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public LinearLayout itemOnClick(int itemType, final String itemName, final String itemPath, LinearLayout item) {// 绑定点击事件
        if (itemType == 2) {// 父文件夹
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    File dir = new File(itemPath);
                    readPath(dir.getParent());
                }
            });
        } else if (itemType == 1) {// `点击`遍历子文件夹
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    readPath(itemPath + "/" + itemName);
                }
            });
        } else {// 获取手势库
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nameLibrary = itemPath + "/" + itemName;// TODO 手势库路径
                    dismiss();
                }
            });
        }

        return item;
    }

}
