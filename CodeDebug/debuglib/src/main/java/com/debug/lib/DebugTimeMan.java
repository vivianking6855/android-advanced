package com.debug.lib;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.os.MessageQueue;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import static com.debug.lib.DebugMan.CODE_DEBUG;
import static com.debug.lib.DebugMan.ENABLE_MODULE;
import static com.debug.lib.DebugMan.ENABLE_TIME_DEBUG;
import static com.debug.lib.DebugMan.TAG;

/**
 * The type Time cost util.
 */
@SuppressWarnings("ALL")
public class DebugTimeMan {
    private long lastTimeNanos;
    private long currentTimeNanos;

    private static volatile DebugTimeMan instance;

    private DebugTimeMan() {

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DebugTimeMan getInstance() {
        if (instance == null) {
            synchronized (DebugDumpMan.class) {
                if (instance == null) {
                    instance = new DebugTimeMan();
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

    /**
     * Register idle handler.
     */
    public void registerIdleHandler(final IdleHandlerListener listener) {
        if (isDisable()) {
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

    private boolean isDisable() {
        return !ENABLE_MODULE || !CODE_DEBUG || !ENABLE_TIME_DEBUG;
    }

}


