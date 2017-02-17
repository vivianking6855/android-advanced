package com.vv.threadsync.current;

import android.os.SystemClock;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by vivian on 2017/2/17.
 */

public class TestCountDownLatch {
    private final static String TAG = TestCountDownLatch.class.getSimpleName();

    private ExecutorService threadPool = Executors.newFixedThreadPool(3);
    private CountDownLatch mCount = new CountDownLatch(3);

    public void start() {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mCount.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "all works done " + Thread.currentThread().getName());
            }
        });
        threadPool.execute(staff);
        threadPool.execute(staff);
        threadPool.execute(staff);
    }

    Runnable staff = new Runnable() {
        @Override
        public void run() {
            SystemClock.sleep(500);
            mCount.countDown();
            Log.d(TAG, "work done " + Thread.currentThread().getName());
        }
    };
}
