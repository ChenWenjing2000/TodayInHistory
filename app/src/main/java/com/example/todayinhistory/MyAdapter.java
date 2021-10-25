package com.example.todayinhistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends ArrayAdapter {
    public MyAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Item> data) {
        super(context, resource, (List) data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,
                    parent,
                    false);
        }
        Item item = (Item) getItem(position);
        TextView Text = (TextView) itemView.findViewById(R.id.itemText);
        Text.setText(item.gettext());
        TextView Text1 = (TextView) itemView.findViewById(R.id.itemTitle);
        Text1.setText(item.gettitle());
        return itemView;
    }
}
