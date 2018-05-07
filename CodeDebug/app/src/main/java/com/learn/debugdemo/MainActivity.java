package com.learn.debugdemo;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.debug.lib.DebugMan;
import com.debug.lib.DebugDumpMan;
import com.debug.lib.DebugTimeMan;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "CodeDebug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BlockMonitor.install(this, new UserBlockConfig()).start();

        DebugMan.enable(false);
        DebugTimeMan.getInstance().start();
        // init DebugDumpMan
        DebugDumpMan.getInstance().init(this);
        // start tracing, it must call in UI thread
        DebugDumpMan.getInstance().startTracing();

        // stop tracing, tracing file will not appear since you call stopTracing method
        DebugDumpMan.getInstance().stopTracing();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            DebugTimeMan.getInstance().stop("onWindowFocusChanged");
        }
    }

    public void testMethodTimeConsume(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "TimeConsumeThread").start();
    }

    public void testMemoryLeak(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(60 * 60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, "MemoryLeakThread").start();
    }

    public void testStringPerformance(View v){
        StateMan.sStop = false;
        TestString.testStringPerformance();
    }

    public void testMethodChurn(View v){
        StateMan.sStop = false;
        TestString.testMemoryChurn();
    }

    @Override
    protected void onResume() {
        super.onResume();

        DebugTimeMan.getInstance().registerIdleHandler(new DebugTimeMan.IdleHandlerListener() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void idleHandlerDone() {
                reportFullyDrawn();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        DebugTimeMan.getInstance().start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        DebugTimeMan.getInstance().stop("onDestroy");
    }

    public void stopAll(View view) {
        StateMan.sStop = true;
    }
}
