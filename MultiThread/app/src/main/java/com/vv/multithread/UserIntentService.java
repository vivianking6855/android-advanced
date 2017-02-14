package com.vv.multithread;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by vivian on 2017/2/9.
 */

public class UserIntentService extends IntentService {
    private static final String TAG = UserIntentService.class.getSimpleName();
    private static final String NAME = "UserTaskService";

    public UserIntentService() {
        super(NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getStringExtra(Const.KEY_ACTION);
        Log.d(TAG, "receive task :" + action);

        switch (action) {
            case Const.ACTION_QUERY:
            case Const.ACTION_UPDATE:
            case Const.ACTION_DEL:
                doSomething(intent, action);
                Log.d(TAG, "handle task: " + action);
                break;
            default:
                break;
        }
    }

    private void doSomething(Intent intent, String action) {
        // SystemClock.sleep very similar to Thread.sleep.
        // Use this function for delays if you do not useThread.interrupt(), as it will preserve the interrupted state of the thread.
        SystemClock.sleep(1000);

        // send msg to update ui
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Messenger messenger = (Messenger) bundle.get(Const.KEY_MAIN_MESSENGER);
            Message msg = Message.obtain();
            msg.what = Const.MSG_SHOW;
            msg.obj = "done action " + action + "\r\n";
            try {
                messenger.send(msg);
            } catch (RemoteException e) {
                Log.w(TAG, "doSomething ex", e);
            }
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "service destroyed.");
        super.onDestroy();
    }

}
