package com.demo.loader.photo;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.demo.loader.R;
import com.demo.loader.library.BaseAbstractRecycleCursorAdapter;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoCursorAdapter extends BaseAbstractRecycleCursorAdapter<RecyclerView.ViewHolder> {
    private final LayoutInflater mLayoutInflater;

    public PhotoCursorAdapter(Context context) {
        super(context, null);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        if (cursor == null || cursor.isClosed()) {
            return;
        }
        PhotoItem item = PhotoItem.fromCursor(cursor);
        Picasso.get().load(item.path)
                .centerCrop()
               // .resize(imageGridSize/4*3, imageGridSize/4*3)
                .placeholder(R.drawable.default_img)
                .into(((ImageViewHolder) holder).image);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(mLayoutInflater.inflate(R.layout.photo_item,
                parent, false), this);
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        @Bind(android.R.id.icon)
        ImageView image;

        // cursor adapter
        PhotoCursorAdapter mAdapter;

        ImageViewHolder(View view, PhotoCursorAdapter adapter) {
            super(view);
            mAdapter = adapter;
            ButterKnife.bind(this, view);
        }

        @OnClick(android.R.id.checkbox)
        void onItemClick() {
            Log.d("ImageViewHolder", "onClick--> position = " + getLayoutPosition());
//            DemoItem item = DemoItem.fromCursor((Cursor) mAdapter.getItem(getPosition()));
//            if (getPosition() < 11) {
//                Intent intent = new Intent(mAdapter.mContext, DetailActivity.class);
//                intent.putExtra("position", getPosition());
//                intent.putExtra("title", item.title);
//                mAdapter.mContext.startActivity(intent);
//            } else {
//                Intent intent = new Intent(mAdapter.mContext, SelectActivity.class);
//                intent.putExtra("position", getPosition());
//                intent.putExtra("title", item.title);
//                mAdapter.mContext.startActivity(intent);
//            }
        }
    }


}



