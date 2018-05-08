package com.demo.loader.applist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.loader.R;
import com.demo.loader.base.BaseListAdapter;

public class ApkListAdapter extends BaseListAdapter<ApkEntity, ApkListAdapter.DataViewHolder> {

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_apk_item, parent, false);
        ApkListAdapter.DataViewHolder holder = new ApkListAdapter.DataViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        if (mData == null || position >= mData.size()) {
            return;
        }
        holder.tv.setText(mData.get(position).label);
        holder.icon.setImageDrawable(mData.get(position).icon);
    }

    class DataViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView icon;

        public DataViewHolder(View view) {
            super(view);
            tv = view.findViewById(android.R.id.title);
            icon = view.findViewById(android.R.id.icon);
        }
    }
}
