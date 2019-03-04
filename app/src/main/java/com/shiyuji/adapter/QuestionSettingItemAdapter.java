package com.shiyuji.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shiyuji.model.QuestionSettingItem;
import com.shiyuji.R;

import java.util.List;

public class QuestionSettingItemAdapter extends ArrayAdapter {

    private final int resourceId;       // 当前视图id

    public QuestionSettingItemAdapter(Context context, int textViewResourceId, List<QuestionSettingItem> items) {
        super(context, textViewResourceId, items);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        QuestionSettingItem item = (QuestionSettingItem) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView questionTV = (TextView) view.findViewById(R.id.questionTV);

        questionTV.setText(item.getQuestion());     // 设置问题文字

        return view;
    }
}
