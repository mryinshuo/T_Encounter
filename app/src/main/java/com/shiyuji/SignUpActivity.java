package com.shiyuji;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shiyuji.Application.MyApplication;
import com.shiyuji.db.personalInformation;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;



public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SignUpActivity";
    private Intent loginIntent;
    private EditText editTextUn;
    private EditText editTextPw;
    private EditText SecEditTextPw;
    private EditText editTextPwCh;
    private Button signUpButton;
    private TextView sign2log;
    private TextView getCaptcha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        loginIntent = new Intent(this, LoginActivity.class);

        editTextUn = (EditText) findViewById(R.id.signUpUsername);
//        editTextPw = (EditText) findViewById(R.id.signUpPassword);
        sign2log = (TextView) findViewById(R.id.sign2log);
        sign2log.setOnClickListener(this);
//
        signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(this);
//验证码
        getCaptcha = (TextView) findViewById(R.id.getCaptcha);
        getCaptcha.setOnClickListener(this);
//输入验证码
        editTextPwCh = (EditText) findViewById(R.id.findPwCaptcha);
        //第一次密码
        editTextPw = (EditText) findViewById(R.id.signUpPassword);
        //第二次输入的密码
        SecEditTextPw = (EditText) findViewById(R.id.confirmPassword);
    }

    /**
     *验证码
     */
    EventHandler eventHandler = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            mHandler.sendMessage(msg);
        }

    };
    /**
     * 验证码
     */
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event=" + event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                    String FirPwd = editTextPw.getText().toString();
                    String SecPwd = SecEditTextPw.getText().toString();
                    String AccountNum = editTextUn.getText().toString();
                    if (FirPwd.equals(SecPwd)){//验证两次密码相同
                        MyApplication.getInstance().hashMapInfo.put("AccountNum",AccountNum);
                        MyApplication.getInstance().hashMapInfo.put("FirPwd",FirPwd);
                        Register(FirPwd,AccountNum);
                        Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    }else{
                        editTextPw.setText("");
                        SecEditTextPw.setText("");
                        Toast.makeText(getApplicationContext(), "两次密码不一致", Toast.LENGTH_SHORT).show();
                    }

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //已经验证
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //已经验证
                    Toast.makeText(getApplicationContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();
//                    textV.setText(data.toString());
                }

            } else {
//				((Throwable) data).printStackTrace();
//				Toast.makeText(MainActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
//					Toast.makeText(MainActivity.this, "123", Toast.LENGTH_SHORT).show();
                int status = 0;
                try {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;

                    JSONObject object = new JSONObject(throwable.getMessage());
                    String des = object.optString("detail");
                    status = object.optInt("status");
                    if (!TextUtils.isEmpty(des)) {
                        Toast.makeText(SignUpActivity.this, des, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    SMSLog.getInstance().w(e);
                }
            }
        }
    };

    //将资源的图片保存到本地图片
    public static void savePic(Context context) {
        File pic = new File("");
        if (!pic.exists()) {
            try {
                //把资源文件转成bitmap
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.channel_videos);

                //再转成字节数组
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int i = 100;
                bitmap.compress(Bitmap.CompressFormat.PNG, i, out);
                byte[] array = out.toByteArray();

                //最后通过流在保存
                FileOutputStream fos = new FileOutputStream(pic);
                fos.write(array);
                fos.close();
            } catch (FileNotFoundException e) {
                // TODO: handle exception
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }

        }
    }

    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    };



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signUpButton:     // 注册按钮
//                String username = editTextUn.getText().toString();
//                String password = editTextPw.getText().toString();
//                String checkPassword = editTextPwCh.getText().toString();
//                Toast.makeText(this, "账号：" + username + "\n" + "密码：" + password + "\n" + "确认密码：" + checkPassword, Toast.LENGTH_SHORT).show();
// 提交验证码，其中的code表示验证码，如“1357”
                SMSSDK.submitVerificationCode("86", editTextUn.getText().toString(), editTextPwCh.getText().toString());
                break;
            case R.id.sign2log:         // 登陆按钮
                startActivity(loginIntent);      // 启动登陆Activity
                finish();
                break;
            case R.id.getCaptcha:         // 验证码操作
                // 注册一个事件回调，用于处理SMSSDK接口请求的结果
                SMSSDK.registerEventHandler(eventHandler);
                // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
                SMSSDK.getVerificationCode("86", editTextUn.getText().toString());
                Log.d(TAG, "onClick:验证码操作 ");
                break;
        }
    }

    public void onBackPressed() {       // 当触摸返回键时
        AlertDialog.Builder checkExit = new AlertDialog.Builder(this);      // 创建对话框
        checkExit.setMessage("退出程序？");
        checkExit.setCancelable(true);
        checkExit.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();                                                       // 选“是”则结束当前Activity
            }
        });
        checkExit.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}      // 选“否”对话框消失
        });
        checkExit.show();       // 弹出对话框
    }

    public void Register(String pwd,String accountNum) {

        SimpleDateFormat formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
        String    str    =    formatter.format(curDate);

        if (pwd!=null){
            personalInformation personalInformation = new personalInformation();
            personalInformation.setPassword(pwd);
            personalInformation.setAccountNumber(accountNum);
            personalInformation.setRegisterTime(str);
            personalInformation.save();
        }
    }


}
