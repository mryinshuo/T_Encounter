package com.shiyuji.myUtils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.shiyuji.Application.MyApplication;
import com.shiyuji.EditVideo;
import com.shiyuji.IndexActivity;
import com.shiyuji.R;
import com.shiyuji.bean.Discussion;
import com.shiyuji.bean.User;
import com.shiyuji.bean.Video;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class picMethod {
    /*
     * 使用照相机
     * */
    private static Bitmap bitmap;
    private static String encodedString;
    private static TextView choosePhoto;
    private static TextView takePhoto;
    private static View inflate;
    private static Dialog dialog;
    public static void show(View.OnClickListener onClickListener) {

        dialog = new Dialog(MyApplication.getContext(), R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.channel_topic_edit_camera, null);
        //初始化控件
        choosePhoto = (TextView) inflate.findViewById(R.id.choosePhoto);
        takePhoto = (TextView) inflate.findViewById(R.id.takePhoto);
        choosePhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
        takePhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
       /* choosePhoto.setOnClickListener(onClickListener);
        takePhoto.setOnClickListener(onClickListener);*/
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

    public static Intent useCamera( ){
        return new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
    }

    //使用相册
    public static Intent useGallery(){
        /*File outputImage=new File(getExternalCacheDir(),"outputImage.jpg");*/

        return new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    }


    //开始上传图片
    public static void uploadImage(String imgPath,Object o,String pointUrl) {
        if (imgPath != null && !imgPath.isEmpty()) {//相册获得
            Log.d("xuanze", "onActivityResult:upload image");
            encodeImagetoString(imgPath,o,pointUrl);
        } else {//相机获得
            init(o,pointUrl);
            //Toast.makeText(MyApplication.getContext(), "You must select image from gallery before you try to upload", Toast.LENGTH_LONG).show();
        }
    }
    /*
     * 图片转码
     * */
    @SuppressLint("StaticFieldLeak")
    public static void encodeImagetoString(final String imgPath, final Object o, final String pointUrl) {

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
                // 上传图片*/
                Log.d("xuanze", "onActivityResult:put image ");
                init(o,pointUrl);
            }
        }.execute(null, null, null);
    }




    private static void init(Object o,String pointUrl){
        String urlPath= MyApplication.getMYURL()+pointUrl;
        final URL url;
        int id=1;
        Log.d("ResponseCode", "init函数");
        try {
            url=new URL(urlPath);
            JSONObject jsonObject=new JSONObject();
            if (o instanceof Video ) {
                Video video = (Video) o;
                if (encodedString==null){
                    encodedString=video.getImage();
                }

                jsonObject.put("title",video.getTitle());  //参数put到json串里
                jsonObject.put("intro", video.getIntro());
                jsonObject.put("image", encodedString);
                jsonObject.put("uid", video.getUid());
            }
            if (o instanceof User){
                User user = (User) o;
                if (encodedString==null){
                    encodedString=user.getImage();
                }
                jsonObject.put("phone",user.getPhone());
                jsonObject.put("image", encodedString);
            }

            if (o instanceof Discussion){
                Discussion ds = (Discussion) o;
                jsonObject.put("title",ds.getTitle());  //参数put到json串里
                jsonObject.put("intro", ds.getIntro());
                jsonObject.put("cateId", ds.getCateId());
                jsonObject.put("image", encodedString);
                jsonObject.put("uid", ds.getUid());
            }
          //  Log.d("ResponseCodephone", "myphone："+uid);
            final String content=String.valueOf(jsonObject);  //json串转string类型

            new Thread(new Runnable() {
                @Override
                public void run(){
                    try {
                        HttpURLConnection conn=(HttpURLConnection) url.openConnection(); //开启连接
                        conn.setConnectTimeout(5000);
                        Log.d("ResponseCode", "start conn");
                        conn.setDoOutput(true);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("ser-Agent", "Fiddler");
                        conn.setRequestProperty("Content-Type","application/json");
                        //写输出流，将要转的参数写入流里
                        Log.d("ResponseCode", "start os");
                        OutputStream os=conn.getOutputStream();
                        os.write(content.getBytes()); //字符串写进二进流
                        os.close();
                        int code=conn.getResponseCode();
                        Log.d("ResponseCode", "获取code:"+code);
                      /*  Intent it=new Intent(EditVideo.this, IndexActivity.class);
                        startActivity(it);*/
                    }  catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
            Log.d("ResponseCode", "连接成功");

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /*
处理图片
*/
    public static String handleTakePhoto(Intent data){
        Bitmap bitmap;
        //"data"这个居然没用常量定义,也是醉了,我们可以发现它直接把bitmap序列化到intent里面了。
        bitmap = data.getExtras().getParcelable("data");
        //TODO:do something with bitmap, Do NOT forget call Bitmap.recycler();
        ByteArrayOutputStream bStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bStream);
        byte[]bytes=bStream.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);
    }
    public static String handleChoosePhoto(Intent data){
        String imgPath;
        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        // 获取游标
        Cursor cursor =  MyApplication.getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        imgPath = cursor.getString(columnIndex);
        cursor.close();
        return imgPath;
    }

/*    @Override
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

        }
    }*/
}
