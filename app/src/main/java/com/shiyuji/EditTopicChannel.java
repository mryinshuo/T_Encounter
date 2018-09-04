package com.shiyuji;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditTopicChannel extends AppCompatActivity implements View.OnClickListener {

    private View inflate;
    private TextView choosePhoto;
    private TextView takePhoto;
    private Dialog dialog;
    private ImageView editAddImage;
    private EditText topicInput;
    private TextView topicInputNum;
    private boolean selected = false;
    private final int TOTALNUM = 300;
    private boolean isOutOfBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_topic_edit);

        topicInput = (EditText) findViewById(R.id.topicInputET);
        topicInputNum = (TextView) findViewById(R.id.topicInputNum);
        editAddImage = (ImageView) findViewById(R.id.editAddImage);
        editAddImage.setOnClickListener(this);

        topicInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count <= TOTALNUM) {
                    topicInputNum.setText(Integer.toString(count));
                } else {
                    isOutOfBound = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isOutOfBound) {

                }
            }
        });
    }

    public void show() {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.channel_topic_edit_camera, null);
        //初始化控件
        choosePhoto = (TextView) inflate.findViewById(R.id.choosePhoto);
        takePhoto = (TextView) inflate.findViewById(R.id.takePhoto);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        //显示对话框
        dialog.show();
    }

    //拍照和从相册中选择的监听
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.takePhoto:
                Toast.makeText(this, "点击了拍照", Toast.LENGTH_SHORT).show();
                selected = true;
                break;
            case R.id.choosePhoto:
                Toast.makeText(this, "点击了从相册选择", Toast.LENGTH_SHORT).show();
                selected = true;
                break;
            case R.id.editAddImage:
                show();
                break;
        }
        if (selected) {
            dialog.dismiss();
            selected = false;
        }
    }
}
