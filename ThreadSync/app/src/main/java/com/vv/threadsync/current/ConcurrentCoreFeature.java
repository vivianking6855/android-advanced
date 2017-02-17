package com.vv.threadsync.current;

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
        //new TestCountDownLatch().start();
        new TestCyclicBarrier().start();
    }

//    - CompletionService ExecutorService的扩展，可以获得线程执行结果的

}
