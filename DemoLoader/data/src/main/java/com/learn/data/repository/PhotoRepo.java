package com.learn.data.repository;

import android.content.Context;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;

public class PhotoRepo {
    private final static Uri PHOTO_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    // order by add date: DESC. you may change to ASC if needed
    private final static String SORT_ORDER = MediaStore.Images.Media.DATE_ADDED + " DESC ";
    // image projection
    private final static String[] IMAGE_PROJECTION = {
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media._ID
    };

    public static CursorLoader getCursorLoader(Context context) {
        return new CursorLoader(context, PHOTO_URI,
                IMAGE_PROJECTION, null, null, SORT_ORDER);
    }

    public static final class ItemsDBInfo implements BaseColumns {
        private ItemsDBInfo() {
        }

        public static final String PATH = MediaStore.Images.Media.DATA;
        public static final String TITLE = MediaStore.Images.Media.DISPLAY_NAME;
    }

}
