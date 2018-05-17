package com.learn.data.repository;

import android.content.Context;

import com.learn.data.cache.DiskCacheManager;
import com.learn.data.cache.GlobalManager;
import com.learn.data.common.DiskLruCacheUtils;

public class DataModule {
    private static volatile DataModule instance;

    private Context appContext;

    private DataModule() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DataModule getInstance() {
        if (instance == null) {
            synchronized (DataModule.class) {
                if (instance == null) {
                    instance = new DataModule();
                }
            }
        }
        return instance;
    };

    public void install(Context context){
        appContext = context.getApplicationContext();
        // init cache
        GlobalManager.getInstance().init();

        DiskCacheManager.getInstance().init(context);
        DiskLruCacheUtils.setDiskLruCache(DiskCacheManager.getInstance().getDiskLruCache(context));
    }

    public void uninstall(){
        GlobalManager.getInstance().release();
        DiskCacheManager.getInstance().release();
    }
}
