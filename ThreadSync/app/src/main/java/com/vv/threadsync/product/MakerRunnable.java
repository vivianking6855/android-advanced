package com.vv.threadsync.product;

import android.os.SystemClock;
import android.util.Log;

import com.vv.threadsync.Const;

import java.util.concurrent.BlockingQueue;

/**
 * Created by vivian on 2017/2/13.
 */

public class MakerRunnable implements Runnable {
    private final static String TAG = MakerRunnable.class.getSimpleName();

    private String name;
    private BlockingQueue<Product> mQueue;

    public MakerRunnable(String name, BlockingQueue<Product> queue) {
        this.name = name;
        mQueue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < Const.MAX_NUM; i++) {
            Product item = new Product(i, "name " + i);
            try {
                mQueue.put(item);
                Log.d(TAG, name + " : " + item.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SystemClock.sleep(Const.TIME_MAKE);
        }
    }
}
