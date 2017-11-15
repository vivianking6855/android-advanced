package com.vv.cache.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;


/**
 * The type Path utils.
 */
public final class PathUtils {
    private static final String TAG = "PathUtils";

    /**
     * Gets disk cache directory File.
     * it will return File created with internal cache path or external cache path if sdcard exist and not removed.
     * <p> path like this:
     * external sdcard cache path : /storage/emulated/0/Android/data/应用包名/cache/XXX (sdcardd)
     * internal cache path : sdcard\Android\data\<package name>\cache\
     *
     * @param context    the context
     * @param uniqueName the unique name
     * @return the disk cache directory File
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        return new File(getDiskCacheDir(context) + File.separator + uniqueName);
    }

    /**
     * Gets disk cache directory path.
     * it will return internal cache path or external cache path if sdcard exist and not removed
     * <p> path like this:
     * external sdcard cache path : /storage/emulated/0/Android/data/应用包名/cache/XXX (sdcardd)
     * internal cache path :
     *
     * @param context the context
     * @return the disk cache directory path
     */
    public static String getDiskCacheDir(Context context) {
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

    /**
     * Create dir.
     *
     * @param file the file
     */
    public static void createDir(File file) {
        if (file != null && !file.exists()) {
            file.mkdirs();
        }
    }

}
