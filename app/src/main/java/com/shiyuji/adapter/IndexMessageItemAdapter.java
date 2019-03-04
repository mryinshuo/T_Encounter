package com.shiyuji.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiyuji.R;
import com.shiyuji.model.IndexMessageItem;

import java.util.List;

public class IndexMessageItemAdapter extends ArrayAdapter {
    private final int resourceId;
    private LayoutInflater inflater;

    public IndexMessageItemAdapter(@NonNull Context context, int resource, List<IndexMessageItem> items) {
        super(context, resource, items);
        resourceId = resource;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        IndexMessageItem item = (IndexMessageItem) getItem(position);
        View view = inflater.inflate(resourceId, null);

        ((ImageView) view.findViewById(R.id.indexMessageIV)).setImageResource(item.getHead());
        ((TextView) view.findViewById(R.id.indexMessageName)).setText(item.getName());
        ((TextView) view.findViewById(R.id.indexMessageText)).setText(item.getText());
        ((TextView) view.findViewById(R.id.indexMessageTime)).setText(item.getTime());

        return view;
    }
}
