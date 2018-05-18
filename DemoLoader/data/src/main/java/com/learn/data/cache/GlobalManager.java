package com.learn.data.cache;


/**
 * Created by vivian on 2017/11/13.
 * global variable manager,
 * light data cache and share
 * if you need large data cache,user LruCache or DiskCache
 */
public class GlobalManager {
    public int mDataCount;

    private static volatile GlobalManager instance;

    private GlobalManager() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static GlobalManager getInstance() {
        if (instance == null) {
            synchronized (GlobalManager.class) {
                if (instance == null) {
                    instance = new GlobalManager();
                }
            }
        }
        return instance;
    };

    public void init(){

    }

    public void release(){

    }
}
