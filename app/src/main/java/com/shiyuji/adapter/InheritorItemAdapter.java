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
import com.shiyuji.model.InheritorItem;

import java.util.List;

public class InheritorItemAdapter extends ArrayAdapter {
    private final int resourceId;
    private LayoutInflater inflater;

    public InheritorItemAdapter(@NonNull Context context, int resource, List<InheritorItem> items) {
        super(context, resource, items);
        resourceId = resource;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        InheritorItem item = (InheritorItem) getItem(position);
        View view = inflater.inflate(resourceId, null);

        ((ImageView) view.findViewById(R.id.inheritorHead)).setImageResource(item.getHead());
        ((TextView) view.findViewById(R.id.inheritorRealName)).setText(item.getRealName());
        ((TextView) view.findViewById(R.id.inheritorName)).setText(item.getName());
        ((TextView) view.findViewById(R.id.inheritorText)).setText(item.getText());

        return view;
    }
}
