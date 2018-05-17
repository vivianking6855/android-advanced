package com.clean.debug;

import android.util.Log;

import com.clean.BuildConfig;

/**
 * The type Default config.
 */
public class DefaultConfig {

    /**
     * false will disable all log debug.
     */
    public static final boolean ENABLE = true;

    /**
     * LOG_TAG for high level debug with property: 'adb shell setprop log.tag.codedebug D'
     */
    private static final String LOG_TAG = "codedebug";

    /**
     * The constant CODE_DEBUG.
     * you need set property 'adb shell setprop log.tag.codedebug D'
     * and restart application
     */
    public static final boolean DEBUG_HIGH = Log.isLoggable(LOG_TAG, Log.DEBUG);

    /**
     * The constant DEBUG_LOW.
     */
    public static final boolean DEBUG_LOW = BuildConfig.DEBUG;

}
