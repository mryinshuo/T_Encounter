package com.shiyuji;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.shiyuji.Application.MyApplication;
import com.shiyuji.adapter.SettingItemAdapter;
import com.shiyuji.bean.User;
import com.shiyuji.model.SettingItem;
import com.shiyuji.myUtils.ImageBitmap;
import com.shiyuji.myUtils.changeUser;
import com.shiyuji.myUtils.getPersonMes;
import com.shiyuji.myUtils.picMethod;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InfoSettings extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "InfoSettings";
    private List<SettingItem> itemList = new ArrayList<>();     // 定义一个ArrayList存放所有要添加的item
    private Calendar calendar;
    private int year, month, day;
    private TextView itemDetailTV;
    private boolean selected = false;
    private int choice;
    private TextView choosePhoto;
    private TextView takePhoto;
    private View inflate;
    private Dialog dialog;
    private TextView  textAdd;
    private String encodedString;
    private Bitmap bitmap;
    private String bitmapPic;
    public final static int REQUEST_IMAGE_CAPTURE = 1;
    private int RESULT_LOAD_IMG = 2;
    public final static String pointUrl = "user/updateUser.action";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_info);
        initItems();                                                                                            // 生成ListView内容
        SettingItemAdapter adapter = new SettingItemAdapter(this, R.layout.settings_item, itemList);    // 声明adapter
        ListView itemsLV = (ListView) findViewById(R.id.settingsInfoLV);                                        // 获得该ListView
        itemsLV.setAdapter(adapter);                                                                            // 设置adapter
        itemsLV.setOnItemClickListener(this);                                                                   // 监听item的click事件

        getDate();
    }

    private void initItems() {
        User user = getPersonMes.getConnect(MyApplication.phone);
        Bitmap imageBitmap;
        //System.out.println("phone:"+MyApplication.phone+" getHeadUrl:"+user.getHeadUrl());
        if(user!=null){
            String imgUrl = user.getHeadUrl();
            imageBitmap = ImageBitmap.getImageBitmap("headImage/"+imgUrl);
            itemList.add(new SettingItem("头像", imageBitmap, null));
        }else {
            itemList.add(new SettingItem("头像", R.drawable.user_head, null));
        }

       /* imageBitmap = ImageBitmap.getImageBitmap("headImage/"+user.getHeadUrl());
        itemList.add(new SettingItem("头像", imageBitmap, null));*/
        itemList.add(new SettingItem("手机号", user.getPhone(), null));
        itemList.add(new SettingItem("昵称", user.getName(), null));
        itemList.add(new SettingItem("个性签名", user.getSignature(), null));
        itemList.add(new SettingItem("性别", user.getSex(), null));
        itemList.add(new SettingItem("生日", user.getBirthday(), null));
    }

    private void getDate() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        itemDetailTV = view.findViewById(R.id.itemDetailTV);
        if (itemList.get(i).getText().equals("头像")) {
            changeHeadImg();
            //Toast.makeText(this, "现在还改不了", Toast.LENGTH_SHORT).show();
        }
        if (itemList.get(i).getText().equals("个性签名")) {
            finish();
           Intent intent =new Intent(this,ChangeInfoSettings.class);
           intent.putExtra("title","个性签名");
           startActivity(intent);
            //Toast.makeText(this, "现在还改不了", Toast.LENGTH_SHORT).show();
        }
        if (itemList.get(i).getText().equals("昵称")) {
            finish();
            Intent intent =new Intent(this,ChangeInfoSettings.class);
            intent.putExtra("title","昵称");
            startActivity(intent);
            //Toast.makeText(this, "现在还改不了", Toast.LENGTH_SHORT).show();
        }
        if (itemList.get(i).getText().equals("性别")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("请选择性别：");
            final String[] sex = {"男", "女"};
            builder.setSingleChoiceItems(sex, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    choice = which;
                }
            });
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    itemDetailTV.setText(sex[choice]);
                    User user = new User();
                    user.setSex(sex[choice]);
                    user.setPhone(MyApplication.phone);
                    changeUser.init(user,pointUrl);
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {}
            });
            builder.show();
        }

        if (itemList.get(i).getText().equals("生日")) {
            DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int year, int month, int day) {
                    String birthday = year + " - " + (month + 1) + " - " + day;
                    itemDetailTV.setText(birthday);
                    User user = new User();
                    user.setBirthday(birthday);
                    user.setPhone(MyApplication.phone);
                    changeUser.init(user,pointUrl);
                }
            };
            DatePickerDialog dialog = new DatePickerDialog(this, 0, listener, year, month, day);
            dialog.show();
        }
    }

    public void changeHeadImg() {
        final User user = new User();
        user.setPhone(MyApplication.phone);

        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.channel_topic_edit_camera, null);
        //初始化控件
        choosePhoto = (TextView) inflate.findViewById(R.id.choosePhoto);
        takePhoto = (TextView) inflate.findViewById(R.id.takePhoto);
        choosePhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MyApplication.getContext(), "点击了从相册选择", Toast.LENGTH_SHORT).show();
                Intent intent1 = picMethod.useGallery();
                startActivityForResult(intent1, RESULT_LOAD_IMG);
            }
        });
        takePhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = picMethod.useCamera();
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

            }

        });
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
    public void changeName(){
        final User user = new User();
        user.setPhone(MyApplication.phone);
        final String pointUrl = "user/updateUser.action";

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("我被销毁");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final User user = new User();
        user.setPhone(MyApplication.phone);
        final String pointUrl = "user/updateUser.action";
        try {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {//相机
                try {
                    encodedString=picMethod.handleTakePhoto(data);
                    user.setImage(encodedString);//bitmapPic != null
                } catch (ClassCastException e){
                    e.printStackTrace();
                }
            } else if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data)//相册获得
            {
                bitmapPic= picMethod.handleChoosePhoto(data);

            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
            picMethod.uploadImage(bitmapPic,user,pointUrl);//上传头像
            selected = true;
            if (selected) {
                dialog.dismiss();
                selected = false;
            }

           Intent intent = new Intent(this, IndexActivity.class);
            startActivity(intent);
            finish();


        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }


}
