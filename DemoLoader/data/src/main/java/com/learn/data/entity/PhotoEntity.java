package com.learn.data.entity;

import android.database.Cursor;

import com.learn.data.repository.PhotoRepo;

public class PhotoEntity {
    public String path;
    public String title;

    public static PhotoEntity fromCursor(Cursor cursor) {
        PhotoEntity item = new PhotoEntity();
        item.path = cursor.getString(cursor.getColumnIndexOrThrow(PhotoRepo.ItemsDBInfo.PATH));
        item.title = cursor.getString(cursor.getColumnIndexOrThrow(PhotoRepo.ItemsDBInfo.TITLE));
        return item;
    }

    @Override
    public String toString() {
        return "PhotoEntity : { path:" + path + ",title:" + title + "}";
    }
}
