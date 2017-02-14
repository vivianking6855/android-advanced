package com.vv.threadsync;

import android.os.SystemClock;
import android.util.Log;

/**
 * Created by vivian on 2017/2/10.
 */

public class SyncAdvanced {
    private final static String TAG = SyncAdvanced.class.getSimpleName();

    private final static String SKY = "\r\n Sky";
    private final static String RAIN = "\r\n Rain";
    private final static String CLOUD = "\r\n Cloud";
    private final static String SUN = "\r\n Sun";
    private Integer mLocker = 1;

    public void beginTest() {
        // new thread
        new Thread(new GameRunnable()).start();

        synchronized (mLocker) {
            try {
                Log.d(TAG, SKY);
                // wait, release locker
                mLocker.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.d(TAG, RAIN);
        }
    }

    private class GameRunnable implements Runnable {
        public void run() {
            SystemClock.sleep(100);

            synchronized (mLocker) {
                Log.d(TAG, CLOUD);
                // notify
                mLocker.notify();
                Log.d(TAG, SUN);
            }
        }
    }

}
