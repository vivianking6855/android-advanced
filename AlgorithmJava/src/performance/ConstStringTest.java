package performance;

import static util.CollectUtils.*;
import static util.Const.*;

public class ConstStringTest {

    public static void start() {
        System.out.println("const string start =================");

        // "+"
        final String TAG1 = "[const +]: ";
        long startMem = startRecordMemory(TAG1);
        long lastTimeNanos = System.nanoTime();
        for (int i = 0; i < LOOP; i++) {
            String str = S1 + S2 + S3 + S4;
        }
        System.out.println(diff(TAG1, lastTimeNanos));
        stopRecordMemory(TAG1, startMem);
        System.out.println();

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
        System.out.println();

        // "concat"
        final String TAG3 = "[const concat]: ";
        startMem = startRecordMemory(TAG3);
        lastTimeNanos = System.nanoTime();
        for (int i = 0; i < LOOP; i++) {
            String str = S1.concat(S2).concat(S3).concat(S4);
        }
        System.out.println(diff(TAG3, lastTimeNanos));
        stopRecordMemory(TAG3, startMem);
        System.out.println();

        System.out.println("const string end =================");
    }
}