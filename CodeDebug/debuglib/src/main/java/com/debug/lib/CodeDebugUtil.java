package com.debug.lib;

import android.content.Context;
import android.os.Debug;
import android.os.Environment;
import android.util.Log;

import com.debug.lib.task.FileTask;

import java.io.File;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import static com.debug.lib.CodeDebugConst.CODE_DEBUG;
import static com.debug.lib.CodeDebugConst.ENABLE_CODE_DEBUG;
import static com.debug.lib.CodeDebugConst.ENABLE_MODULE;

/**
 * The type Code debug utils.
 * singleton mode
 * use it like this
 * // init CodeDebugUtil
 * CodeDebugUtil.getInstance().init(this);
 * // start tracing, it must call in UI thread
 * CodeDebugUtil.getInstance().startTracing();
 * // stop tracing, tracing file will not appear since you call stopTracing method
 * CodeDebugUtil.getInstance().stopTracing();
 */
public class CodeDebugUtil {
    private final static String TAG = "CodeDebugUtil";
    private final static String TRACING_FILE = "method.trace";
    private final static String HPROF_FILE = "launch.hprof";

    private Reference<Context> mWeakContext;
    private String mTracePath;
    private String mDumpPath;

    private static volatile CodeDebugUtil instance;

    private CodeDebugUtil() {

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static CodeDebugUtil getInstance() {
        if (instance == null) {
            synchronized (CodeDebugUtil.class) {
                if (instance == null) {
                    instance = new CodeDebugUtil();
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
     * @return the instance
     */
    public void init(Context context) {
        if (!ENABLE_MODULE || !CODE_DEBUG || !ENABLE_CODE_DEBUG) {
            return;
        }
        mWeakContext = new WeakReference<>(context);
        mTracePath = getDiskCacheDir(mWeakContext.get(), TRACING_FILE).getPath();
        mDumpPath = getDiskCacheDir(mWeakContext.get(), HPROF_FILE).getPath();

        Log.d(TAG, "[CodeDebugUtil init] MethodTracing path: " + mTracePath + "; \r\n Heap Dump path: " + mDumpPath);
    }


    /**
     * Start tracing long.
     * attention: this method must run on UI thread, otherwise it will not work
     *
     * @return the long nanos time of start time
     */
    public void startTracing() {
        if (!ENABLE_MODULE || !CODE_DEBUG || !ENABLE_CODE_DEBUG) {
            return;
        }

        Log.d(TAG, "[CodeDebugUtil startTracing]");
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
        if (!ENABLE_MODULE || !CODE_DEBUG || !ENABLE_CODE_DEBUG) {
            return;
        }

        Log.d(TAG, "[CodeDebugUtil stopTracing]");
        Debug.stopMethodTracing();
    }

    /**
     * Start dump.
     */
    public void startDump() {
        if (!ENABLE_MODULE || !CODE_DEBUG || !ENABLE_CODE_DEBUG) {
            return;
        }

        Log.d(TAG, "[CodeDebugUtil startDump]");
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
        if (mWeakContext == null || mWeakContext.get() == null) {
            return false;
        }

        return true;
    }

    private File getDiskCacheDir(Context context, String uniqueName) {
        return new File(getDiskCacheDir(context) + File.separator + uniqueName);
    }

    private String getDiskCacheDir(Context context) {
        String cachePath;
        // sd card exist and not removed
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }


}
