package com.demo.loader.photo;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.demo.loader.R;
import com.demo.loader.common.Const;
import com.demo.loader.library.BaseAbstractRecycleCursorAdapter;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Optional;

import static com.demo.loader.photo.PhotoDataHelper.PHOTO_GRID_NUM;

/**
 * The type Photo cursor adapter.
 */
public class PhotoCursorAdapter extends BaseAbstractRecycleCursorAdapter<RecyclerView.ViewHolder> {
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
        imageSet = new HashSet<String>();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        // cursor validation
        if (cursor == null || cursor.isClosed()) {
            return;
        }
        PhotoItem item = PhotoItem.fromCursor(cursor);
        // photo path validation
        if (item.path == null || item.path.isEmpty()) {
            return;
        }
        Picasso.get().load(new File(item.path))
                .centerCrop()
                .resize(photoSize, photoSize)
                .placeholder(R.drawable.default_img)
                .into(((ImageViewHolder) holder).image);
        imageSet.add(item.path);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(mLayoutInflater.inflate(R.layout.photo_item,
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
            mAdapterRef = new WeakReference<PhotoCursorAdapter>(adapter);
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
                PhotoItem item = PhotoItem.fromCursor((Cursor) mAdapterRef.get().getItem(getLayoutPosition()));
                Log.d(Const.TAG, "check " + getLayoutPosition() + ":" + item.title);
                Toast.makeText(mAdapterRef.get().mContext, "check " + getLayoutPosition() + ":" + item.title, Toast.LENGTH_SHORT).show();
            } else {
                // unCheck
            }
        }

    }

    private int getScreenWidth() {
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * Release all images in adapter
     */
    public void release() {
        for (String path : imageSet) {
            Picasso.get().invalidate(new File(path));
            Log.d(Const.TAG, "onDetachedFromRecyclerView release " + path);
        }
    }
}



