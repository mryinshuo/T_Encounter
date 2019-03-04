package com.shiyuji;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shiyuji.Application.MyApplication;
import com.shiyuji.db.personalInformation;

import org.litepal.FluentQuery;
import org.litepal.LitePal;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    private EditText editTextUn;
    private EditText editTextPw;
    private TextView togglePw;
    private Button loginButton;
    private TextView findPw;
    private TextView log2sign;
    private boolean isShown = false;
    private SharedPreferences sps;
    private SharedPreferences.Editor edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取账号密码
        String accountNum = MyApplication.getInstance().hashMapInfo.get("AccountNum");
        String firPwd = MyApplication.getInstance().hashMapInfo.get("FirPwd");
        sps = PreferenceManager.getDefaultSharedPreferences(this);
        editTextUn = (EditText) findViewById(R.id.loginUn);
        if (accountNum != null) {
            editTextUn.setText(accountNum);//初始化账号
        } else if (sps.getString("AccNum", null) != null) {
            Log.d(TAG, "onCreate: AccNum" + sps.getString("AccNum", null));
            editTextUn.setText(sps.getString("AccNum", null));//初始化账号
        }


        editTextPw = (EditText) findViewById(R.id.loginPw);
        if (accountNum != null) {
            editTextPw.setText(firPwd);//初始化密码
        } else if (sps.getString("pwd", null) != null) {
            editTextPw.setText(sps.getString("pwd", null));//初始化账号

        }


        togglePw = (TextView) findViewById(R.id.togglePw);
        togglePw.setOnClickListener(this);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        log2sign = (TextView) findViewById(R.id.log2sign);
        log2sign.setOnClickListener(this);
        findPw = (TextView) findViewById(R.id.findPw);
        findPw.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.togglePw:
                if (!isShown) {                                                                     // 若当前密码为隐藏状态
                    editTextPw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());    // 显示密码
                    togglePw.setText("隐藏");                                                             // 改变文字
                    editTextPw.setSelection(editTextPw.length());                                         // 将光标移至最后
                    isShown = true;
                } else {                                                                            // 若当前密码为显示状态
                    editTextPw.setTransformationMethod(PasswordTransformationMethod.getInstance());       // 隐藏密码
                    togglePw.setText("显示");                                                             // 改变文字
                    editTextPw.setSelection(editTextPw.length());                                         // 将光标移至最后
                    isShown = false;
                }
                break;
            case R.id.loginButton:      // 登录按钮
                Login();
                break;
            case R.id.log2sign:         // 注册按钮（login to sign up）
                Intent intent1 = new Intent(this, SignUpActivity.class);
                startActivity(intent1);     // 启动注册Activity
                finish();
                break;
            case R.id.findPw:           // 忘记密码按钮
                Intent intent2 = new Intent(this, FindPasswordActivity.class);
                startActivity(intent2);     // 启动找回密码Activity
                break;                      // 不调用finish()，找回密码页为二级目录，用户须退回一级目录再退出程序
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
            public void onClick(DialogInterface dialogInterface, int i) {
            }      // 选“否”对话框消失
        });
        checkExit.show();       // 弹出对话框
    }

    //登陆验证
    private void Login() {
        FluentQuery where = null;
        String AccNum = editTextUn.getText().toString();
        String pwd = editTextPw.getText().toString();
        personalInformation perInformations;

        //LitePal.where("accountNumber = ?", AccNum).find(personalInformation.class);
        try {
            where = LitePal.where("accountNumber = ?", AccNum);
            if (where != null) {
                perInformations = where.find(personalInformation.class).get(0);
                String password = perInformations.getPassword();//数据库中对应账号的密码
                if (password.equals(pwd)) {
                    //默认记主上次登陆账号密码
                    Log.d(TAG, "Login: AccNum" + AccNum);
                    edit = sps.edit();
                    edit.putString("AccNum", AccNum);
                    edit.putString("pwd", pwd);
                    edit.commit();
                    Log.d(TAG, "Login: AccNum111" + sps.getString("AccNum", null));
                    Intent intent = new Intent(this, IndexActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    editTextPw.setText("");
                    Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
                }
            } else {
                editTextUn.setText("");
                editTextPw.setText("");
            }//if end
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "账号不存在", Toast.LENGTH_SHORT).show();
        }
    }
}
