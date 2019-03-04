package com.shiyuji;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedbackSettings extends AppCompatActivity {

    private EditText feedbackET;
    private EditText feedbackNumET;
    private Button feedbackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_feedback);

        feedbackET = (EditText) findViewById(R.id.feedbackET);
        feedbackNumET = (EditText) findViewById(R.id.feedbackNumET);
        feedbackButton = (Button) findViewById(R.id.feedbackButton);

        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FeedbackSettings.this, "意见：" + feedbackET.getText() + "\n" + "手机号/邮箱：" + feedbackNumET.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
