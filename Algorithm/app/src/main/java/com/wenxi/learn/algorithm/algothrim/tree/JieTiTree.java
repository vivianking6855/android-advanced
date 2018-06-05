package com.wenxi.learn.algorithm.algothrim.tree;

import android.util.Log;

import com.wenxi.learn.algorithm.algothrim.base.IAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 阶梯树求和算法
 * 阶梯树 12, 10, 121,123等。数字的左右两边都大1或小一； 是大于等于0的数值
 * 规律：
 * 1. i(i+1)或i(i-1)
 * 2. n位的阶梯树是n-1位向有扩展；（向左扩展会重复）
 */
public class JieTiTree implements IAlgorithm {
    
    private final static String TAG = "JieTiTree";
    private final static int MAX_NUM = 100000; // 10W
    private final static int BIT_NUM = String.valueOf(MAX_NUM).length();

    /**
     * Gets instance with inner static class way
     *
     * @return the instance
     */
    public static JieTiTree getInstance() {
        return JieTiTree.Holder.INSTANCE;
    }

    private JieTiTree() {
    }

    private static class Holder {
        private static final JieTiTree INSTANCE = new JieTiTree();
    }

    @Override
    public void startAlgorithm() {
        JieTiTree.getInstance().start();
    }

    private synchronized void start() {
        new WhileTest().strategyWhile();
        new RecursiveTest().strategyRecursive();
    }

    class RecursiveTest {
        int sum = 0;
        List<Integer> array = new ArrayList<>();
        List<Integer> array_temp = new ArrayList<>();
        String temp_str;
        int temp_int;
        int last_num;
        int num1, num2; // i+1; i-1

        void strategyRecursive() {
            // tag start time
            long startTime = System.nanoTime();
            recursiveLoop(2);
            Log.d(TAG, "strategyRecursive sum = " + sum);
            // tag end time
            long diffMs = TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
            Log.d(TAG, "strategyRecursive time diff = " + diffMs + "ms");
        }

        private void recursiveLoop(int index) {
            if (index == 2) {
                for (int n = 1; n < 10; n++) {
                    // calculator +1, -1
                    temp_int = n * 10;
                    // add to sum
                    if (n + 1 < 10) {
                        sum += temp_int + n + 1;
                        array.add(temp_int + n + 1);
                        //Log.d(TAG, "index = " + index + "; num = " + (temp_int + n + 1));
                    }
                    sum += temp_int + n - 1;
                    array.add(temp_int + n - 1);
                    //Log.d(TAG, "index = " + index + "; num = " + (temp_int + n - 1));
                }
                recursiveLoop(index + 1);
            } else {
                // create temp array to for circle
                array_temp.clear();
                for (int s = 0; s < array.size(); s++) {
                    temp_str = String.valueOf(array.get(s));
                    temp_str = temp_str.substring(temp_str.length() - 1);
                    last_num = Integer.parseInt(temp_str);
                    num1 = last_num + 1;
                    num2 = last_num - 1;
                    temp_int = array.get(s) * 10;
                    // add to sum
                    if (num1 < 10) {
                        sum += temp_int + num1;
                        array_temp.add(temp_int + num1);
                        //Log.d(TAG, "index = " + index + "; num = " + (temp_int + num1));
                    }
                    if (num2 > 0) {
                        sum += temp_int + num2;
                        array_temp.add(temp_int + num2);
                        //Log.d(TAG, "index = " + index + "; num = " + (temp_int + num2));
                    }
                }
                array.clear();
                array.addAll(array_temp);

                if (index != BIT_NUM - 1) {
                    recursiveLoop(index + 1);
                }
            }
        }

    }

    class WhileTest {
        int sum = 0;

        private void strategyWhile() {
            // tag start time
            long startTime = System.nanoTime();
            whileLoop();
            Log.d(TAG, "strategyWhile sum = " + sum);
            // tag end time
            long diffMs = TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
            Log.d(TAG, "strategyWhile time diff = " + diffMs + "ms");
        }

        private void whileLoop() {
            int index = 2;
            int num1, num2; // i+1; i-1
            int last_num;
            List<Integer> array = new ArrayList<>();
            List<Integer> array_temp = new ArrayList<>();
            String temp_str;
            int temp_int;
            while (index < BIT_NUM) {
                // 2 bits num, such as 12,10
                if (index == 2) {
                    for (int n = 1; n < 10; n++) {
                        // calculator +1, -1
                        num1 = n + 1;
                        num2 = n - 1;
                        temp_int = n * 10;
                        // add to sum
                        if (num1 < 10) {
                            sum += temp_int + num1;
                            array.add(temp_int + num1);
                            //Log.d(TAG, "index = " + index + "; num = " + (temp_int + num1));
                        }
                        sum += temp_int + num2;
                        array.add(temp_int + num2);
                        //Log.d(TAG, "index = " + index + "; num = " + (temp_int + num2));
                    }
                } else {
                    // create temp array to for circle
                    array_temp.clear();
                    for (int s = 0; s < array.size(); s++) {
                        temp_str = String.valueOf(array.get(s));
                        temp_str = temp_str.substring(temp_str.length() - 1);
                        last_num = Integer.parseInt(temp_str);
                        num1 = last_num + 1;
                        num2 = last_num - 1;
                        temp_int = array.get(s) * 10;
                        // add to sum
                        if (num1 < 10) {
                            sum += temp_int + num1;
                            array_temp.add(temp_int + num1);
                            //Log.d(TAG, "index = " + index + "; num = " + (temp_int + num1));
                        }
                        if (num2 > 0) {
                            sum += temp_int + num2;
                            array_temp.add(temp_int + num2);
                            //Log.d(TAG, "index = " + index + "; num = " + (temp_int + num2));
                        }
                    }
                    array.clear();
                    array.addAll(array_temp);

                }// end else

                index++;
            }// end while
        }

    }

}
