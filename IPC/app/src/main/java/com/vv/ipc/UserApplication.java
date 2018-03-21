package com.vv.ipc;

import android.app.Application;
import android.os.Process;
import android.util.Log;

import com.vv.ipc.utils.Utils;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

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
