package com.vv.threadsync.product;

import android.os.AsyncTask;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by vivian on 2017/2/13.
 */

public class ProductManager {
    private final static String TAG = ProductManager.class.getSimpleName();

    // Queue
    private final BlockingQueue<Product> mQueue = new LinkedBlockingDeque<Product>(2);

    public void beginTest() {
        //ExecutorService exec = Executors.newFixedThreadPool(3);
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new ConsumerRunnable("c1", mQueue));
        exec.execute(new ConsumerRunnable("c2", mQueue));
        exec.execute(new MakerRunnable("maker", mQueue));
    }

}
