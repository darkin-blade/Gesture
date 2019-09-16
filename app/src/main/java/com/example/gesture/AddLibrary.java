package com.example.gesture;

import android.app.Activity;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.gesture.fragments.NormalManager;

import java.io.File;

// `添加至已有手势库`的文件管理器

public class AddLibrary extends NormalManager {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("fuck", "on create view");
        myView = inflater.inflate(R.layout.library_add, container);
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
        textGesture = view.findViewById(R.id.gesture_name);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public LinearLayout itemOnClick(int itemType, final String itemName, final String itemPath, LinearLayout item) {//
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
        } else {// 将手势存入该手势库
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nameLibrary = itemPath + "/" + itemName;
                    nameGesture = textGesture.getText().toString();// 手势名称不能为空

                    if (nameGesture.length() == 0) {
                        MainActivity.infoToast(getContext(), "gesture name can't be empty");
                        return;
                    }


                    // 加入手势库
                    GestureLibrary gestureLibrary = GestureLibraries.fromFile(nameLibrary);
                    gestureLibrary.addGesture(nameGesture, MainActivity.addGesture.gesture);
                    gestureLibrary.save();
                    dismiss();
                }
            });
        }

        return item;
    }
}
