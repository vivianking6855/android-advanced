package com.vv.threadsync.product;

import android.os.SystemClock;
import android.util.Log;

import com.vv.threadsync.Const;

import java.util.concurrent.BlockingQueue;

/**
 * Created by vivian on 2017/2/13.
 */

public class ConsumerRunnable implements Runnable {
    private final static String TAG = ConsumerRunnable.class.getSimpleName();

    private String name;
    private BlockingQueue<Product> mQueue;

    public ConsumerRunnable(String name, BlockingQueue<Product> queue) {
        this.name = name;
        mQueue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < Const.MAX_NUM; i++) {
            // consume product
            try {
                Product item = mQueue.take();
                Log.d(TAG, name + " : " + item.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            SystemClock.sleep(Const.TIME_CONSUME);
        }
    }
}