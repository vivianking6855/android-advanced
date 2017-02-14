package com.vv.advanced.advanced;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientActivity extends Activity {
    private static final String TAG = ClientActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
    }

    public void onButtonClick(View v) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_simulated_notification);
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        remoteViews.setTextViewText(R.id.msg, "msg from process : " + Process.myPid() + "\r\n"
                + df.format(new Date())
                + "\r\n点击开启B");

        // set pending intent, when click textview
        PendingIntent openActivityPendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, ClientActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notify_layout, openActivityPendingIntent);
        Intent intent = new Intent(Const.REMOTE_ACTION);
        intent.putExtra(Const.EXTRA_REMOTE_VIEWS, remoteViews);
        sendBroadcast(intent);
        finish();
    }

}
