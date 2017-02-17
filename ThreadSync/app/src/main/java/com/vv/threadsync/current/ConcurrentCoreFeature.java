package com.vv.threadsync.current;

import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by vivian on 2017/2/14.
 * blog demo: http://vivianking6855.github.io/Thread-Sync/
 */

public class ConcurrentCoreFeature {
    private final static String TAG = ConcurrentCoreFeature.class.getSimpleName();

    public void beginTest() {
        // new TestFuture().start();
        //new TestSemaphore().start();
        //new TestReentrantLock().start();
        new TestCountDownLatch().start();
    }

//    - CompletionService ExecutorService的扩展，可以获得线程执行结果的

}
