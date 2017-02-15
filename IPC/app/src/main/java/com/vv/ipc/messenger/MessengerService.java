package com.vv.ipc.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import com.vv.ipc.Const;

import java.util.Date;

public class MessengerService extends Service {
    private static final String TAG = MessengerService.class.getSimpleName();

    // messenger used to return mMessenger.getBinder when bind Service
    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    // handler to deal with msg from client
    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Const.MSG_FROM_CLIENT:
                    dealWithClientMsg(msg);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    public MessengerService() {
    }

    private static void dealWithClientMsg(Message msg) {
        Log.d(TAG, "get msg :" + msg.getData().getString(Const.KEY_FROM_CLIENT));
        SystemClock.sleep(2000);
        Messenger client = msg.replyTo;
        Message reply = Message.obtain(null, Const.MSG_FROM_SERVICE);
        Bundle bundle = new Bundle();
        bundle.putString(Const.KEY_FROM_SERVICE, "service reply at " + Const.DATA_FORMAT.format(new Date()));
        reply.setData(bundle);
        try {
            client.send(reply);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
