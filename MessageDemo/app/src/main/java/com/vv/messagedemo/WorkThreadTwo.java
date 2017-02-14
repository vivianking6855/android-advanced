package com.vv.messagedemo;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

/**
 * Created by vivian on 2017/2/8.
 */

public class WorkThreadTwo extends Thread {
    private final static String TAG = WorkThreadTwo.class.getSimpleName();

    private Context mContext;
    private HandlerThread mHandlerThread = null;
    private UserHandler mWorkHandler;
    private CountDownLatch mLatch = new CountDownLatch(1);

    public WorkThreadTwo(Context context) {
        mContext = context;
    }

    public Handler getInnerHandler() {
        try {
            Log.d(TAG, "getInnerHandler await start");
            mLatch.await();
            Log.d(TAG, "getInnerHandler await end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mWorkHandler;
    }

    @Override
    public void run() {
        if (mHandlerThread == null) {
            // prepare Looper
            mHandlerThread = new HandlerThread("WorkThreadTwo");
            mHandlerThread.start();

            // init handler in work thread
            Log.d(TAG, "create handler");
            mWorkHandler = new UserHandler(mHandlerThread.getLooper());
            mLatch.countDown();
        }
    }

    private class UserHandler extends Handler {
        public UserHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Const.MSG_MAIN_TO_WORK:
                    String title = (String) msg.obj;
                    Utils.showHint(mContext, title, "ASUS HZ");
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
        if (mHandlerThread.getLooper() != null) {
            mHandlerThread.getLooper().quitSafely();
            mHandlerThread.interrupt();
            mHandlerThread = null;
        }
    }
}
