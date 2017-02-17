package com.vv.threadsync;

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

/**
 * Created by vivian on 2017/2/14.
 * blog demo: http://vivianking6855.github.io/Thread-Sync/
 */

public class ConcurrentCoreFeature {
    private final static String TAG = ConcurrentCoreFeature.class.getSimpleName();

    private ExecutorService cachedPool = Executors.newCachedThreadPool();

    public void beginTest() {
        //testFuture();
        testSemaphore();
    }

    private class UserCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            String tname = Thread.currentThread().getName();
            Log.d(TAG, "UserCallable work " + tname);
            return tname;
        }
    }

    /**
     * get return of Callable
     */
    private void testFuture() {
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

    /**
     * 控制资源被允许访问的线程数
     */
    private void testSemaphore() {
        final TestSemaphore testSemaphore = new TestSemaphore();
        for(int i=0; i< 10; i++) {
            cachedPool.execute(new Runnable() {
                @Override
                public void run() {
                    testSemaphore.start();
                }
            });
        }
    }

    private class TestSemaphore {
        private Semaphore semaphore = new Semaphore(3);
        private final static int TIMEOUT = 500;

        public void start() {
            try {
                boolean getAccquire = semaphore.tryAcquire(TIMEOUT, TimeUnit.MILLISECONDS);
                if(getAccquire) {
                    Log.d(TAG, "tryAcquire true; now working; tname = " + Thread.currentThread().getName());
                    SystemClock.sleep(2000);
                    semaphore.release();
                    Log.d(TAG, "work done, release semaphore; tname = " + Thread.currentThread().getName());
                }else {
                    Log.d(TAG, "tryAcquire false." + "; tname = " + Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


//    - CompletionService ExecutorService的扩展，可以获得线程执行结果的
//    - ReentrantLock 可重入的互斥锁定 Lock，功能类似synchronized，但要强大的多
//    - CountDownLatch 在完成其他线程中操作之前，允许一个或多个线程一直等待
//    - CyclicBarrier 允许一组线程互相等待，直到到达某个公共屏障点
//    - [CopyOnWriteArrayList](http://ifeve.com/java-copy-on-write/)
//            - CopyOnWrite容器即写时复制的容器。
//            - 通俗的理解是当我们往一个容器添加元素的时候，不直接往当前容器添加，而是先将当前容器进行Copy，复制出一个新的容器，然后新的容器里添加元素，添加完元素之后，再将原容器的引用指向新的容器。
//            - 这样做的好处是我们可以对CopyOnWrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素。
//            - 所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器。
//            - ConcurrentHashMap
}
