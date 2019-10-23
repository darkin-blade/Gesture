package com.example.gesture;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.io.File;
import java.util.ArrayList;

// 打开手势库,并识别输入的手势

public class TestGesture extends DialogFragment {
    public OpenLibrary openLibrary;// 打开手势库
    public ResultDialog resultDialog;// 手势识别结果

    public Button btnOpen;// 打开手势库
    public Button btnReco;// 识别手势
    public Button btnCancel;// 删除当前
    public Button btnExit;// 返回到主页面
    public TextView pathLibrary;// 打开的手势库名称

    public GestureOverlayView gestureOverlayView;
    public GestureLibrary gestureLibrary;// 打开的手势库
    public Gesture gesture;

    static public View myView;
    static public FragmentManager fragmentManager;

    @Override
    public void show(FragmentManager fragmentManager, String tag) {
        super.show(fragmentManager, tag);
        MainActivity.window_num = MainActivity.TEST_GESTURE;

        this.fragmentManager = fragmentManager;
        gestureLibrary = null;// 初始化
        openLibrary = new OpenLibrary();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme);// 关闭背景(点击外部不能取消)
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.test_gesture, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));// 背景透明

        initGesture();// 初始化手势
        initButton();// 绑定按钮事件

        return myView;
    }

    public void initButton() {
        btnOpen = myView.findViewById(R.id.button_4);
        btnReco = myView.findViewById(R.id.button_3);
        btnCancel = myView.findViewById(R.id.button_2);
        btnExit = myView.findViewById(R.id.button_1);

        // 打开手势库
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLibrary.show(fragmentManager, "open");
            }
        });

        // 识别当前手势
        btnReco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 检查手势库
                if (openLibrary.nameLibrary == null || openLibrary.nameLibrary.length() < 1) {
                    MainActivity.infoToast(getContext(), "you haven't open any library");
                    return;
                }

                gestureLibrary = GestureLibraries.fromFile(openLibrary.nameLibrary);// 打开手势库

                // 识别手势
                gesture = gestureOverlayView.getGesture();
                if (gesture == null) {// 没有输入手势
                    MainActivity.infoToast(getContext(), "please input gesture");
                    return;
                }

                // 加载手势库
                if (gestureLibrary.load() == false) {
                    MainActivity.infoToast(getContext(), "can't load this library");
                    return;
                }

                // TODO 获取识别结果
                ArrayList<Prediction> predictions = gestureLibrary.recognize(gesture);
                resultDialog = new ResultDialog();// 结果初始化
                resultDialog.results = new ArrayList<String>();// 识别结果
                int total = 0;
                for (Prediction prediction : predictions) {
                    MainActivity.infoLog(prediction.name + ":" + prediction.score);
                    resultDialog.results.add(prediction.name + ": " + prediction.score);// 匹配相似度
                    total ++;
                    if (total >= 5) {// TODO 只加载5个
                        break;
                    }
                }

                // 输出结果
                resultDialog.show(fragmentManager, "result");//显示结果
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gestureOverlayView.clear(true);// 清空
                gesture = null;
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gestureOverlayView.clear(true);// 清空
                gesture = null;
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
    //                MainActivity.infoLog(gestureOverlayView.getGesture().toString());
    //                if (gesture != null && gestureOverlayView.getGesture() == null) {// TODO 使手势不自动清除
    //                    gestureOverlayView.setGesture(gesture);
                        MainActivity.infoLog("started");
    //                }
                }

                @Override
                public void onGesture(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
                }

                @Override
                public void onGestureEnded(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
                    gesture = gestureOverlayView.getGesture();
                }

                @Override
                public void onGestureCancelled(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
                    MainActivity.infoLog("canceled");
                }
            });
    }

    public void loadName() {
        pathLibrary = myView.findViewById(R.id.library_name);
        File tempLibrary = new File(openLibrary.nameLibrary);
        pathLibrary.setText("current library: " + tempLibrary.getName());
    }
}
