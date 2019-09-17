package com.example.gesture.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

    public String nameLibrary;// 打开的库的路径
    public String nameGesture;// 手势名称

    public View myView;
    public TextView curPath;// 当前路径
    public EditText textLibrary;// 新手势库命名
    public EditText textGesture;// 新收拾命名

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
        return null;
    }

    public void initPath(View view) {
        curPath = view.findViewById(R.id.cur_path);// 路径框
    }

    public void initButton(View view) {
        ;
    }

    public void readPath(final String dirPath) {
        // 特判根目录
        if (dirPath == null) {
            MainActivity.infoToast(getContext(), "can't access this nameLibrary");
            dismiss();// 强制返回
            return;
        }

        // 清空并显示父目录
        LinearLayout layout = myView.findViewById(R.id.item_list);
        layout.removeAllViews();
        createItem(2, "..", dirPath);// 父目录

        // 遍历文件夹
        File dir = new File(dirPath);
        File[] items = dir.listFiles();
        if (items != null) {
            for (int i = 0; i < items.length; i++) {
                if (items[i].isDirectory()) {
                    createItem(1, items[i].getName(), dirPath);
                } else {
                    createItem(0, items[i].getName(), dirPath);
                }
            }
        }

        // 显示路径
        curPath.setText(dirPath);// TODO 简化路径
    }

    public LinearLayout createItem(int itemType, final String itemName, final String itemPath) {// 创建图标
        LinearLayout layout = myView.findViewById(R.id.item_list);
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

        item = itemOnClick(itemType, itemName, itemPath, item);

        layout.addView(item);

        return item;
    }

    public LinearLayout itemOnClick(int itemType, final String itemName, final String itemPath, LinearLayout item) {// 绑定点击事件
        return item;
    }
}