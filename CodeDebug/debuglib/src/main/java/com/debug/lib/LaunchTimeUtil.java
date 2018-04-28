package com.debug.lib;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.os.MessageQueue;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import static com.debug.lib.CodeDebugConst.CODE_DEBUG;
import static com.debug.lib.CodeDebugConst.ENABLE_MODULE;
import static com.debug.lib.CodeDebugConst.ENABLE_TIME_DEBUG;
import static com.debug.lib.CodeDebugConst.TAG;

/**
 * The type Time cost util.
 */
public class LaunchTimeUtil {
    private long lastTimeNanos;
    private long currentTimeNanos;

    private static volatile LaunchTimeUtil instance;

    private LaunchTimeUtil() {

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static LaunchTimeUtil getInstance() {
        if (instance == null) {
            synchronized (CodeDebugUtil.class) {
                if (instance == null) {
                    instance = new LaunchTimeUtil();
                }
            }
        }
        return instance;
    }

    /**
     * Start.
     */
    public void start() {
        if (!ENABLE_MODULE || !CODE_DEBUG || ENABLE_TIME_DEBUG) {
            return;
        }
        lastTimeNanos = System.nanoTime();
        Log.d(TAG, "[LaunchTimeUtil start]");
    }

    /**
     * Stop.
     *
     * @param title the title
     */
    @SuppressLint("LogConditional")
    public void stop(String title) {
        if (!ENABLE_MODULE || !CODE_DEBUG || ENABLE_TIME_DEBUG) {
            return;
        }
        Log.d(TAG, "[LaunchTimeUtil stop]");
        diff(title);
        lastTimeNanos = currentTimeNanos;
    }

    /**
     * Diff.
     *
     * @param title the title
     */
    @SuppressLint("LogConditional")
    public void diff(String title) {
        if (!ENABLE_MODULE || !CODE_DEBUG || ENABLE_TIME_DEBUG) {
            return;
        }
        currentTimeNanos = System.nanoTime();
        Log.d(TAG, "[LaunchTimeUtil diff] " + title + " diffMs: "
                + TimeUnit.MILLISECONDS.convert(currentTimeNanos - lastTimeNanos, TimeUnit.NANOSECONDS) + "ms");
    }

    /**
     * Register idle handler.
     */
    public void registerIdleHandler(final IdleHandlerListener listener) {
        if (!ENABLE_MODULE || !CODE_DEBUG || ENABLE_TIME_DEBUG) {
            return;
        }

        // IdleHandler to collect diffMs of activity launch nano time
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @SuppressLint("LogConditional")
            @Override
            public boolean queueIdle() {
                currentTimeNanos = System.nanoTime();
                long diffMs = TimeUnit.MILLISECONDS.convert(currentTimeNanos - lastTimeNanos, TimeUnit.NANOSECONDS);
                Log.d(TAG, "IdleHandler diffMs: +" + diffMs + "ms");
                lastTimeNanos = currentTimeNanos;

                if(listener != null){
                    listener.idleHandlerDone();
                }

                return false;
            }
        });
    }

    public interface IdleHandlerListener{
        void idleHandlerDone();
    }

}


