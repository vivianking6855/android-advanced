package com.vv.frescodemo;

import android.app.Application;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by vivian on 2017/2/23.
 */

public class UserApplication extends Application {
    private final static String TAG = UserApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        Log.d(TAG, " Fresco.initialize");
    }

}
