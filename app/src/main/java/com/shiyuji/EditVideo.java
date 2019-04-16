package com.shiyuji;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.google.gson.Gson;
import com.mob.wrappers.AnalySDKWrapper;
import com.shiyuji.Application.MyApplication;
import com.shiyuji.bean.NetUtils;
import com.shiyuji.bean.Video;
import com.shiyuji.myUtils.picMethod;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.internal.http2.Header;

public class EditVideo extends AppCompatActivity implements View.OnClickListener {

    private TextView choosePhoto;
    private TextView takePhoto;
    private View inflate;
    private Dialog dialog;
    private ImageView editAddImage;
    private EditText topicInput;
    private EditText Vtitle;
    private TextView topicInputNum;
    private boolean selected = false;
    private Button ReleaseVideo;
    ListView listView;
    String intro,title;
    private Uri ImageUri;
    private ProgressDialog prgDialog;
    private TextView  textAdd;
    private RequestParams params = new RequestParams();
    private String encodedString;
    private Bitmap bitmap;
    private String bitmapPic;
    public final static int REQUEST_IMAGE_CAPTURE = 1;
    private int RESULT_LOAD_IMG = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_edit);
        textAdd = (TextView) findViewById(R.id.textAdd);
        ReleaseVideo = (Button) findViewById(R.id.ReleaseVideo);
        topicInput = (EditText) findViewById(R.id.videoInputET);//视频简介
        Vtitle = (EditText) findViewById(R.id.videoTitleET);//视频标题
        topicInputNum = (TextView) findViewById(R.id.videoInputNum);
        editAddImage = (ImageView) findViewById(R.id.editAddImage);
        editAddImage.setOnClickListener(this);
        ReleaseVideo.setOnClickListener(this);
        topicInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                topicInputNum.setText(Integer.toString(s.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {
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
                Intent intent = picMethod.useCamera();
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                selected = true;
                break;
            case R.id.choosePhoto:
                Toast.makeText(this, "点击了从相册选择", Toast.LENGTH_SHORT).show();
                Intent intent1 = picMethod.useGallery();
                startActivityForResult(intent1, RESULT_LOAD_IMG);
                selected = true;
                break;
            case R.id.ReleaseVideo:
                boolean release = release();
                if (release)
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

    /**
     * 发布
     */
    public boolean release() {

        EditText mainTitle=(EditText) findViewById(R.id.videoTitleET); //获取用户控件
        title= mainTitle.getText().toString(); //获取控件里面的值
        EditText tvPsd=(EditText) findViewById(R.id.videoInputET);
        intro=tvPsd.getText().toString();
        final String pointUrl="video/addVideo.action";
        final Video video = new Video();
        if (title!=null&&intro!=null&&(encodedString!=null||bitmapPic!=null)) {
            video.setTitle(title);
            video.setIntro(intro);
            video.setImage(encodedString);
            video.setUid(MyApplication.phone);
            new Thread() {
                @Override
                public void run() {
                    picMethod.uploadImage(bitmapPic, video, pointUrl);
                }
            }.start();
            Toast.makeText(this, "发布视频成功", Toast.LENGTH_SHORT).show();
            Intent follows = new Intent(this, IndexActivity.class);
            startActivity(follows);

            return true;
        }else{
            Toast.makeText(this, "请将信息填写完整",
                    Toast.LENGTH_LONG).show();
        return false;
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {//相机
                try {
                    encodedString=picMethod.handleTakePhoto(data);
                    Toast.makeText(this, "You  have already taken a picture",
                            Toast.LENGTH_LONG).show();
                    Log.d("xuanze", "onActivityResult: You  have already taken a picture");
                    textAdd.setText("已选定");
                } catch (ClassCastException e){
                    e.printStackTrace();
                }
            } else if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data)//相册获得
            {
                bitmapPic= picMethod.handleChoosePhoto(data);
                System.out.println("bitmapPic:"+bitmapPic);
                textAdd.setText("已选定");
            } else {

                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

}
