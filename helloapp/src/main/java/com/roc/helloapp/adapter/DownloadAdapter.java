package com.roc.helloapp.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by ROC on 2016/11/23.
 */

public class DownloadAdapter extends BaseAdapter {
    private List<Map<String,String>> downloadList;
    public DownloadAdapter(List<Map<String,String>> downloadList) {
        this.downloadList=downloadList;
    }

    @Override
    public int getCount() {
        return downloadList==null?0:downloadList.size();
    }

    @Override
    public Object getItem(int i) {
        return downloadList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup group) {
        return null;
    }
}
