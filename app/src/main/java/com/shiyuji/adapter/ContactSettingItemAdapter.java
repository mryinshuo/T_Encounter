package com.shiyuji.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shiyuji.model.ContactSettingItem;
import com.shiyuji.R;

import java.util.List;

public class ContactSettingItemAdapter extends ArrayAdapter {
    private final int resourceId;           // 当前视图id

    public ContactSettingItemAdapter(Context context, int textViewResourceId, List<ContactSettingItem> items) {
        super(context, textViewResourceId, items);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ContactSettingItem item = (ContactSettingItem) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        LinearLayout contactItemLeftLL = (LinearLayout) view.findViewById(R.id.contactItemLeftLL);      // 获取客服对话框（左对齐）
        LinearLayout contactItemRightLL = (LinearLayout) view.findViewById(R.id.contactItemRightLL);    // 获取用户对话框（右对齐）

        if (!item.isUser()) {                                                                                       // 若当前item是客服对话框
            contactItemRightLL.setVisibility(View.GONE);                                                                // 令用户对话框消失
            ((ImageView) contactItemLeftLL.findViewById(R.id.contactItemLeftIV)).setImageResource(item.getHead());      // 设置头像
            ((TextView) contactItemLeftLL.findViewById(R.id.contactItemLeftTV)).setText(item.getText());                // 设置文字
        } else {                                                                                                    // 若当前item是用户对话框
            contactItemLeftLL.setVisibility(View.GONE);                                                                 // 令客服对话框消失
            ((ImageView) contactItemRightLL.findViewById(R.id.contactItemRightIV)).setImageResource(item.getHead());    // 设置头像
            ((TextView) contactItemRightLL.findViewById(R.id.contactItemRightTV)).setText(item.getText());              // 设置文字
        }

        return view;
    }
}
