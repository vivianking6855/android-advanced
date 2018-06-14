package performance;

import java.util.Random;
import java.util.StringTokenizer;

import static util.CollectUtils.*;

public class CutStringTest {

    public static void start() {
        String originStr = getOriginStr();
        // split
        splitStr(originStr);
        // StringTokenizer
        stringTokenizerStr(originStr);
        // substring()
        substringStr(originStr);
    }

    private static void splitStr(String orgin) {
        String TAG = "[split]: ";
        long startMem = startRecordMemory(TAG);
        long lastTimeNanos = System.nanoTime();
        String[] result = orgin.split("\\.");
        System.out.println(TAG + "result " + result.length);
        System.out.println(diff(TAG, lastTimeNanos));
        stopRecordMemory(TAG, startMem);
        System.out.println();
    }

    private static void stringTokenizerStr(String origin) {
        String TAG = TAG = "[StringTokenizer]: ";
        long startMem = startRecordMemory(TAG);
        long lastTimeNanos = System.nanoTime();
        StringTokenizer token = new StringTokenizer(origin, ".");
        System.out.println(TAG + "result " + token.countTokens());
        System.out.println(diff(TAG, lastTimeNanos));
        stopRecordMemory(TAG, startMem);
        System.out.println();
    }

    private static void substringStr(String origin) {
        String TAG = "[substring]: ";
        long startMem = startRecordMemory(TAG);
        long lastTimeNanos = System.nanoTime();
        int len = origin.lastIndexOf(".");
        int k = 0, count = 0;
        //String sub;
        for (int i = 0; i <= len; i++) {
            if (origin.substring(i, i + 1).equals(".")) {
                if (count == 0) {
                    origin.substring(0, i);
                } else {
                    origin.substring(k + 1, i);
                    if (i == len) {
                        origin.substring(len + 1, origin.length());
                    }
                }
                k = i;
                count++;
            }
        }

        System.out.println(TAG + "result " + count);
        System.out.println(diff(TAG, lastTimeNanos));
        stopRecordMemory(TAG, startMem);
        System.out.println();
    }

    private static String getOriginStr() {
        final int len = 10000;
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            result.append(random.nextInt(9)).append(random.nextInt(9)).append(random.nextInt(9)).append(".");
        }

        return result.toString();
    }

}
