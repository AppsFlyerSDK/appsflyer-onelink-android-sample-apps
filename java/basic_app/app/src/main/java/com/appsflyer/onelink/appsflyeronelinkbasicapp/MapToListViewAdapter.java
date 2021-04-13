package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class MapToListViewAdapter extends BaseAdapter {
    private final ArrayList mapData;

    public MapToListViewAdapter(Map<String, Object> map) {
        mapData = new ArrayList();
        if (map != null) {
            mapData.addAll(map.entrySet());
        }
    }

    @Override
    public int getCount() {
        return mapData.size();
    }

    @Override
    public Map.Entry<String, Object> getItem(int position) {
        return (Map.Entry) mapData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_items_adapter, parent, false);
        } else {
            result = convertView;
        }

        Map.Entry<String, Object> item = getItem(position);

        // TODO replace findViewById by ViewHolder
        ((TextView) result.findViewById(R.id.textkey)).setText(item.getKey() + ": ");
        Object value = result.findViewById(R.id.textvalue);
        if (value == null) {
            value = "null";
        }
        ((TextView) value).setText(item.getValue().toString());

        return result;
    }
}


