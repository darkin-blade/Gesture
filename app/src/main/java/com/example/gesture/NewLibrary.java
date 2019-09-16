package com.example.gesture;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gesture.fragments.NormalManager;

import java.io.File;

// `新建手势库`的文件管理器

public class NewLibrary extends NormalManager {
    public EditText fileName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("fuck", "on create view");
        View view = inflater.inflate(R.layout.library_save, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));// 背景透明

        initPath(view);// 初始化路径
        initButton(view);// 绑定按钮事件

        // 调用文件管理器
        Activity activity = getActivity();
        readPath(activity.getExternalFilesDir("").getAbsolutePath(), view);
        return view;
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

    public LinearLayout createItem(int itemType, final String itemName, final String itemPath, final View manager) {// 创建图标
        LinearLayout layout = manager.findViewById(R.id.item_list);
        LinearLayout.LayoutParams itemParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, item_height);
        LinearLayout.LayoutParams typeParam = new LinearLayout.LayoutParams(item_height, item_height);
        LinearLayout.LayoutParams iconParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams nameParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        LinearLayout item = new LinearLayout(getContext());// TODO 参数
        item.setLayoutParams(itemParam);
        item.setBackgroundResource(R.color.grey);
        item.setPadding(name_padding, 0, 0, 0);

        LinearLayout type = new LinearLayout(getContext());// 图标的外圈
        type.setLayoutParams(typeParam);
        type.setPadding(type_padding, type_padding, type_padding, type_padding);

        View icon = new View(getContext());// 图标
        icon.setLayoutParams(iconParam);
        if (itemType == 0) {// 文件
            icon.setBackgroundResource(R.drawable.item_file);
        } else {// 文件夹
            icon.setBackgroundResource(R.drawable.item_dir);
        }

        TextView name = new TextView(getContext());// 文件名
        name.setLayoutParams(nameParam);
        name.setBackgroundResource(R.color.grey);
        name.setText(itemName);
        name.setPadding(name_padding, name_padding, name_padding, name_padding);
        name.setSingleLine();

        type.addView(icon);
        item.addView(type);
        item.addView(name);

        if (itemType == 2) {// 父文件夹
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    File dir = new File(itemPath);
                    readPath(dir.getParent(), manager);
                }
            });
        } else if (itemType == 1) {// `点击`遍历子文件夹
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    readPath(itemPath + "/" + itemName, manager);
                }
            });
        } else {// `点击`文件,没有任何反应
            ;
        }

        layout.addView(item);

        return item;
    }
}
