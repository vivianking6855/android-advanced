package com.demo.loader.photo;

import android.database.Cursor;

public class PhotoItem {
    String path;
    String title;

    public static PhotoItem fromCursor(Cursor cursor) {
        PhotoItem item = new PhotoItem();
        item.path = cursor.getString(cursor.getColumnIndexOrThrow(PhotoDataHelper.ItemsDBInfo.PATH));
        item.title = cursor.getString(cursor.getColumnIndexOrThrow(PhotoDataHelper.ItemsDBInfo.TITLE));
        return item;
    }
}
