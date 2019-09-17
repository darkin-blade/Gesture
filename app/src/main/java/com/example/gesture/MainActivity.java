package com.example.gesture;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {
    static AddGesture addGesture;// 添加新的手势
    static TestGesture testGesture;// 测试手势库
    static ViewLibrary viewLibrary;// 浏览手势库内容

    static public String appPath;// app路径
    static public final int MAIN = 0;
    static public final int ADD_TO_LIBRARY = 1;
    static public final int CREATE_NEW_LIBRARY = 2;
    static public final int VIEW_GESTURE = 3;// 浏览手势
    static public final int TEST_GESTURE = 4;// 测试

    static public int window_num;// TODO 模式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initButton();
    }

    public void initData() {
        // 初始化路径
        appPath = getExternalFilesDir("").toString();

        // 初始化窗口
        addGesture = new AddGesture();
        testGesture = new TestGesture();
        viewLibrary = new ViewLibrary();

        // 检查权限
        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        int check_result = ActivityCompat.checkSelfPermission(this, permission);// `允许`返回0,`拒绝`返回-1
        if (check_result != PackageManager.PERMISSION_GRANTED) {// 没有`写`权限
            ActivityCompat.requestPermissions(this, new String[]{permission}, 1);// 获取`写`权限
        }
    }

    public void initButton() {
        Button btnAdd = findViewById(R.id.add_gesture);
        btnAdd.setOnClickListener(new View.OnClickListener() {// 点击`打开`按钮
            @Override
            public void onClick(View view) {
                addGesture.show(getSupportFragmentManager(), "add");
            }
        });

        Button btnTest = findViewById(R.id.test_gesture);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testGesture.show(getSupportFragmentManager(), "test");
            }
        });

        Button btnOpen = findViewById(R.id.view_library);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewLibrary.show(getSupportFragmentManager(), "view");
            }
        });
    }

    static public void infoLog(String log) {
        Log.i("fuck", log);
    }

    static public void infoToast(Context context, String log) {
        Toast toast =  Toast.makeText(context, log, Toast.LENGTH_SHORT);
        View view = toast.getView();
        TextView textView = view.findViewById(android.R.id.message);
        textView.setTextColor(Color.rgb(0x00, 0x00, 0x00));
        toast.show();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        infoLog("window_num: " + window_num);
        switch (window_num) {
            case ADD_TO_LIBRARY:
                break;
            case CREATE_NEW_LIBRARY:
                break;
            case VIEW_GESTURE:
                viewLibrary.loadLibrary();
                break;
            case TEST_GESTURE:
                testGesture.loadName();
                break;
        }
    }
}