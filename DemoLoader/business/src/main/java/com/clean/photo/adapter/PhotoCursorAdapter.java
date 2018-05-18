package com.clean.photo.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.clean.R;
import com.learn.data.entity.PhotoEntity;
import com.open.appbase.adapter.recyclerview.BaseRecyclerCursorAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.Optional;

import static com.clean.businesscommon.common.Const.PHOTO_GRID_NUM;

/**
 * The type Photo cursor adapter.
 */
public class PhotoCursorAdapter extends BaseRecyclerCursorAdapter<RecyclerView.ViewHolder> {
    // global LayoutInflater to void multi-get in onCreateViewHolder
    private final LayoutInflater mLayoutInflater;
    // context, you'd better pass one WeakReference to avoid Memory Leak
    private Context mContext;

    // image view size and photo crop size
    private int photoSize;
    private int imageViewSize;

    // save all image path, used for release image when destroy
    private HashSet<String> imageSet;

    /**
     * Instantiates a new Photo cursor adapter.
     *
     * @param context the context
     */
    public PhotoCursorAdapter(Context context) {
        super(context, null);
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;

        initData();
    }

    private void initData() {
        imageViewSize = getScreenWidth() / PHOTO_GRID_NUM;
        photoSize = getScreenWidth() * PHOTO_GRID_NUM / (PHOTO_GRID_NUM + 1);
        imageSet = new HashSet<>();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        // cursor validation
        if (cursor == null || cursor.isClosed()) {
            Logger.w("cursor invalidate");
            return;
        }
        PhotoEntity item = PhotoEntity.fromCursor(cursor);
        // photo path validation
        if (item.path == null || item.path.isEmpty()) {
            Logger.d("photo item invalidate");
            return;
        }

        Picasso.with(mContext).load(new File(item.path))
                .centerCrop()
                .resize(photoSize, photoSize)
                .placeholder(R.drawable.default_img)
                .into(((ImageViewHolder) holder).image);

        imageSet.add(item.path);
    }


    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(mLayoutInflater.inflate(R.layout.photo_recycler_item,
                parent, false), this);
    }

    /**
     * The type Image view holder.
     */
    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        /**
         * The Image.
         */
        @BindView(android.R.id.icon)
        ImageView image;

        /**
         * The M adapter ref.
         */
        Reference<PhotoCursorAdapter> mAdapterRef;

        /**
         * Instantiates a new Image view holder.
         *
         * @param view    the view
         * @param adapter the adapter
         */
        ImageViewHolder(View view, PhotoCursorAdapter adapter) {
            super(view);
            mAdapterRef = new WeakReference<>(adapter);
            ButterKnife.bind(this, view);

            ViewGroup.LayoutParams params = image.getLayoutParams();
            params.height = params.width = adapter.imageViewSize;
        }

        /**
         * On checked changed.
         *
         * @param buttonView the button view
         * @param isChecked  the is checked
         */
        @Optional
        @OnCheckedChanged(android.R.id.checkbox)
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (mAdapterRef.get() == null || mAdapterRef.get().mContext == null) {
                return;
            }

            if (isChecked) {
                PhotoEntity item = PhotoEntity.fromCursor((Cursor) mAdapterRef.get().getItem(getLayoutPosition()));
                Logger.d("check " + getLayoutPosition() + ":" + item.title);
                Toast.makeText(mAdapterRef.get().mContext, "check " + getLayoutPosition() + ":" + item.title, Toast.LENGTH_SHORT).show();
            } else {
                // unCheck
                Logger.d("uncheck " + getLayoutPosition());
            }
        }
    }

    private int getScreenWidth() {
        try {
            WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(outMetrics);
            return outMetrics.widthPixels;

        } catch (Exception ex) {
            Logger.w("getScreenWidth ex", ex);
            return 800;
        }
    }

    /**
     * Release all images in adapter
     */
    public void releasePicasso() {
        for (String path : imageSet) {
            Picasso.with(mContext).invalidate(new File(path));
            Logger.d("release Picasso photo " + path);
        }
    }
}



