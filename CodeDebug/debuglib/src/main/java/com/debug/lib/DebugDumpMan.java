package com.debug.lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Debug;
import android.os.Environment;
import android.util.Log;

import com.debug.lib.task.FileTask;

import java.io.File;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import static com.debug.lib.DebugMan.CODE_DEBUG;
import static com.debug.lib.DebugMan.ENABLE_DUMP_DEBUG;
import static com.debug.lib.DebugMan.ENABLE_MODULE;

/**
 * The type Code debug utils.
 * singleton mode
 * use it like this
 * // init DebugDumpMan
 * DebugDumpMan.getInstance().init(this);
 * // start tracing, it must call in UI thread
 * DebugDumpMan.getInstance().startTracing();
 * // stop tracing, tracing file will not appear since you call stopTracing method
 * DebugDumpMan.getInstance().stopTracing();
 */
public class DebugDumpMan {
    private final static String TAG = "DebugDumpMan";
    private final static String TRACING_FILE = "method.trace";
    private final static String HPROF_FILE = "launch.hprof";

    private Reference<Context> mWeakContext;
    private String mTracePath;
    private String mDumpPath;

    private static volatile DebugDumpMan instance;

    private DebugDumpMan() {

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DebugDumpMan getInstance() {
        if (instance == null) {
            synchronized (DebugDumpMan.class) {
                if (instance == null) {
                    instance = new DebugDumpMan();
                }
            }
        }
        return instance;
    }

    /**
     * init.
     * you must call it before you call all other method
     *
     * @param context the context
     */
    @SuppressLint("LogConditional")
    public void init(Context context) {
        if (isDisable()) {
            return;
        }

        if (mWeakContext != null && mWeakContext.get() != null) {
            return;
        }

        mWeakContext = new WeakReference<>(context);
        mTracePath = getDiskCacheDir(mWeakContext.get(), TRACING_FILE).getPath();
        mDumpPath = getDiskCacheDir(mWeakContext.get(), HPROF_FILE).getPath();

        Log.d(TAG, "[DebugDumpMan init] MethodTracing path: " + mTracePath + "; \r\n Heap Dump path: " + mDumpPath);
    }


    /**
     * Start tracing long.
     * attention: this method must run on UI thread, otherwise it will not work
     */
    public void startTracing() {
        if (isDisable()) {
            return;
        }

        Log.d(TAG, "[DebugDumpMan startTracing]");
        if (!checkContextValidation()) {
            return;
        }

        Debug.startMethodTracing(mTracePath);
    }

    /**
     * Stop tracing without collect method tracing nano time
     * attention: this method must run on UI thread, otherwise it will not work
     * tracing file will not create since stopTracing called.
     */
    public void stopTracing() {
        if (isDisable()) {
            return;
        }

        Log.d(TAG, "[DebugDumpMan stopTracing]");
        Debug.stopMethodTracing();
    }

    /**
     * Start dump.
     * dump hprof data in work thread
     */
    public void startDump() {
        if (isDisable()) {
            return;
        }

        Log.d(TAG, "[DebugDumpMan startDump]");
        if (!checkContextValidation()) {
            return;
        }

        FileTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Debug.dumpHprofData(mDumpPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean checkContextValidation() {
        return mWeakContext != null && mWeakContext.get() != null;
    }

    private File getDiskCacheDir(Context context, String uniqueName) {
        String path = getDiskCacheDir(context);
        if (path == null || path.isEmpty()) {
            return null;
        }
        return new File(path + File.separator + uniqueName);
    }

    private String getDiskCacheDir(Context context) {
        String cachePath = "";
        try {
            // sd card exist and not removed
            if (Environment.MEDIA_MOUNTED.equals(Environment
                    .getExternalStorageState())
                    || !Environment.isExternalStorageRemovable()) {
                cachePath = context.getExternalCacheDir().getPath();
            } else {
                cachePath = context.getCacheDir().getPath();
            }
        } catch (Exception e) {
            Log.d(TAG, "getDiskCacheDir ex", e);
        }

        return cachePath;
    }

    private boolean isDisable() {
        return !ENABLE_MODULE || !CODE_DEBUG || !ENABLE_DUMP_DEBUG;
    }

}
