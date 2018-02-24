package com.vv.appuniform.cache;

import android.content.Context;

import com.open.utislib.cache.DiskLruCacheUtils;

/**
 * Created by vivian on 2017/11/16.
 *
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

}
