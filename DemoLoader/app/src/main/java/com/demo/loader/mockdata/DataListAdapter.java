package com.demo.loader.mockdata;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.loader.R;
import com.demo.loader.base.BaseListAdapter;

public class DataListAdapter extends BaseListAdapter<MockEntity, DataListAdapter.DataViewHolder> {

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        DataViewHolder holder = new DataViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        if (mData == null || position >= mData.size()) {
            return;
        }
        holder.tv.setText(mData.get(position).name);
    }

    class DataViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public DataViewHolder(View view) {
            super(view);
            tv = view.findViewById(android.R.id.title);
        }
    }
}
