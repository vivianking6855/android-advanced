package com.clean.debug;

import com.clean.businesscommon.common.Const;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class DebugBusiness {
    private static DefaultConfig sConfig;

    public static void install() {
        if(sConfig == null){
            sConfig = new DefaultConfig();
        }

        // init orhanobut logger
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                //.showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                //.methodCount(1)         // (Optional) How many method line to show. Default 2
                //.methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag(Const.TAG_APP)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return sConfig.ENABLE && sConfig.DEBUG_LOW;
            }
        });
    }

    public static void uninstall() {
        sConfig = null;
        Logger.clearLogAdapters();
    }

}
