package com.vv.messagedemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by vivian on 2017/2/8.
 */

public class LoadFileThread extends Thread {
    private final static String TAG = LoadFileThread.class.getSimpleName();

    private Handler mMainHandler;

    public LoadFileThread(Handler handler) {
        mMainHandler = handler;
    }

    @Override
    public void run() {
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // after load finish send message
        Message msg = mMainHandler.obtainMessage(Const.MSG_TO_MAIN);

        // save data in bundle
        Bundle b = new Bundle();
        b.putString(Const.KEY_USER_STR, "ASUS HZ thread id:" + this.getId());
        b.putInt(Const.KEY_USER_INT, 2017);
        msg.setData(b);

        // send msg with handle, it will add msg in message queue;
        // looper will get msg from message queue;
        // then find related handler and call it's handleMessage() to deal with
        msg.sendToTarget();

        Log.d(TAG, "LoadFileThread : "
                + Thread.currentThread().getName());
    }
}
