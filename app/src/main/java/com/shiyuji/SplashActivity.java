package com.shiyuji;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView = (ImageView) findViewById(R.id.splash);
        imageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.splash:       // 启动图片
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);      // 启动登陆Activity
                finish();
                break;
        }
    }
}
