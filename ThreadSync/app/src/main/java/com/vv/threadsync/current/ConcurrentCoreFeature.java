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
        //testFuture();
        //testSemaphore();
        testReentrantLock();
    }

    private void testFuture(){
        new TestFuture().start();
    }

    /**
     * 控制资源被允许访问的线程数
     */
    private void testSemaphore() {
        new TestSemaphore().start();
    }

    /**
     * each doctor room max 3 person
     * patient may increase before time over
     */
    private void testReentrantLock() {
        new TestReentrantLock().start();
    }


//    - CompletionService ExecutorService的扩展，可以获得线程执行结果的
//    - CountDownLatch 在完成其他线程中操作之前，允许一个或多个线程一直等待
//    - CyclicBarrier 允许一组线程互相等待，直到到达某个公共屏障点
//    - [CopyOnWriteArrayList](http://ifeve.com/java-copy-on-write/)
//            - CopyOnWrite容器即写时复制的容器。
//            - 通俗的理解是当我们往一个容器添加元素的时候，不直接往当前容器添加，而是先将当前容器进行Copy，复制出一个新的容器，然后新的容器里添加元素，添加完元素之后，再将原容器的引用指向新的容器。
//            - 这样做的好处是我们可以对CopyOnWrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素。
//            - 所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器。
//            - ConcurrentHashMap
}
