package com.vv.threadsync.current;

import android.os.SystemClock;
import android.util.Log;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by vivian on 2017/2/17.
 */

public class TestCyclicBarrier {
    private final static String TAG = TestCyclicBarrier.class.getSimpleName();
    private ExecutorService threadPool = Executors.newFixedThreadPool(3);
    private CyclicBarrier mBarrier = new CyclicBarrier(3);

    public void start() {
        threadPool.execute(runner);
        threadPool.execute(runner);
        threadPool.execute(runner);
    }

    Runnable runner = new Runnable() {
        @Override
        public void run() {
            SystemClock.sleep(500);
            Log.d(TAG, "runner ready " + Thread.currentThread().getName());
            try {
                mBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "start run " + Thread.currentThread().getName());
        }
    };
}
