package com.shiyuji;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

public class FindPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText findPwUsername;
    private EditText findPwCaptcha;
    private Button findPwButton;
    private TextView find2log;
    private EditText newPwd;
    private EditText affirmNewPassword;
    private TextView getCaptchaFind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        findPwUsername = (EditText) findViewById(R.id.findPwUsername);
        findPwUsername.setOnClickListener(this);
        findPwCaptcha = (EditText) findViewById(R.id.findPwCaptchaFind);
        findPwCaptcha.setOnClickListener(this);
        findPwButton = (Button) findViewById(R.id.findPwButton);
        findPwButton.setOnClickListener(this);
        find2log = (TextView) findViewById(R.id.find2log);
        find2log.setOnClickListener(this);
        //获取验证码
        getCaptchaFind = (TextView) findViewById(R.id.getCaptchaFind);
        getCaptchaFind.setOnClickListener(this);
        newPwd = (EditText)findViewById(R.id.newPassword);
        affirmNewPassword = (EditText)findViewById(R.id.affirmNewPassword);

    }
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    };

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
                    String FirPwd = newPwd.getText().toString();
                    String SecPwd = affirmNewPassword.getText().toString();
                    String AccountNum = findPwUsername.getText().toString();
                    if (FirPwd.equals(SecPwd)){//验证两次密码相同
                        MyApplication.getInstance().hashMapInfo.put("AccountNum",AccountNum);
                        MyApplication.getInstance().hashMapInfo.put("FirPwd",FirPwd);
                        if (findPwd(FirPwd,AccountNum)!=0){
                            Toast.makeText(getApplicationContext(), "成功找回密码", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(FindPasswordActivity.this, LoginActivity.class));
                        }


                    }else{
                        affirmNewPassword.setText("");
                        newPwd.setText("");
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
                        Toast.makeText(FindPasswordActivity.this, des, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    SMSLog.getInstance().w(e);
                }
            }
        }
    };



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.findPwButton:         // 找回密码按钮
                String username = findPwUsername.getText().toString();
                String captcha = findPwCaptcha.getText().toString();
                SMSSDK.submitVerificationCode("86", username, captcha);
               // Toast.makeText(this, "账号：" + username + "\n" + "验证码：" + captcha, Toast.LENGTH_SHORT).show();
                break;
            case R.id.find2log:             // 登陆按钮
                finish();
                break;
            case R.id.getCaptchaFind:         // 验证码操作
                // 注册一个事件回调，用于处理SMSSDK接口请求的结果
                SMSSDK.registerEventHandler(eventHandler);
                // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
                SMSSDK.getVerificationCode("86", findPwUsername.getText().toString());
               // Log.d(TAG, "onClick:验证码操作 ");
                break;
        }
    }

    public int findPwd(String pwd,String accountNum) {
        int i=0;
        if (pwd!=null){
            personalInformation personalInformation = new personalInformation();
            personalInformation.setPassword(pwd);
             i = personalInformation.updateAll("accountNumber = ?", accountNum);
        }
        return i;
    }

}
