package com.example.gesture;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {
    AddGesture addGesture;// 1

    static public String appPath;// app路径

    static public int window_num;// 模式

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
        switch (window_num) {
            case 1:
                resAdd();
                break;
        }
    }

    public void resAdd() {
        ;
    }
}