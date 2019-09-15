package com.example.gesture;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
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
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class AddGesture extends DialogFragment {
    public Button btnSave;// 保存手势
    public Button btnDelete;// 删除当前

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

        // 绑定按钮事件
        initButton();

        return view;
    }

    public void initButton() {
        ;
    }


    public void getGesture(GestureOverlayView gestureOverlayView, final Gesture gesture) {// TODO
        Bitmap bitmap = gesture.toBitmap(128,128,10,0x000000);
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
