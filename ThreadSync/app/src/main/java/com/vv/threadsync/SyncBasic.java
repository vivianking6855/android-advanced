package com.vv.threadsync;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.Random;

/**
 * Created by vivian on 2017/2/10.
 */

public class SyncBasic {
    private final static String TAG = SyncBasic.class.getSimpleName();

    private int countFlag = 1;
    private static Integer sLocker = 1;
    private Handler mUIHandler = null;

    public SyncBasic(Handler handler) {
        mUIHandler = handler;
    }

    public void beginTest() {
        Runnable operate = new Runnable() {
            @Override
            public void run() {
                new Operation().operate();
            }
        };
        new Thread(operate,"Thread-01").start();
        new Thread(operate,"Thread-02").start();
    }

    private class Operation {
        public void operate() {
            //synchronized (SyncBasic.sLocker) {
            //synchronized (Operation.class) {
            synchronized (this) {
                countFlag++;
                try {
                    Thread.sleep(new Random().nextInt(5));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countFlag--;

                String show = "Thread: " + Thread.currentThread().getName()
                        + " /countFlag : " + countFlag;

                Log.d(TAG, show);
                notifyUI(show + "\r\n");
            }
        }
    }

    private void notifyUI(String show) {
        Message msg = mUIHandler.obtainMessage(Const.MSG_TO_UI);
        msg.obj = show;
        msg.sendToTarget();
    }

}
