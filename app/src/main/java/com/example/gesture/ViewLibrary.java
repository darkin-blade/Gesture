package com.example.gesture;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.io.File;
import java.util.ArrayList;

public class ViewLibrary extends DialogFragment {
    public OpenLibrary openLibrary;

    public Button btnOpen,
            btnDelete,// 删除选中项
            btnBack;
    public TextView pathLibrary;// 打开的手势库路径
    public LinearLayout gestureList;// 手势库列表

    static public View myView;
    static public FragmentManager fragmentManager;

    public GestureLibrary gestureLibrary;// 打开的手势库

    public int img_width = 130;
    public int box_width = 80;
    public int name_padding = 30;
    public int img_padding = 20;
    public int detail_margin = 10;

    public ArrayList<String> listDelete;// 要删除的手势列表

    @Override
    public void show(FragmentManager fragmentManager, String tag) {
        super.show(fragmentManager, tag);
        MainActivity.window_num = MainActivity.VIEW_GESTURE;

        this.fragmentManager = fragmentManager;
        listDelete = null;
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
        myView = inflater.inflate(R.layout.library_view, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));// 背景透明

        initButton();// 绑定按钮事件
        initPath();

        return myView;
    }

    public void initButton() {
        btnOpen = myView.findViewById(R.id.button_3);
        btnDelete = myView.findViewById(R.id.button_2);
        btnBack = myView.findViewById(R.id.button_1);

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
        pathLibrary = myView.findViewById(R.id.library_name);// 手势库路径
        gestureList = myView.findViewById(R.id.gesture_list);// 手势库列表
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

        // 显示手势
        gestureList.removeAllViews();// 清空列表

        Object[] names = gestureLibrary.getGestureEntries().toArray();// 获取手势名列表
        if (names == null || names.length < 1) {
            MainActivity.infoToast(getContext(), "load library failed");
            return;
        }

        for (Object obj : names) {
            String name = obj.toString();
            Gesture gesture = gestureLibrary.getGestures(name).get(0);// TODO 默认不重名
            MainActivity.infoLog(name);
            createItem(name, gesture);
        }
    }

    public LinearLayout createItem(String itemName, Gesture gesture) {
        LinearLayout.LayoutParams itemParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, img_width);
        LinearLayout.LayoutParams typeParam = new LinearLayout.LayoutParams(img_width, img_width);
        LinearLayout.LayoutParams imgParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams detailParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams boxParam = new LinearLayout.LayoutParams(box_width, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams nameParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        LinearLayout item = new LinearLayout(getContext());// 整个手势
        item.setLayoutParams(itemParam);
        item.setBackgroundResource(R.color.grey);
        item.setPadding(0, 0, 0, 0);

        LinearLayout type = new LinearLayout(getContext());// 图标的外圈
        type.setLayoutParams(typeParam);
        type.setPadding(img_padding, img_padding, img_padding, img_padding);
        type.setBackgroundResource(R.drawable.item_gesture);

        ImageView img = new ImageView(getContext());// 手势预览
        Bitmap bitmap = gesture.toBitmap(128, 128, 10, 0xff30f030);
        img.setLayoutParams(imgParam);
        img.setImageBitmap(bitmap);
        img.setBackgroundResource(R.color.grey);

        final RelativeLayout detail = new RelativeLayout(getContext());// TODO
        detailParam.setMargins(detail_margin, detail_margin, detail_margin, detail_margin);
        detail.setLayoutParams(detailParam);

        final TextView name = new TextView(getContext());// TODO 文件名
        name.setLayoutParams(nameParam);
        name.setBackgroundResource(R.color.transparent);
        name.setText(itemName);
        name.setPadding(name_padding, name_padding, name_padding, name_padding);
        name.setSingleLine();

        CheckBox checkBox = new CheckBox(getContext());
        checkBox.setLayoutParams(boxParam);
        checkBox.setButtonDrawable(R.drawable.checkbox_gesture);

        type.addView(img);
        item.addView(type);
        detail.addView(name);
        detail.addView(checkBox);
        item.addView(detail);

        // 设置靠父元素左/右
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) name.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        name.setLayoutParams(params);
        params = (RelativeLayout.LayoutParams) checkBox.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        checkBox.setLayoutParams(params);

        // TODO 添加选中监听
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    listDelete.add(name.getText().toString());
                    detail.setBackgroundResource(R.color.grey_light);
                } else {
                    listDelete.remove(name.getText().toString());
                    detail.setBackgroundResource(R.color.transparent);
                }
                MainActivity.infoLog("size: " + listDelete.size());
            }
        });

        gestureList.addView(item);// 添加至列表

        return item;
    }
}
