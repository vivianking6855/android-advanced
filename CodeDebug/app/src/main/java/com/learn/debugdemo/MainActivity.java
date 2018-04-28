package com.learn.debugdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.debug.lib.CodeDebugConst;
import com.debug.lib.CodeDebugUtil;
import com.debug.lib.LaunchTimeUtil;
import com.learn.blockmonitor.BlockMonitor;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "CodeDebug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (CodeDebugConst.DEBUG) {
            BlockMonitor.install(this, new UserBlockConfig()).start();
        }

        LaunchTimeUtil.getInstance().start();
        // init CodeDebugUtil
        CodeDebugUtil.getInstance().init(this);
        // start tracing, it must call in UI thread
        CodeDebugUtil.getInstance().startTracing();

        findViewById(R.id.tv_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testMethodTimeConsume();
            }
        });

        testMemoryLeak();

        // stop tracing, tracing file will not appear since you call stopTracing method
        CodeDebugUtil.getInstance().stopTracing();
        // if you need collect method tracing nano time, use following instead.
        // long diffMs = CodeDebugUtil.stopTracing(lastTimeNanos);
        // Log.d(TAG,"onCreate MethodTracing: " + + diffMs + "ms");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            LaunchTimeUtil.getInstance().stop("onWindowFocusChanged");
        }
    }

    private void testMethodTimeConsume() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "DemoThread").start();
    }

    private void testMemoryLeak() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(60 * 60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, "VivianLeakThread").start();
    }

    @Override
    protected void onResume() {
        super.onResume();

        LaunchTimeUtil.getInstance().registerIdleHandler();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        LaunchTimeUtil.getInstance().start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LaunchTimeUtil.getInstance().stop("onDestroy");
    }

}
