package com.learn.debugdemo;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import static com.debug.lib.DebugMan.TAG;

/**
 * The type Test string.
 */
public class TestString {
    private final static String TAG = "CodeDebug";
    // The constant TEST_STR.
    private final static String TEST_STR = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    // The constant LOOP_NUM.
    private final static int LOOP_NUM = 1000000; // 100W

    /**
     * Test memory churn.
     */
    public static void testMemoryChurn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "testMemoryChurn START====================================");
                    Thread.sleep(2000);

                    // churn test
                    mockChurn();
                    mockAvoidChurn();

                    Log.d(TAG, "testMemoryChurn END====================================");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "testMemoryChurn").start();
    }

    /**
     * Test string performance.
     */
    public static void testStringPerformance() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "testStringPerformance START====================================");

                    // 1. static combine test
                    goodStatic();

                    Thread.sleep(3000);

                    // 2. dynamic combine test
                    badDynamic();
                    goodDynamic();

                    Log.d(TAG, "testStringPerformance END====================================");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "testStringPerformance").start();
    }

    // the worst way for dynamic combine: memory churn appeared
    private static void badDynamic() {
        long lastTimeNanos = System.nanoTime();
        final int LOOP = 1000;
        String dynamic_temp = null;
        for (int i = 0; i < LOOP; i++) {
            if (StateMan.sStop) {
                return;
            }
            dynamic_temp += "test" + i + TEST_STR;
        }

        Log.d(TAG, "badDynamic " + dynamic_temp);

        Log.d(TAG, diff("[badDynamic]", lastTimeNanos));
    }

    private static void goodDynamic() {
        long lastTimeNanos = System.nanoTime();
        final int LOOP = 1000;
        String[] strList = new String[1000];
        int evalue_len = 0;
        for (int i = 0; i < LOOP; i++) {
            if (StateMan.sStop) {
                return;
            }
            strList[i] = "test" + i + TEST_STR;
            evalue_len += strList[i].length();
        }

        Log.d(TAG, "goodDynamic " + evalue_len);
        StringBuilder stringBuilder = new StringBuilder(evalue_len);
        for (int i = 0; i < LOOP; i++) {
            stringBuilder.append(strList[i]);
        }
        strList = null;
        Log.d(TAG, "goodDynamic " + stringBuilder.capacity());
        Log.d(TAG, stringBuilder.toString());

        Log.d(TAG, diff("[goodDynamic]", lastTimeNanos));
    }

    // good way for static combine with '+'
    private static void goodStatic() {
        String static_temp = null; // static combine
        for (int i = 0; i < LOOP_NUM; i++) {
            if (StateMan.sStop) {
                return;
            }
            // good way for static combine wiht '+'
            static_temp = "I'm " + "static " + "combine " + TEST_STR;
            //Log.d(TAG, static_temp);
        }
    }

    // mock memory churn,the worst way to create too much objects
    private static void mockChurn() {
        long lastTimeNanos = System.nanoTime();
        for (int i = 0; i < LOOP_NUM; i++) {
            if (StateMan.sStop) {
                return;
            }
            String temp = "test" + i + i + i + TEST_STR;
            //Log.d(TAG, temp);
        }

        Log.d(TAG, diff("[mockChurn]", lastTimeNanos));
    }

    // mock to avoid memory churn: good way to use outside String instead
    private static void mockAvoidChurn() {
        long lastTimeNanos = System.nanoTime();
        String outtemp = null;
        for (int i = 0; i < LOOP_NUM; i++) {
            if (StateMan.sStop) {
                return;
            }

            outtemp = "test" + i + TEST_STR;
            //Log.d(TAG, outtemp);
        }

        Log.d(TAG, diff("[mockAvoidChurn]", lastTimeNanos));
    }

    private static String diff(String title, long lastTimeNanos) {
        return title + " diffMs: "
                + TimeUnit.MILLISECONDS.convert(System.nanoTime() - lastTimeNanos, TimeUnit.NANOSECONDS) + "ms";
    }

}
