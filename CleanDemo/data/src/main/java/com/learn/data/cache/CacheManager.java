package com.learn.data.cache;

import android.content.Context;

import com.learn.data.common.DiskLruCacheUtils;

/**
 * CacheManager include LruCache and DishCache
 */

public enum CacheManager {
    INSTANCE;

    public void init(Context context) {
        DiskCacheManager.INSTANCE.init(context);
        DiskLruCacheUtils.setDiskLruCache(DiskCacheManager.INSTANCE.getDiskLruCache(context));
    }

    public void release() {
        DiskCacheManager.INSTANCE.release();
    }

    public void getDiskCacheManager(){

    }

}
