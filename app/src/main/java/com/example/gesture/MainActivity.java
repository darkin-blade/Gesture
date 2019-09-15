package com.example.gesture;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    public EditText editText;
    public GestureOverlayView gestureOverlayView;
    public Gesture gesture;

    public String gesturePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        // initGesture();// TODO
    }

    public void initData() {
        // 检查权限
        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        int check_result = ActivityCompat.checkSelfPermission(this, permission);// `允许`返回0,`拒绝`返回-1
        if (check_result != PackageManager.PERMISSION_GRANTED) {// 没有`写`权限
            ActivityCompat.requestPermissions(this, new String[]{permission}, 1);// 获取`写`权限
        }

        gesturePath = getExternalFilesDir("").toString() + "/gestureOverlayView";
    }

    public void initGesture() {
        //获取手势编辑组件后，设置相关参数
        gestureOverlayView = findViewById(R.id.gesture);
        gestureOverlayView.setGestureColor(Color.GREEN);
        gestureOverlayView.setUncertainGestureColor(Color.rgb(0xef, 0xa0, 0x50));
        gestureOverlayView.setGestureStrokeWidth(5);

        gestureOverlayView.addOnGestureListener(new GestureOverlayView.OnGestureListener() {
            @Override
            public void onGestureStarted(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
                Log.i("fuck", "start");

                gesture = null;
            }

            @Override
            public void onGesture(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
                Log.i("fuck", "gesture");
            }

            @Override
            public void onGestureEnded(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
                Log.i("fuck", "ended");

//                gesture = gestureOverlayView.getGesture();
            }

            @Override
            public void onGestureCancelled(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
                Log.i("fuck", "canceled");
            }
        });
    }

    public void getGesture(GestureOverlayView gestureOverlayView, final Gesture gesture) {
        View saveDialog = getLayoutInflater().inflate(R.layout.dialog_save,null,false);
        ImageView img_show = saveDialog.findViewById(R.id.gesture_img);
        final EditText edit_name = saveDialog.findViewById(R.id.edit_name);
        Bitmap bitmap = gesture.toBitmap(128,128,10,0xffff0000);
        img_show.setImageBitmap(bitmap);
        new AlertDialog.Builder(MainActivity.this).setView(saveDialog)
                .setPositiveButton("保存",new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //获取文件对应的手势库
                        GestureLibrary gestureLib = GestureLibraries.fromFile(gesturePath);// TODO 路径
                        gestureLib.addGesture(edit_name.getText().toString(),gesture);
                        gestureLib.save();
                    }
                }).setNegativeButton("取消", null).show();
    }
}