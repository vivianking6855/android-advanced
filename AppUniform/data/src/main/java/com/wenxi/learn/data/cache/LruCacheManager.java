package com.wenxi.learn.data.cache;

import android.content.Context;
import android.support.v4.util.LruCache;

import com.open.utislib.device.DeviceUtils;

/**
 * Created by vivian on 2017/11/16.
 */

public enum LruCacheManager {
    INSTANCE;

    private final String TAG = LruCacheManager.class.getSimpleName();

    // lru cache
    private static LruCache<String, String> mLruStringCache;

    public static int getDefaultLruCacheSize() {
        return (int) DeviceUtils.getMaxMemory() / 8;
    }

    /**
     * Gets disk lru cache.
     *
     * @param context the context
     * @return the disk lru cache
     */
    public LruCache<String, String> getLruCache(Context context) {
        if (mLruStringCache == null) {
            // create cache path
            mLruStringCache = new LruCache((int) DeviceUtils.getMaxMemory() / 8) {
                @Override
                protected int sizeOf(Object key, Object value) {
                    return super.sizeOf(key, value);
                }
            };
        }

        return mLruStringCache;
    }

}
