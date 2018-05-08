package com.debug.lib;

import android.util.Log;

/**
 * The type Const.
 */
public final class DebugMan {
    /**
     * The constant TAG.
     */
    public static final String TAG = "codedebug";

    /**
     * Enable.
     *
     * @param enable if false all debug method will not work
     */
    public static void enable(boolean enable){
        ENABLE_MODULE = enable;
    }

    /**
     * Enable dump debug.
     *
     * @param enable if false all method in {@link DebugDumpMan} will not work
     */
    public static void enableDumpDebug(boolean enable){
        ENABLE_DUMP_DEBUG = enable;
    }

    /**
     * Enable time debug.
     *
     * @param enable if false all method in {@link DebugTimeMan} will not work
     */
    public static void enableTimeDebug(boolean enable){
        ENABLE_TIME_DEBUG = enable;
    }

    /**
     * Enable hight debug.
     *
     * @param enable if true you need set property 'adb shell setprop log.tag.debug D'
     */
    public static void enableHighDebug(boolean enable){
        ENABLE_HIGH_DEBUG = enable;
    }

    /**
     * Enable memory debug.
     *
     * @param enable if false all method in {@link DebugMemoryMan} will not work
     */
    public static void enableMemoryDebug(boolean enable){
        ENABLE_MEMORY_DEBUG = enable;
    }

    // ------------------------------------------------debug mode
    /**
     * The constant ENABLE_MODULE. if false all debug method will not work
     */
    static boolean ENABLE_MODULE = true;

    /**
     * The constant ENABLE_DUMP_DEBUG. if false all method in {@link DebugDumpMan} will not work
     */
    static boolean ENABLE_DUMP_DEBUG = true;

    /**
     * The constant ENABLE_TIME_DEBUG.if false all method in {@link DebugTimeMan} will not work
     */
    static boolean ENABLE_TIME_DEBUG = true;

    /**
     * The constant HIGH_DEBUG. if enable you need set property 'adb shell setprop log.tag.debug D'
     */
    static boolean ENABLE_HIGH_DEBUG = false;

    /**
     * The constant HIGH_DEBUG. if false all method in {@link DebugMemoryMan} will not work
     */
    static boolean ENABLE_MEMORY_DEBUG = false;

    /**
     * The constant CODE_DEBUG.
     * you need set property 'adb shell setprop log.tag.debug D'
     * and restart application
     */
    private static final boolean DEBUG_HIGH = Log.isLoggable(TAG, Log.DEBUG);

    /**
     * The constant DEBUG_LOW.
     */
    private static final boolean DEBUG_LOW = BuildConfig.DEBUG;

    /**
     * The constant CODE_DEBUG. if false all log will not print
     */
    static final boolean CODE_DEBUG = ENABLE_HIGH_DEBUG ? DEBUG_HIGH : DEBUG_LOW;


}
