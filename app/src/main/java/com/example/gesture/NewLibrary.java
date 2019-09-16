package com.example.gesture;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.gesture.fragments.NormalManager;

import java.io.File;

// `新建手势库`的文件管理器

public class NewLibrary extends NormalManager {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("fuck", "on create view");
        myView = inflater.inflate(R.layout.library_save, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));// 背景透明

        initPath(myView);// 初始化路径
        initButton(myView);// 绑定按钮事件

        // 调用文件管理器
        Activity activity = getActivity();
        readPath(activity.getExternalFilesDir("").getAbsolutePath());
        return myView;
    }

    public void initButton(View view) {
        yes = view.findViewById(R.id.yes_button);
        cancel = view.findViewById(R.id.cancel_button);
        fileName = view.findViewById(R.id.library_name);// 新手势库名称

        yes.setOnClickListener(new View.OnClickListener() {//
            @Override
            public void onClick(View view) {
                // 文件名不能为空
                if (fileName.getText().toString().length() == 0) {
                    MainActivity.infoToast(getContext(), "file name can't be empty");
                    return;
                }

                path = curPath.getText().toString() + "/" + fileName.getText().toString();

                // 判断有无重名
                File file = new File(path);
                if (file.exists() == false) {// 没有重名
                    dismiss();
                } else {
                    MainActivity.infoToast(getContext(), path + " already exists");
                    return;
                }
            }
        });

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
        } else {// `点击`文件,没有任何反应
            ;
        }

        return item;
    }
}
