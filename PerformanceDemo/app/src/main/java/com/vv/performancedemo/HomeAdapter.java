package com.vv.performancedemo;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivian on 2016/12/19.
 */

public class HomeAdapter extends RecyclerView.Adapter {
    public final static String TAG = HomeAdapter.class.getSimpleName();

    private ArrayList<Droid> mData;
    private Context mContext;


    // click listener
    private OnItemClickLitener mOnItemClickLitener;

    public HomeAdapter(Context context) {
        mContext = context;
        initData();
    }

    protected void initData() {
        mData = (ArrayList) Droid.generateDatas();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_item, parent,
                false));
//        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_item_bad, parent,
//                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        Droid droid = mData.get(position);
        viewHolder.date.setText(droid.date);
        viewHolder.msg.setText(droid.msg);
        viewHolder.name.setText(droid.name);

        // optimized code
        if (droid.imageId == -1) {
            viewHolder.icon.setBackgroundColor(Color.parseColor("#F5F5DC"));
            viewHolder.icon.setImageResource(android.R.color.transparent);
        } else {
            viewHolder.icon.setImageResource(droid.imageId);
            viewHolder.icon.setBackgroundResource(android.R.color.transparent);
        }
        // bad code
        //viewHolder.icon.setBackgroundColor(Color.parseColor("#F5F5DC"));
        //viewHolder.icon.setImageResource(droid.imageId);

        // set listener
        setItemListener(viewHolder);
    }

    private void setItemListener(final MyViewHolder holder) {
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // view holder
    class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView icon;
        public TextView name;
        public TextView date;
        public TextView msg;

        public MyViewHolder(View view) {
            super(view);
            icon = (ImageView) view.findViewById(R.id.id_chat_icon);
            name = (TextView) view.findViewById(R.id.id_chat_name);
            date = (TextView) view.findViewById(R.id.id_chat_date);
            msg = (TextView) view.findViewById(R.id.id_chat_msg);
        }
    }

    public interface OnItemClickLitener {
        /**
         * @param view
         * @param position
         */
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);

    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.mOnItemClickLitener = onItemClickLitener;
    }
}
