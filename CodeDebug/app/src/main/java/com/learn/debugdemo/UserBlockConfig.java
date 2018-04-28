package com.learn.debugdemo;

import com.learn.blockmonitor.data.config.Config;

public class UserBlockConfig extends Config{
    private static final long TIME_USER_BLOCK = 160 ;// 160ms = 10*16.6ms

    @Override
    public long getBlockThreshold() {
        return TIME_USER_BLOCK;
    }

}
