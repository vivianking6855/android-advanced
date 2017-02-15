package com.vv.ipc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.vv.ipc.book.BookManagerActivity;
import com.vv.ipc.messenger.MessengerActivity;

/**
 * IPC Demo
 * 1. static variable unexpected value
 * MainActivity set new sUserId = 2; But SecondActivity get sUserId = 1;
 * When start SecondActivity, it run in another process, Application onCreate will re- enter.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mTVShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserManager.sUserId = 2;

        initView();
    }

    private void initView() {
        mTVShow = (TextView) findViewById(R.id.show);
        mTVShow.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTVShow.setText("myPid = " + Process.myPid() + "; sUserId = " + UserManager.sUserId);
    }

    public void startSecond(View v) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, SecondActivity.class);
        intent.putExtra(Const.KEY_TO_SECOND, "Main myPid= " + Process.myPid());
        startActivity(intent);
    }

    public void runMessenger(View v) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, MessengerActivity.class);
        startActivity(intent);
    }

    public void runAIDL(View v) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, BookManagerActivity.class);
        startActivity(intent);
    }

}
