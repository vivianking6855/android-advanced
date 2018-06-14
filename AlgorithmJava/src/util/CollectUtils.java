package util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CollectUtils {

    public static String diff(String title, long lastTimeNanos) {
        return title + " diffMs: "
                + TimeUnit.MILLISECONDS.convert(System.nanoTime() - lastTimeNanos, TimeUnit.NANOSECONDS) + "ms";
    }

    public static long startRecordMemory(String tag) {
        Runtime run = Runtime.getRuntime();
        run.gc();
        System.out.println(tag + "time: " + (new Date()));
        // start get memory
        long startMem = run.totalMemory() - run.freeMemory();
        System.out.println(
                tag + "memory> total:" + run.totalMemory() + " free:" + run.freeMemory() + " used:" + startMem);

        return startMem;
    }

    public static void stopRecordMemory(String tag, long startMem) {
        Runtime run = Runtime.getRuntime();
        System.out.println(tag + "time: " + (new Date()));
        long endMem = run.totalMemory() - run.freeMemory();
        System.out
                .println(tag + "memory> total:" + run.totalMemory() + " free:" + run.freeMemory() + " used:" + endMem);
        System.out.println(tag + "memory difference:" + (endMem - startMem) / 1024 + "(K)");
    }
}
