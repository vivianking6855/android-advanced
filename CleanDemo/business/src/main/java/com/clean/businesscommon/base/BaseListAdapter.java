package com.clean.businesscommon.base;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseListAdapter<DT, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<DT> mData;

    public BaseListAdapter() {
    }

    public void setData(List<DT> list) {
        if (list == null) {
            return;
        }
        if (mData == null) {
            mData = new ArrayList<>();
        } else {
            mData.clear();
        }

        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void clearData() {
        if (mData != null) {
            mData.clear();
            mData = null;
        }
    }

    public void addData(List<DT> list) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

}
