package com.vv.messagedemo;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.CountDownLatch;

/**
 * Created by vivian on 2017/2/8.
 */

public class WorkThreadOne extends Thread {
    private final static String TAG = WorkThreadOne.class.getSimpleName();

    private Context mContext;
    private Looper mLooper;
    private UserHandler mWorkHandler = null;
    private CountDownLatch mLatch = new CountDownLatch(1);

    public WorkThreadOne(Context context) {
        mContext = context;
    }

    public Handler getInnerHandler() {
        try {
            mLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mWorkHandler;
    }

    @Override
    public void run() {
        if (Looper.myLooper() == null) {
            // prepare Looper
            Looper.prepare();
            mLooper = Looper.myLooper();
            // init handler in work thread
            mWorkHandler = new UserHandler();
            mLatch.countDown();

            Looper.loop();
        }
    }

    private class UserHandler extends Handler {
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
        mLooper.quitSafely();
    }

}
