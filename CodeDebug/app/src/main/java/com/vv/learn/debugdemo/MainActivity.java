package com.vv.learn.debugdemo;

import android.os.Bundle;
import android.os.Debug;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.open.utislib.file.PathUtils;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "CodeDebug";
    private long lastTimeNanos;
    private long currentTimeNanos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lastTimeNanos = System.nanoTime();
        CodeDebugUtils.startTracing(this);

        findViewById(R.id.tv_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testConsume();
            }
        });

        CodeDebugUtils.stopTracing(lastTimeNanos);
        // if you need collect method tracing nano time, use following instead.
        // long diffMs = CodeDebugUtils.stopTracing(lastTimeNanos);
        // Log.d(TAG,"onCreate MethodTracing: " + + diffMs + "ms");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            currentTimeNanos = System.nanoTime();
            long diffMs = TimeUnit.MILLISECONDS.convert(currentTimeNanos - lastTimeNanos, TimeUnit.NANOSECONDS);
            Log.d(TAG, "onWindowFocusChanged .MainActivity: +" + diffMs + "ms");
        }
    }

    private void testConsume() {
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

    @Override
    protected void onResume() {
        super.onResume();

        // IdleHandler to collect diffMs of activity launch nano time
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                currentTimeNanos = System.nanoTime();
                long diffMs = TimeUnit.MILLISECONDS.convert(currentTimeNanos - lastTimeNanos, TimeUnit.NANOSECONDS);
                Log.d(TAG, "IdleHandler .MainActivity: +" + diffMs + "ms");
                return false;
            }
        });
    }
}
