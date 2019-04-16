package com.shiyuji;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
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

import com.shiyuji.Application.MyApplication;
import com.shiyuji.bean.Discussion;
import com.shiyuji.bean.Video;
import com.shiyuji.myUtils.picMethod;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class EditTopicChannel extends AppCompatActivity implements View.OnClickListener {

    private View inflate;
    private TextView choosePhoto;
    private TextView takePhoto;
    private Dialog dialog;
    private ImageView editAddImage;
    private EditText topicInput;
    private TextView topicInputNum;
    private TextView AddImage;
    private boolean selected = false;
    private String encodedString;
    private Bitmap bitmap;
    private String imgPath;
    private String bitmapPic;
    private Button releaseDisButton;
    String intro,title;
    private String cateid;
    public final static int REQUEST_IMAGE_CAPTURE = 1;
    private int RESULT_LOAD_IMG = 2;
    private TextView  textAddChannel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_topic_edit);

        topicInput = (EditText) findViewById(R.id.topicInputET);
        topicInputNum = (TextView) findViewById(R.id.topicInputNum);
        editAddImage = (ImageView) findViewById(R.id.editAddImage);
        textAddChannel = (TextView) findViewById(R.id.textAddChannel);
        releaseDisButton = (Button) findViewById(R.id.releaseDisButton);

        Intent intent = getIntent();
        cateid = intent.getStringExtra("cateid");      // 从频道传回的标题id

        releaseDisButton.setOnClickListener(this);
        editAddImage.setOnClickListener(this);

        topicInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

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
            case R.id.releaseDisButton:
                boolean release = release();
                if (release){
                    selected = true;
                }

                break;
              /*  Toast.makeText(this, "发布视频", Toast.LENGTH_SHORT).show();
                EditText mainTitle=(EditText) findViewById(R.id.topicTitleET); //获取用户控件
                title= mainTitle.getText().toString(); //获取控件里面的值
                EditText tvPsd=(EditText) findViewById(R.id.topicInputET);
                intro=tvPsd.getText().toString();


                if (encodedString==null) {
                    Log.d("ResponseCode", "开始");
                    new Thread() {
                        @Override
                        public void run() {
                            uploadImage();
                        }
                    }.start();
                }else{//拍照获得
                    new Thread() {
                        @Override
                        public void run() {
                            init(title,intro);
                        }
                    }.start();
                }
                selected = true;
                break;*/
            case R.id.editAddImage:
                show();
                break;
        }
        if (selected) {
            dialog.dismiss();
            selected = false;
        }


    }



    public boolean release() {
        EditText mainTitle=(EditText) findViewById(R.id.topicTitleET); //获取用户控件
        title= mainTitle.getText().toString(); //获取控件里面的值
        EditText tvPsd=(EditText) findViewById(R.id.topicInputET);
        intro=tvPsd.getText().toString();
        final String pointUrl="discussion/addDiscussion.action";
        final Discussion discussion = new Discussion();
        if (title!=null&&intro!=null&&(encodedString!=null||bitmapPic!=null)) {
            discussion.setTitle(title);
            discussion.setIntro(intro);
            discussion.setCateId(cateid);
            discussion.setImage(encodedString);
            discussion.setUid(MyApplication.phone);
            new Thread() {
                @Override
                public void run() {
                    picMethod.uploadImage(bitmapPic, discussion, pointUrl);
                }
            }.start();
            Toast.makeText(this, "发布视频成功", Toast.LENGTH_SHORT).show();
            Intent it = new Intent(EditTopicChannel.this, TopicsChannel.class);
            startActivity(it);
            return true;
        }else{
            Toast.makeText(this, "请将信息填写完整",
                    Toast.LENGTH_LONG).show();
            return false;

        }

    }


/*    //开始上传图片
    private void uploadImage() {
        if (imgPath != null && !imgPath.isEmpty()) {

            Log.d("xuanze", "onActivityResult:upload image ");
            encodeImagetoString();
        } else {
            Toast.makeText(getApplicationContext(), "You must select image from gallery before you try to upload",
                    Toast.LENGTH_LONG).show();
        }
    }*/


