package com.vv.threadsync.current;

import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by vivian on 2017/2/17.
 */

public class TestReentrantLock {
    private final static String TAG = TestReentrantLock.class.getSimpleName();

    private Lock mLock;
    private Condition fullCondtion;
    private Condition emptyCondtion;

    private List<Patient> mPatientList;
    private final static int CAPACITY = 20; // max person to wait in hospital

    private AtomicBoolean isTimeOver = new AtomicBoolean(false);

    private ExecutorService cachedPool = Executors.newCachedThreadPool();

    public void start() {
        mLock = new ReentrantLock();
        fullCondtion = mLock.newCondition();
        emptyCondtion = mLock.newCondition();
        mPatientList = new ArrayList<Patient>();

        cachedPool.execute(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                isTimeOver.set(true);
            }
        });

        cachedPool.execute(doctorRunnable);
        cachedPool.execute(doctorRunnable);
        cachedPool.execute(patientRunnable);
    }

    Runnable doctorRunnable = new Runnable() {
        @Override
        public void run() {
            while (!isTimeOver.get()) {
                mLock.lock();
                try {
                    // if empty queue, stop take person
                    while (mPatientList.size() <= 0) {
                        Log.d(TAG, "empty, now wait " + Thread.currentThread().getName());
                        emptyCondtion.await();
                    }
                    String name = mPatientList.get(0).name;
                    mPatientList.remove(0);
                    fullCondtion.signal();
                    Log.d(TAG, "take over name-" + name + ";" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    mLock.unlock();
                }
            }

        }
    };

    Runnable patientRunnable = new Runnable() {
        @Override
        public void run() {
            while (!isTimeOver.get()) {
                mLock.lock();
                try {
                    // if wait queue full, stop add person
                    while (mPatientList.size() >= CAPACITY) {
                        Log.d(TAG, "full, now wait " + Thread.currentThread().getName());
                        fullCondtion.await();
                    }
                    int id = mPatientList.size() + 1;
                    mPatientList.add(new Patient(id, "name-" + id));
                    emptyCondtion.signal();
                    Log.d(TAG, "add name-" + id + ";" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    mLock.unlock();
                }
            }
        }
    };

    private class Patient {
        private String name;
        private int id;

        public Patient(int id, String name) {
            this.name = name;
            this.id = id;
        }
    }
}
