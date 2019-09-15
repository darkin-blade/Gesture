package com.example.gesture;

import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class AddGesture extends DialogFragment {
    public Button btnSave;// 保存手势
    public Button btnCancel;// 删除当前
    public Button btnExit;// 返回到主页面

    public GestureOverlayView gestureOverlayView;
    public Gesture gesture;

    public String gesturePath;// 保存路径
    public View view;

    @Override
    public void show(FragmentManager fragmentManager, String tag) {
        super.show(fragmentManager, tag);
        MainActivity.window_num = 1;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme);// 关闭背景(点击外部不能取消)
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("fuck", "on create view");
        view = inflater.inflate(R.layout.add_gesture, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));// 背景透明

        initGesture();// 初始化手势
        initButton();// 绑定按钮事件

        return view;
    }

    public void initButton() {
        btnSave = view.findViewById(R.id.save_button);
        btnCancel = view.findViewById(R.id.cancel_button);
        btnExit = view.findViewById(R.id.exit_button);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getGesture();// TODO
                gestureOverlayView.clear(true);// 清空
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gestureOverlayView.clear(true);// 清空
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gestureOverlayView.clear(true);// 清空
                dismiss();
            }
        });
    }


    public void getGesture() {// TODO
//        Bitmap bitmap = gesture.toBitmap(128,128,10,0x000000);
//        img.setImageBitmap(bitmap);
                        //获取文件对应的手势库
//                        GestureLibrary gestureLib = GestureLibraries.fromFile(gesturePath);// TODO 路径
//                        gestureLib.addGesture(edit_name.getText().toString(),gesture);
//                        gestureLib.save();
    }


    public void initGesture() {
        // 初始化路径
        gesturePath = MainActivity.appPath + "/gesture";

        // 设置颜色
        gestureOverlayView = view.findViewById(R.id.gesture);
        gestureOverlayView.setGestureColor(Color.GREEN);
        gestureOverlayView.setUncertainGestureColor(Color.GREEN);
        gestureOverlayView.setGestureStrokeWidth(5);

        gestureOverlayView.addOnGestureListener(new GestureOverlayView.OnGestureListener() {
            @Override
            public void onGestureStarted(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
                gesture = null;
            }

            @Override
            public void onGesture(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
            }

            @Override
            public void onGestureEnded(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
            }

            @Override
            public void onGestureCancelled(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
            }
        });
    }
}