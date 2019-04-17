package com.shiyuji;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.shiyuji.adapter.QuestionSettingItemAdapter;
import com.shiyuji.model.QuestionSettingItem;

import java.util.ArrayList;
import java.util.List;

public class QuestionSettings extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private List<QuestionSettingItem> itemList = new ArrayList<>();     // 定义一个ArrayList存放所有要添加的item
    private LinearLayout questionItemLL;
    private ImageView questionItemExpandIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_question);

        initItems();                                                                                            // 生成ListView内容
        QuestionSettingItemAdapter adapter = new QuestionSettingItemAdapter(this, R.layout.settings_question_item, itemList); // 声明adapter
        ListView itemsLV = (ListView) findViewById(R.id.settingsQuestionLV);                                    // 获得该ListView
        itemsLV.setAdapter(adapter);                                                                            // 设置adapter
        itemsLV.setOnItemClickListener(this);                                                                   // 监听item的click事件
    }

    private void initItems() {
        itemList.add(new QuestionSettingItem("注册失败怎么办？", "联系客服哦", false));
        itemList.add(new QuestionSettingItem("登陆失败怎么办？", "联系客服哦", false));
        itemList.add(new QuestionSettingItem("视频加载不出来怎么办？", "联系客服哦", false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        questionItemLL = (LinearLayout) view.findViewById(R.id.questionItemLL);                         // 获取item最外层LinearLayout
        questionItemExpandIV = (ImageView) view.findViewById(R.id.questionItemExpandIV);                // 获取箭头ImageView
        if (!itemList.get(i).isExpanded()) {                                                            // 若问题未展开
            itemList.get(i).setExpanded(true);                                                              // 将isExpanded设置为true（问题已展开）
            questionItemExpandIV.setImageResource(R.drawable.up);                                           // 更换图片为向上箭头
            TextView answer = new TextView(this);                                                   // 创建答案TextView
            answer.setId(R.id.answerTV);                                                                    // 设置id（方便remove）
            answer.setText("答：" + itemList.get(i).getAnswer());                                           // 设置答案内容
            answer.setTextSize((int) Utils.dp2px(this, 6));                               //  设置字体大小
            answer.setBackgroundColor(Color.parseColor("#EEEEEE"));                              // 设置背景颜色
            answer.setPadding((int) Utils.dp2px(this, 24), (int) Utils.dp2px(this, 10), (int) Utils.dp2px(this, 24), (int) Utils.dp2px(this, 10));    // 设置左padding
            questionItemLL.addView(answer, 1);                                                        // 将答案TextView添加在问题下面（1相对于item）
        } else {                                                                                        // 若问题已展开
            itemList.get(i).setExpanded(false);                                                             // 将isExpanded设置为false（问题未展开）
            questionItemExpandIV.setImageResource(R.drawable.down);                                         // 更换图片为向下箭头
            questionItemLL.removeView(questionItemLL.findViewById(R.id.answerTV));                          // 移除答案TextView
        }
    }
}
