package com.vv.threadsync.current;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by vivian on 2017/2/17.
 */

public class TestFuture {
    private final static String TAG = TestFuture.class.getSimpleName();

    /**
     * get return of Callable
     */
    public void start() {
        // get return of Callable
        List<Future<String>> results = new ArrayList<Future<String>>();
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            results.add(pool.submit(new UserCallable()));
        }
        for (Future<String> res : results) {
            try {
                Log.d(TAG, "get Callable result " + res.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        pool.shutdown();
    }

    private class UserCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            String tname = Thread.currentThread().getName();
            Log.d(TAG, "UserCallable work " + tname);
            return tname;
        }
    }
}