/*
    */
/*
     * 图片转码
     * *//*


    @SuppressLint("StaticFieldLeak")
    public void encodeImagetoString() {
        Log.d("xuanze", "onActivityResult:encode image ");
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {

            };

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                bitmap = BitmapFactory.decodeFile(imgPath,
                        options);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // 压缩图片
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byte_arr = stream.toByteArray();
                // Base64图片转码为String
                encodedString = Base64.encodeToString(byte_arr, 0);
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {

            */
/*    // 将转换后的图片添加到上传的参数中

                // 上传图片*//*

                Log.d("xuanze", "onActivityResult:put image ");
                init(title,intro);
            }
        }.execute(null, null, null);
    }
*/



/*    private void init(String title1,String intro1) {

//  192.168.3.138 这个ip地址是电脑Ipv4 地址 /20170112 是服务端的项目名称  /login/toJsonMain 是@RequestMapping的地址
        String urlPath = MyApplication.getMYURL() + "discussion/addDiscussion.action";
        //    String urlPath="http://192.168.42.207:8080/20170112/login/toJsonMain.action"; 这个是实体机(手机)的端口
        final URL url;
        Log.d("ResponseCode", "init函数");
        int id = 1;
        try {
            url = new URL(urlPath);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", title1);  //参数put到json串里
            jsonObject.put("intro", intro1);
            jsonObject.put("cateId", cateid);
            jsonObject.put("uid", MyApplication.phone);
            jsonObject.put("image", encodedString);
            *//*params.put("image", encodedString);*//*
            Log.d("ResponseCode", "init函数：" + title1);
            //JSONObject Authorization =new JSONObject();
            //   Authorization.put("po类名",jsonObject 即po的字段)

            final String content = String.valueOf(jsonObject);  //json串转string类型

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //开启连接
                        conn.setConnectTimeout(5000);
                        Log.d("ResponseCode", "start conn");
                        conn.setDoOutput(true);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("ser-Agent", "Fiddler");
                        conn.setRequestProperty("Content-Type", "application/json");
                        //写输出流，将要转的参数写入流里
                        Log.d("ResponseCode", "start os");
                        OutputStream os = conn.getOutputStream();
                        os.write(content.getBytes()); //字符串写进二进流
                        os.close();
                        int code = conn.getResponseCode();
                        Log.d("ResponseCode", "获取code:" + code);
                        Intent it = new Intent(EditTopicChannel.this, TopicsChannel.class);
                        startActivity(it);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
            Log.d("ResponseCode", "连接成功");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

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
                    textAddChannel.setText("已选定");
                } catch (ClassCastException e){
                    e.printStackTrace();
                }
            } else if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data)//相册获得
            {
                bitmapPic= picMethod.handleChoosePhoto(data);
                System.out.println("bitmapPic:"+bitmapPic);
                textAddChannel.setText("已选定");
            } else {

                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }
/*        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);

            try {
                if (requestCode == REQUEST_IMAGE_CAPTURE) {
                    Bitmap bitmap;
                    try {
                        //"data"这个居然没用常量定义,也是醉了,我们可以发现它直接把bitmap序列化到intent里面了。
                        bitmap = data.getExtras().getParcelable("data");
                        //TODO:do something with bitmap, Do NOT forget call Bitmap.recycler();
                        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
                        byte[] bytes = bStream.toByteArray();
                        encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);

                        Toast.makeText(this, "You  have already taken a picture",
                                Toast.LENGTH_LONG).show();
                        Log.d("xuanze", "onActivityResult: You  have already taken a picture");
                        textAddChannel.setText("已选定");
                    } catch (ClassCastException e) {
                        //do something with exceptions
                        e.printStackTrace();
                    }
                } else if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
                    Bitmap bitmap;
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    // 获取游标
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imgPath = cursor.getString(columnIndex);
                    cursor.close();
                    Log.d("xuanze", "onActivityResult: You  picked Image");
                    textAddChannel.setText("已选定");
                } else {
                    Log.d("xuanze", "onActivityResult: You haven't picked Image");
                    Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }*/

    }
