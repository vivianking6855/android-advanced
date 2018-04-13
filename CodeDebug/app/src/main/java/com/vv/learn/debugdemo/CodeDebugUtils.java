package com.vv.learn.debugdemo;

import android.content.Context;
import android.os.Debug;
import android.util.Log;

import com.open.utislib.file.PathUtils;

import java.util.concurrent.TimeUnit;

/**
 * The type Code debug utils.
 */
public class CodeDebugUtils {
    private final static String TAG = "CodeDebugUtils";
    private final static String TRACING_FILE = "method.trace";

    /**
     * Start tracing long.
     *
     * @param context the context
     * @return the long nanos time of start time
     */
    public static void startTracing(final Context context) {
        final String path = PathUtils.getDiskCacheDir(context, TRACING_FILE).getPath();
        Debug.startMethodTracing(path);
        if (BuildConfig.DEBUG) Log.d(TAG, "MethodTracing path: " + path);
    }

    /**
     * Stop tracing without collect method tracing nano time
     */
    public static void stopTracing() {
        Debug.stopMethodTracing();
    }

    /**
     * Stop tracing long.
     *
     * @param lastTimeNanos the last time nanos
     * @return the long diffMs of start and stop tracing nano time
     */
    public static long stopTracing(long lastTimeNanos) {
        Debug.stopMethodTracing();
        long currentTimeNanos = System.nanoTime();
        long diffMs = TimeUnit.MILLISECONDS.convert(currentTimeNanos - lastTimeNanos, TimeUnit.NANOSECONDS);
        return diffMs;
    }

}
