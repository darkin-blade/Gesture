package com.example.gesture;

import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
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

public class TestGesture extends DialogFragment {
    public Button btnOpen;// 打开手势库
    public Button btnReco;// 识别手势
    public Button btnCancel;// 删除当前
    public Button btnExit;// 返回到主页面

    public GestureOverlayView gestureOverlayView;
    public Gesture gesture;

    static public View view;
    static public FragmentManager fragmentManager;

    public String nameLibrary = null;// 手势库路径

    @Override
    public void show(FragmentManager fragmentManager, String tag) {
        super.show(fragmentManager, tag);

        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme);// 关闭背景(点击外部不能取消)
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("fuck", "on create view");
        view = inflater.inflate(R.layout.test_gesture, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));// 背景透明

        initGesture();// 初始化手势
        initButton();// 绑定按钮事件

        return view;
    }

    public void initButton() {
        btnOpen = view.findViewById(R.id.button_4);
        btnReco = view.findViewById(R.id.button_3);
        btnCancel = view.findViewById(R.id.button_2);
        btnExit = view.findViewById(R.id.button_1);

        // 打开手势库
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ;
            }
        });

        // 识别当前手势
        btnReco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameLibrary == null) {// 没有打开手势库
                    MainActivity.infoToast(getContext(), "you haven't open any library");
                    return;
                }

                // 识别手势
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

    public void initGesture() {
        // 设置颜色
        gestureOverlayView = view.findViewById(R.id.gesture_input);
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
