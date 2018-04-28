package com.debug.lib.task;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * File Manager with SingleThreadExecutor pool
 */
public class FileTask {
    /**
     * The constant THREAD_POOL_EXECUTOR, which is instance of SingleThreadExecutor
     */
    public static final Executor THREAD_POOL_EXECUTOR;

    static {
        THREAD_POOL_EXECUTOR = Executors.newSingleThreadExecutor();
    }

}
