package com.vv.ipc;

import android.app.Application;
import android.os.Process;
import android.util.Log;

import com.vv.ipc.utils.Utils;

/**
 * Created by vivian on 2017/2/14.
 */

public class UserApplication extends Application {
    private static final String TAG = UserApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        String processName = Utils.getProcessName(getApplicationContext(),
                Process.myPid());
        Log.d(TAG, "application start, process name: " + processName);
    }
}
