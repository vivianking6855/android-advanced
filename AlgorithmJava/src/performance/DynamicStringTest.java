package performance;

import static util.CollectUtils.*;
import static util.Const.*;

public class DynamicStringTest{
    public static void start() {
        dynamicPlus();
        dynamicStringBuilder();
        dynamicStringBuilderOptimized();
        dynamicConcat();
    }

    // good way for dynamic combine with StringBuilder and evaluated length
    private static void dynamicStringBuilderOptimized() {
        final String TAG = "[StringBuilder & evaluated len]: ";
        long startMem = startRecordMemory(TAG);
        long lastTimeNanos = System.nanoTime();
        String[] strList = new String[LOOP];
        // 1. evaluate
        int evaluateLen = 0;
        for (int i = 0; i < LOOP; i++) {
            strList[i] = "test" + i + S1;
            evaluateLen += strList[i].length();
        }

        // 2. create StringBuilder with evaluated size
        StringBuilder stringBuilder = new StringBuilder(evaluateLen);
        for (int i = 0; i < LOOP; i++) {
            stringBuilder.append(strList[i]);
        }
        strList = null;

        // 3. get string
        System.out.println(stringBuilder.toString());

        // collection memory information
        System.out.println(diff(TAG, lastTimeNanos));
        stopRecordMemory(TAG, startMem);
        System.out.println();
    }

    // the worst way for dynamic combine: memory churn appeared with "+"
    private static void dynamicPlus() {
        final String TAG = "[bad String +]: ";
        long startMem = startRecordMemory(TAG);
        long lastTimeNanos = System.nanoTime();
        String dynamic_temp = "";
        for (int i = 0; i < LOOP; i++) {
            dynamic_temp += "test_" + i + S1;
        }
        System.out.println(dynamic_temp);
        System.out.println(diff(TAG, lastTimeNanos));
        stopRecordMemory(TAG, startMem);
        System.out.println();
    }

    // normal way for dynamic combine with StringBuilder
    private static void dynamicStringBuilder() {
        final String TAG = "[StringBuilder]: ";
        long startMem = startRecordMemory(TAG);

        long lastTimeNanos = System.nanoTime();
        StringBuilder stringBuilder = new StringBuilder(LOOP);
        for (int i = 0; i < LOOP; i++) {
            stringBuilder.append("test" + i + S1);
        }

        System.out.println(stringBuilder.toString());
        System.out.println(diff(TAG, lastTimeNanos));
        stopRecordMemory(TAG, startMem);
        System.out.println();
    }

    // normal way for dynamic combine with StringBuilder
    private static void dynamicConcat() {
        final String TAG = "[Concat]: ";
        long startMem = startRecordMemory(TAG);

        long lastTimeNanos = System.nanoTime();
        String start = "test" + 0 + S1;
        for (int i = 1; i < LOOP; i++) {
            start = start.concat("test" + i + S1);
        }

        System.out.println(start);
        System.out.println(diff(TAG, lastTimeNanos));
        stopRecordMemory(TAG, startMem);
        System.out.println();
    }
}