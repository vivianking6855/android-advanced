package com.vv.multithread;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    private TextView mTVShow;
    private DateFormat dataFormat = new SimpleDateFormat("HH:mm:ss");

    private ExecutorService fixedThreadPool;
    private ExecutorService cachedThreadPool;
    private ScheduledExecutorService scheduledThreadPool;
    private ExecutorService singleThreadExecutor;

    private Handler mMainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Const.MSG_SHOW:
                    mTVShow.append(msg.obj.toString());
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initThreadPool();
    }

    private void initView() {
        mTVShow = (TextView) findViewById(R.id.show);
        mTVShow.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    private void initThreadPool() {
        fixedThreadPool = Executors.newFixedThreadPool(4);
        cachedThreadPool = Executors.newCachedThreadPool();
        scheduledThreadPool = Executors.newScheduledThreadPool(4);
        singleThreadExecutor = Executors.newSingleThreadExecutor();
    }

    public void runAsyncTask(View v) {
        mTVShow.setText("");
        try {
            new DownloadAsyncTask().execute(new URL("http://www.baidu.com"),
                    new URL("http://zentalk.asus.com.cn/portal.php"),
                    new URL("http://zentalk.asus.com.cn/portal.php"),
                    new URL("http://zentalk.asus.com.cn/portal.php")
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private class DownloadAsyncTask extends AsyncTask<URL, Integer, Long> { //(Params, Progress, Result)
        protected Long doInBackground(URL... urls) { // run in thread pool
            int count = urls.length;
            long totalSize = 0;
            for (int i = 0; i < count; i++) {
                totalSize += urls[i].toString().length(); // simulate each file size
                publishProgress((int) ((i / (float) count) * 100));
                // Escape early if cancel() is called
                if (isCancelled()) {
                    break;
                }
            }
            return totalSize;
        }

        protected void onProgressUpdate(Integer... progress) { // run in main thread
            mTVShow.append("progress  =  " + progress[0] + "\r\n");
        }

        protected void onPostExecute(Long result) {// run in main thread
            mTVShow.append("doInBackground return totalSize: " + result + "\r\n");
        }
    }

    public void runIntentService(View v) {
        mTVShow.setText("");
        Intent intent = new Intent(this, UserIntentService.class);
        // task query
        intent.putExtra(Const.KEY_ACTION, Const.ACTION_QUERY);
        intent.putExtra(Const.KEY_MAIN_MESSENGER, new Messenger(mMainHandler));
        startService(intent);
        // task update
        intent.putExtra(Const.KEY_ACTION, Const.ACTION_UPDATE);
        intent.putExtra(Const.KEY_MAIN_MESSENGER, new Messenger(mMainHandler));
        startService(intent);
        // task del
        intent.putExtra(Const.KEY_ACTION, Const.ACTION_DEL);
        intent.putExtra(Const.KEY_MAIN_MESSENGER, new Messenger(mMainHandler));
        startService(intent);
    }

    private Runnable command = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "time = " + dataFormat.format(new Date()));
            Message msg = mMainHandler.obtainMessage(Const.MSG_SHOW);
            msg.obj = "thread pool, time " + dataFormat.format(new Date()) + "\r\n";
            msg.sendToTarget();
            SystemClock.sleep(2000);
        }
    };

    public void runThreadPool(View v) {
        fixedThreadPool.execute(command);

        cachedThreadPool.execute(command);

        // 2000ms后执行command
        scheduledThreadPool.schedule(command, 2000, TimeUnit.MILLISECONDS);
        // 延迟10ms后，每隔1000ms执行一次command
        scheduledThreadPool.scheduleAtFixedRate(command, 10, 1000, TimeUnit.MILLISECONDS);

        singleThreadExecutor.execute(command);
    }

    public void endThreadPool(View v) {
        if (fixedThreadPool != null) {
            fixedThreadPool.shutdown();
        }
        if (cachedThreadPool != null) {
            cachedThreadPool.shutdown();
        }
        if (scheduledThreadPool != null) {
            scheduledThreadPool.shutdown();
        }
        if (singleThreadExecutor != null) {
            singleThreadExecutor.shutdown();
        }
    }
}
