package com.vv.imagecache.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.vv.imagecache.R;
import com.vv.imagecache.common.Utils;
import com.vv.imagecache.imageloader.ImageLoader;

public class ImageAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Drawable mDefaultBitmapDrawable;
    private ImageLoader mImageLoader;

    public ImageAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mDefaultBitmapDrawable = context.getResources().getDrawable(R.drawable.image_default);
        mImageLoader = ImageLoader.build(context);
    }

    @Override
    public int getCount() {
        return Utils.sUrList.size();
    }

    @Override
    public String getItem(int position) {
        return Utils.sUrList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.image_list_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageView imageView = holder.imageView;
        final String tag = (String) imageView.getTag();
        final String uri = getItem(position);
        if (!uri.equals(tag)) {
            imageView.setImageDrawable(mDefaultBitmapDrawable);
        }
        if (Utils.sIsGridViewIdle && Utils.sCanGetBitmapFromNetWork) {
            imageView.setTag(uri);
            mImageLoader.bindBitmap(uri, imageView, Utils.sImageWidth, Utils.sImageWidth);
        }
        return convertView;
    }

    private static class ViewHolder {
        public ImageView imageView;
    }

}
