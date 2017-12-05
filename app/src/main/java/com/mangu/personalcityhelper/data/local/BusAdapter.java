package com.mangu.personalcityhelper.data.local;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mangu.personalcityhelper.R;

import java.util.ArrayList;


public class BusAdapter extends BaseAdapter {
    protected Context mContext;
    protected ArrayList<BusItem> mList;

    public BusAdapter(Context mContext, ArrayList<BusItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    public void clear() {
        mList.clear();
    }


    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (view == null) {
            LayoutInflater li = LayoutInflater.from(mContext);
            v = li.inflate(R.layout.view_bus, null);
        }
        BusItem item = mList.get(i);
        TextView tvLine = (TextView) v.findViewById(R.id.tv_line);
        tvLine.setText(item.getmLine());
        TextView tvLineName = (TextView) v.findViewById(R.id.tv_line_name);
        tvLineName.setText(item.getmLineInfo());
        TextView tvId = (TextView) v.findViewById(R.id.line_id);
        tvId.setText(item.getmId());
        return v;
    }
}
