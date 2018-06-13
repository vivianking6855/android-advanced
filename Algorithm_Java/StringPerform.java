import java.util.concurrent.TimeUnit;
import java.util.Date;

public class StringPerform {
    // The constant TEST_STR.
    private final static String S1 = "This is a";
    private final static String S2 = "long test string for ";
    private final static String S3 = "different JDK performance ";
    private final static String S4 = "testing.";
    // The loop number
    private final static int LOOP = 10000; // 1W, 1000000; //100W

    /**
     * Gets instance with inner static class way
     *
     * @return the instance
     */
    public static StringPerform getInstance() {
        return StringPerform.Holder.INSTANCE;
    }

    private StringPerform() {
    }

    private static class Holder {
        private static final StringPerform INSTANCE = new StringPerform();
    }

    public void demo() {
        //constString();
        // dynamicString();
    }

    public void constString() {
        // "+"
        final String TAG1 = "[const +]: ";
        long startMem = startRecordMemory(TAG1);
        long lastTimeNanos = System.nanoTime();
        for (int i = 0; i < LOOP; i++) {
            String str = S1 + S2 + S3 + S4;
        }
        System.out.println(diff(TAG1, lastTimeNanos));
        stopRecordMemory(TAG1, startMem);

        // "StringBuilder"
        final String TAG2 = "[const StringBuilder]: ";
        startMem = startRecordMemory(TAG2);
        lastTimeNanos = System.nanoTime();
        for (int i = 0; i < LOOP; i++) {
            StringBuilder buffer = new StringBuilder();
            buffer.append(S1);
            buffer.append(S2);
            buffer.append(S3);
            buffer.append(S4);
            String str = buffer.toString();
        }
        System.out.println(diff(TAG2, lastTimeNanos));
        stopRecordMemory(TAG2, startMem);

        // "concat"
        final String TAG3 = "[const concat]: ";
        startMem = startRecordMemory(TAG3);
        lastTimeNanos = System.nanoTime();
        for (int i = 0; i < LOOP; i++) {
            String str = S1.concat(S2).concat(S3).concat(S4);
        }
        System.out.println(diff(TAG3, lastTimeNanos));
        stopRecordMemory(TAG3, startMem);

        System.out.println("const string end =================");
    }

    public void dynamicString() {
        goodDynamic();
        normalDynamic();
        badDynamic();
    }

    private void goodDynamic() {
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

        System.out.println(diff("dynamic string [good StringBuilder & evaluated len]: ", lastTimeNanos));
    }

    // the worst way for dynamic combine: memory churn appeared
    private void badDynamic() {
        long lastTimeNanos = System.nanoTime();
        String dynamic_temp = "";
        for (int i = 0; i < LOOP; i++) {
            dynamic_temp += "test_" + i + S1;
        }
        System.out.println(dynamic_temp);
        System.out.println(diff("dynamic string [bad String +]: ", lastTimeNanos));
    }

    private void normalDynamic() {
        long lastTimeNanos = System.nanoTime();
        StringBuilder stringBuilder = new StringBuilder(LOOP);
        for (int i = 0; i < LOOP; i++) {
            stringBuilder.append("test" + i + S1);
        }

        System.out.println(stringBuilder.toString());

        System.out.println(diff("dynamic string [normal StringBuilder]: ", lastTimeNanos));
    }

    // // mock memory churn,the worst way to create too much objects
    // private static void mockChurn() {
    // long lastTimeNanos = System.nanoTime();
    // for (int i = 0; i < LOOP_NUM; i++) {
    // if (StateMan.sStop) {
    // return;
    // }
    // String temp = "test" + i + i + i + TEST_STR;
    // // Log.d(TAG, temp);
    // }

    // Log.d(TAG, diff("[mockChurn]", lastTimeNanos));
    // }

    // // mock to avoid memory churn: good way to use outside String instead
    // private static void mockAvoidChurn() {
    // long lastTimeNanos = System.nanoTime();
    // String outtemp = null;
    // for (int i = 0; i < LOOP_NUM; i++) {
    // if (StateMan.sStop) {
    // return;
    // }

    // outtemp = "test" + i + TEST_STR;
    // // Log.d(TAG, outtemp);
    // }

    // Log.d(TAG, diff("[mockAvoidChurn]", lastTimeNanos));
    // }

    private String diff(String title, long lastTimeNanos) {
        return title + " diffMs: "
                + TimeUnit.MILLISECONDS.convert(System.nanoTime() - lastTimeNanos, TimeUnit.NANOSECONDS) + "ms";
    }

    private long startRecordMemory(String tag) {
        Runtime run = Runtime.getRuntime();
        run.gc();
        System.out.println(tag + "time: " + (new Date()));
        // 获取开始时内存使用量
        long startMem = run.totalMemory() - run.freeMemory();
        System.out.println(
                tag + "memory> total:" + run.totalMemory() + " free:" + run.freeMemory() + " used:" + startMem);

        return startMem;
    }

    private void stopRecordMemory(String tag, long startMem) {
        Runtime run = Runtime.getRuntime();
        System.out.println("time: " + (new Date()));
        long endMem = run.totalMemory() - run.freeMemory();
        System.out
                .println(tag + "memory> total:" + run.totalMemory() + " free:" + run.freeMemory() + " used:" + endMem);
        System.out.println(tag + "memory difference:" + (endMem - startMem) / 1024 + "(K)");
    }

}
