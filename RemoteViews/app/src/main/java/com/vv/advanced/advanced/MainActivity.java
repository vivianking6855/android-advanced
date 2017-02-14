package com.vv.advanced.advanced;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

/**
 * @description logic :
 * - 有2个Activity分别运行在不同的进程中，一个A(MainActivity),一个B(ClientActivity)
 * - A接受通知，模拟通知栏的效果。
 * - B可以不停的地发送通知栏消息（模拟消息）。通知方案选用Broadcast（系统是用Binder）
 * blog demo http://vivianking6855.github.io/Android-Nano-Tips-12-RemoteViews/
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    // receive remote view broadcast and update ui
    private LinearLayout mRemoteViewsContent;
    private BroadcastReceiver mRemoteViewsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            RemoteViews remoteViews = intent
                    .getParcelableExtra(Const.EXTRA_REMOTE_VIEWS);
            if (remoteViews != null) {
                updateUI(remoteViews);
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
        mRemoteViewsContent = (LinearLayout) findViewById(R.id.remote_views_content);
        IntentFilter filter = new IntentFilter(Const.REMOTE_ACTION);
        registerReceiver(mRemoteViewsReceiver, filter);

        TextView hint = (TextView) findViewById(R.id.hint);
        hint.setText("logic:\r\n"
                + "1. 有A，B 2个Activity运行在不同的进程\r\nA(MainActivity),B(ClientActivity)\r\n"
                + "2. A接受通知，模拟通知栏的效果。\r\n"
                + "3. B不停的地发送通知栏消息（模拟消息）\r\n\r\n"
                + "MainActivity（A) process : " + Process.myPid());
    }

    private void updateUI(RemoteViews remoteViews) {
        // if A,B belongs to the same application use this way
        // View view = remoteViews.apply(this, mRemoteViewsContent);
        // if A,B belongs to different application,you may use this way
        int layoutId = getResources().getIdentifier("layout_simulated_notification", "layout", getPackageName());
        View view = getLayoutInflater().inflate(layoutId, mRemoteViewsContent, false);
        remoteViews.reapply(this, view);

        mRemoteViewsContent.addView(view);
    }

    public void onButtonClick(View v) {
        Intent intent = new Intent(this, ClientActivity.class);
        startActivity(intent);
    }

}
