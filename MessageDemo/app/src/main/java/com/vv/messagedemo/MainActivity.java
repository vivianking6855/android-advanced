package com.vv.messagedemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description logic:
 * Demo One : Work thread send message to Main thread
 * 1.	Main thread create Handler，implement handleMessage()
 * 2.	Work thread send message through handler to update UI. message will add to MessageQueue
 * 3.	Then Looper will pick out message to Main thread to deal with in handleMessage
 * Demo Two: Main thread send message to Work thread
 * 1.	Create work thread and it's handler and Looper (two ways)
 * 2.	Main thread send message with handler
 */
public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    private TextView mTVShow;

    private LoadFileThread mLoadFileThread = null;
    private WorkThreadOne mWorkThreadOne = null;
    private WorkThreadTwo mWorkThreadTwo = null;

    private Handler mMainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Const.MSG_TO_MAIN:
                    // get data from bundle
                    Bundle b = msg.getData();
                    String content = b.getString(Const.KEY_USER_STR);
                    int time = b.getInt(Const.KEY_USER_INT);
                    DateFormat df = new SimpleDateFormat("HH:mm:ss");
                    mTVShow.append("load : " + time + " " + content + "\r\n" + df.format(new Date()) + "\r\n");
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mTVShow = (TextView) findViewById(R.id.show);
        mTVShow.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    public void workToMain(View v) {
        if (mLoadFileThread == null) {
            mLoadFileThread = new LoadFileThread(mMainHandler);
        }

        mLoadFileThread.start();
    }

    public void mainToWork1(View v) {
        if (mWorkThreadOne == null) {
            mWorkThreadOne = new WorkThreadOne(this);
        }

        mWorkThreadOne.start();

        Message msg = mWorkThreadOne.getInnerHandler().obtainMessage(Const.MSG_MAIN_TO_WORK);
        msg.obj = "one: main msg.thread id: " + mWorkThreadOne.getId();
        msg.sendToTarget();
    }

    public void mainToWork2(View v) {
        if (mWorkThreadTwo == null) {
            mWorkThreadTwo = new WorkThreadTwo(this);
            mWorkThreadTwo.start();
        }

        Message msg = mWorkThreadTwo.getInnerHandler().obtainMessage(Const.MSG_MAIN_TO_WORK);
        msg.obj = "two: main msg.thread id: " + mWorkThreadTwo.getId();
        msg.sendToTarget();
    }

    public void endThreads(View v) {
        quitThreads();
    }

    private void quitThreads() {
        if (mLoadFileThread != null) {
            mLoadFileThread.interrupt();
            mLoadFileThread = null;
        }
        if (mWorkThreadOne != null) {
            mWorkThreadOne.interrupt();
            mWorkThreadOne = null;
        }
        if (mWorkThreadTwo != null) {
            mWorkThreadTwo.interrupt();
            mWorkThreadTwo = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        quitThreads();
    }
}
