package com.vv.threadsync.current;

import android.os.SystemClock;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Created by vivian on 2017/2/17.
 */

public class TestSemaphore {
    private final static String TAG = TestSemaphore.class.getSimpleName();

    private ExecutorService cachedPool = Executors.newCachedThreadPool();
    private Semaphore semaphore = new Semaphore(3);
    private final static int TIMEOUT = 500;

    public void start() {
        for (int i = 0; i < 10; i++) {
            cachedPool.execute(new Runnable() {
                @Override
                public void run() {
                    testSemaphore();
                }
            });
        }
    }

    public void testSemaphore() {
        try {
            boolean getAccquire = semaphore.tryAcquire(TIMEOUT, TimeUnit.MILLISECONDS);
            if (getAccquire) {
                Log.d(TAG, "tryAcquire true; now working; tname = " + Thread.currentThread().getName());
                SystemClock.sleep(2000);
                semaphore.release();
                Log.d(TAG, "work done, release semaphore; tname = " + Thread.currentThread().getName());
            } else {
                Log.d(TAG, "tryAcquire false." + "; tname = " + Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
