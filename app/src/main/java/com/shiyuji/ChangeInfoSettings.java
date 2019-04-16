package com.shiyuji;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.shiyuji.Application.MyApplication;
import com.shiyuji.bean.User;
import com.shiyuji.myUtils.changeUser;

public class ChangeInfoSettings extends AppCompatActivity {

    private Titlebar changeInfoTitlebar;
    private EditText changeInfoET;
    private Button changeInfoBt;
    private User user;
    private String title;
    private String text=" ";
    public final static String pointUrl = "user/updateUser.action";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_info_change);

        changeInfoTitlebar = (Titlebar) findViewById(R.id.changeInfoTitlebar);
        changeInfoET = (EditText) findViewById(R.id.changeInfoET);
        changeInfoBt = (Button) findViewById(R.id.changeInfoBt);
        title = getIntent().getStringExtra("title");
        changeInfoTitlebar.setTitle(title);

        user = new User();

        changeInfoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                 * TODO
                 * 将 changeInfoET.getText() 写入数据库
                 */
                text = changeInfoET.getText().toString();
                switch(title){
                    case "昵称":
                        user.setName(text);
                        user.setPhone(MyApplication.phone);
                        changeUser.init(user,pointUrl);
                        Toast.makeText(MyApplication.getContext(), "昵称修改成功", Toast.LENGTH_SHORT).show();
                        break;
                    case "个性签名":
                        user.setSignature(text);
                        user.setPhone(MyApplication.phone);
                        changeUser.init(user,pointUrl);
                        Toast.makeText(MyApplication.getContext(), "个性签名修改成功", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        Toast.makeText(MyApplication.getContext(), "貌似出错了", Toast.LENGTH_SHORT).show();
                        break;
                }
                finish();
                Intent intent = new Intent(ChangeInfoSettings.this, InfoSettings.class);
                startActivity(intent);
            }
        });



/*
        String value = "从数据库读取原值";
        changeInfoET.setText(value);*/
    }


}
