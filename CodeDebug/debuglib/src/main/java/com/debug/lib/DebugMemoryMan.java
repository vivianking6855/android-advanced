package com.debug.lib;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import static com.debug.lib.DebugMan.CODE_DEBUG;
import static com.debug.lib.DebugMan.ENABLE_MEMORY_DEBUG;
import static com.debug.lib.DebugMan.ENABLE_MODULE;
import static com.debug.lib.DebugMan.TAG;

public class DebugMemoryMan {
    private long lastTimeNanos;
    private long currentTimeNanos;

    // Memory MB unit
    private final static int MB_UNIT = 1024 * 1024;

    private static volatile DebugMemoryMan instance;

    private DebugMemoryMan() {

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DebugMemoryMan getInstance() {
        if (instance == null) {
            synchronized (DebugDumpMan.class) {
                if (instance == null) {
                    instance = new DebugMemoryMan();
                }
            }
        }
        return instance;
    }

    /**
     * Start.
     */
    public void start() {
        if (isDisable()) {
            return;
        }
        lastTimeNanos = System.nanoTime();
        Log.d(TAG, "[DebugTimeMan start]");
    }

    /**
     * Stop.
     *
     * @param title the title
     */
    @SuppressLint("LogConditional")
    public void stop(String title) {
        if (isDisable()) {
            return;
        }
        Log.d(TAG, "[DebugTimeMan stop]");
        diff(title);
        lastTimeNanos = currentTimeNanos;
    }

    /**
     * Diff.
     *
     * @param title the title
     */
    @SuppressWarnings("WeakerAccess")
    @SuppressLint("LogConditional")
    public void diff(String title) {
        if (isDisable()) {
            return;
        }
        currentTimeNanos = System.nanoTime();
        Log.d(TAG, "[DebugTimeMan diff] " + title + " diffMs: "
                + TimeUnit.MILLISECONDS.convert(currentTimeNanos - lastTimeNanos, TimeUnit.NANOSECONDS) + "ms");
    }

    private boolean isDisable() {
        return !ENABLE_MODULE || !CODE_DEBUG || !ENABLE_MEMORY_DEBUG;
    }

    public void getMemoryInfo(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // avail memory 以m为单位
        int memClass = activityManager.getMemoryClass();
        Log.d(TAG, "[mermoy avail (MB)]: " + memClass);

        // MemoryInfo: all memory information here
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memInfo);
        // total
        long totalMem = memInfo.totalMem / MB_UNIT;
        // available
        long availMem = memInfo.availMem / MB_UNIT;
        // low memory kill threshold
        long threshold = memInfo.threshold / MB_UNIT;
        // is low memory
        boolean isLowMem = memInfo.lowMemory;
        Log.d(TAG, "[mermoy total/avail/threshold/isLowMem (MB)]: "
                + totalMem
                + " / " + availMem
                + " / " + threshold
                + " / " + isLowMem);
    }


}
