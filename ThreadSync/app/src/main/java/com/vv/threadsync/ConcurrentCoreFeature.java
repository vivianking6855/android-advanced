package com.vv.threadsync;

/**
 * Created by vivian on 2017/2/14.
 * blog demo: http://vivianking6855.github.io/Thread-Sync/
 */

public class ConcurrentCoreFeature {
    private final static String TAG = ConcurrentCoreFeature.class.getSimpleName();

    public void beginTest() {
        testFuture();
    }

    private void testFuture(){
    }

    private void testSemaphore(){

    }

//    - ReentrantLock 可重入的互斥锁定 Lock，功能类似synchronized，但要强大的多
//    - CountDownLatch 在完成其他线程中操作之前，允许一个或多个线程一直等待
//    - CyclicBarrier 允许一组线程互相等待，直到到达某个公共屏障点
//
//    - CompletionService ExecutorService的扩展，可以获得线程执行结果的
}
