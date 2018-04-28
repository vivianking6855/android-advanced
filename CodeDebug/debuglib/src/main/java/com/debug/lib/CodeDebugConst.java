package com.debug.lib;

import android.util.Log;

/**
 * The type Const.
 */
public final class CodeDebugConst {
    /**
     * The constant TAG.
     */
    public static final String TAG = "codedebug";

    /**
     * The constant HIGH_DEBUG.
     */
    public static final boolean ENABLE_HIGH_DEBUG = false;

    // ------------------------------------------------debug mode

    /**
     * The constant ENABLE_MODULE. if false all debug method will not run
     */
    public static final boolean ENABLE_MODULE = true;

    /**
     * The constant ENABLE_CODE_DEBUG. if false all method in {@link CodeDebugUtil} will not run
     */
    public static final boolean ENABLE_CODE_DEBUG = true;

    /**
     * The constant ENABLE_TIME_DEBUG.if false all method in {@link LaunchTimeUtil} will not run
     */
    public static final boolean ENABLE_TIME_DEBUG = false;

    /**
     * The constant CODE_DEBUG.
     * you need set property 'adb shell setprop log.tag.debug D'
     * and restart application
     */
    public static final boolean DEBUG_HIGH = Log.isLoggable(TAG, Log.DEBUG);

    /**
     * The constant DEBUG_LOW.
     */
    public static final boolean DEBUG_LOW = BuildConfig.DEBUG;

    /**
     * The constant CODE_DEBUG. if false all log will not print
     */
    public static final boolean CODE_DEBUG = ENABLE_HIGH_DEBUG ? DEBUG_HIGH : DEBUG_LOW;
}
