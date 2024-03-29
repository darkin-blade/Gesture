package com.example.gesture;

import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class AddGesture extends DialogFragment {
    public SaveDialog saveDialog;

    public Button btnSave;// 保存手势
    public Button btnCancel;// 删除当前
    public Button btnExit;// 返回到主页面

    static public GestureOverlayView gestureOverlayView;
    static public Gesture gesture;

    static public View myView;
    static public FragmentManager fragmentManager;

    @Override
    public void show(FragmentManager fragmentManager, String tag) {
        super.show(fragmentManager, tag);

        this.fragmentManager = fragmentManager;
        saveDialog = new SaveDialog();// 提示框
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme);// 关闭背景(点击外部不能取消)
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.add_gesture, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));// 背景透明

        initGesture();// 初始化手势
        initButton();// 绑定按钮事件

        return myView;
    }

    public void initButton() {
        btnSave = myView.findViewById(R.id.button_3);
        btnCancel = myView.findViewById(R.id.button_2);
        btnExit = myView.findViewById(R.id.button_1);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gesture = gestureOverlayView.getGesture();
                if (gesture != null) {// TODO
                    saveDialog.show(fragmentManager, "add save");
                } else {
                    MainActivity.infoToast(getContext(), "please input gesture");
                    return;
                }
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
        gestureOverlayView = myView.findViewById(R.id.gesture_input);
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
