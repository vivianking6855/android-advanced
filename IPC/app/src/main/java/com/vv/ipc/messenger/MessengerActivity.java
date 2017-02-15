package com.vv.ipc.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.vv.ipc.Const;
import com.vv.ipc.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessengerActivity extends AppCompatActivity {
    private static final String TAG = MessengerActivity.class.getSimpleName();
    private static final String SERVICE_URI = "com.vv.ipc.messenger.MessengerService.launch";

    private TextView mTVShow;

    // service and reply messenger
    private Messenger mService;
    private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());

    // handler to update ui
    private class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Const.MSG_FROM_SERVICE:
                    mTVShow.append(msg.getData().getString(Const.KEY_FROM_SERVICE) + "\r\n");
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        initView();

        // bind service : after 5.0 must follow
        // https://developer.android.com/google/play/billing/billing_integrate.html#billing-requests
        Intent intent = new Intent(SERVICE_URI);
        intent.setPackage(getPackageName());
        bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);
    }

    private void initView() {
        mTVShow = (TextView) findViewById(R.id.show);
        mTVShow.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    private ServiceConnection mServiceConn = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = new Messenger(service);
            Log.d(TAG, "bind service");
        }

        public void onServiceDisconnected(ComponentName className) {
        }
    };

    public void sendMsgToService(View v) {
        if(mService == null){
            Log.w(TAG, "mService is null");
            return;
        }

        Message msg = Message.obtain(null, Const.MSG_FROM_CLIENT);
        Bundle data = new Bundle();
        data.putString(Const.KEY_FROM_CLIENT, "hello, I' client." + Const.DATA_FORMAT.format(new Date()));
        msg.setData(data);
        msg.replyTo = mGetReplyMessenger;
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            unbindService(mServiceConn);
        }
    }
}
