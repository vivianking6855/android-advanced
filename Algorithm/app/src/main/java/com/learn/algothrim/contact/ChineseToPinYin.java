package com.learn.algothrim.contact;

import android.util.Log;

import com.learn.algothrim.base.IAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * simulator chinese to pinyin algorithm.
 * each chinese words has multi pinyin, we need to get all combine here
 * 模拟测试联系人汉字to拼音的两种策略：递归和while循环
 * 包含多音字
 */
public class ChineseToPinYin implements IAlgorithm {
    private final static String TAG = "C2PY";
    private final static String HEADER = "_pinyin_";
    private final int[] dict = new int[]{0, 1, 2, 3, 4, 5, 6}; // simulator chinese words dict
    private final int[] dict_pin = new int[]{2, 3, 1, 6, 1, 2, 1}; // each words has several pinyin

    private int[] chineseWords; // target chinese words
    private List<String> chineseWordsPin = new ArrayList<>(); // target pinyin of chinese words
    private int targetLen = 1;
    private Random random = new Random(System.nanoTime());

    /**
     * Gets instance with inner static class way
     *
     * @return the instance
     */
    public static ChineseToPinYin getInstance() {
        return Holder.INSTANCE;
    }

    private ChineseToPinYin() {
    }

    private static class Holder {
        private static final ChineseToPinYin INSTANCE = new ChineseToPinYin();
    }

    @Override
    public void startAlgorithm() {
        ChineseToPinYin.getInstance().start();
    }

    private void mockData() {
        // mock chinese words 5~10
        int len = random.nextInt(5) + 10;
        chineseWords = new int[len];
        int num;
        for (int i = 0; i < len; i++) {
            num = random.nextInt(dict.length);
            chineseWords[i] = dict[num];
        }
        Log.d(TAG, Arrays.toString(chineseWords));
    }

    public void start() {
        mockData();
        // calculator right result number
        for (int i = 0; i < chineseWords.length; i++) {
            int word = chineseWords[i];
            int pin_num = dict_pin[word];
            targetLen = targetLen * pin_num;
        }

        strategyRecursive();
        strategyWhile();
    }

    private void strategyRecursive() {
        // tag start time
        long startTime = System.nanoTime();
        // begin recursive to get get chineseWordsPin
        recursiveLoop(0, new StringBuilder());
        // make sure result is right
        if (targetLen == chineseWordsPin.size()) {
            Log.d(TAG, "strategyRecursive got right number " + targetLen);
        }
        // tag end time
        long diffMs = TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
        Log.d(TAG, "strategyRecursive time diff = " + diffMs + "ms");

        chineseWordsPin.clear();
        chineseWordsPin = null;
    }

    private void recursiveLoop(int index, StringBuilder result) {
        // this work has how many pin yin
        int word = chineseWords[index];
        int pin_num = dict_pin[word];
        for (int i = 0; i < pin_num; i++) {
            StringBuilder str = new StringBuilder(result);
            str.append("**" + word + HEADER + i + " ");
            if (index == chineseWords.length - 1) {
                chineseWordsPin.add(str.toString() + "\r\n");
            } else {
                recursiveLoop(index + 1, str);
            }
        }
    }

    private void strategyWhile() {
        // tag start time
        long startTime = System.nanoTime();
        whileLoop();
        // make sure result is right
        if (targetLen == chineseWordsPin.size()) {
            Log.d(TAG, "strategyWhile got right number " + targetLen);
        }
        // tag end time
        long diffMs = TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
        Log.d(TAG, "strategyWhile time diff = " + diffMs + "ms");

        chineseWordsPin.clear();
        chineseWordsPin = null;
    }

    private void whileLoop() {
        int index = 0;
        chineseWordsPin = new ArrayList<>();
        List<String> temp_result = new ArrayList<>();
        String temp;
        while (index < chineseWords.length) {
            int word = chineseWords[index];
            int pin_num = dict_pin[word];
            // extend capacity if needed
            if (index != 0) {
                temp_result.clear();
                temp_result.addAll(chineseWordsPin);
                for (int i = 1; i < pin_num; i++) {
                    chineseWordsPin.addAll(temp_result);
                }
            }
            // append index multi pinyin
            for (int i = 0; i < pin_num; i++) {
                temp = word + HEADER + i + " ";
                // if at the start index == 0, only add, not append
                if (index == 0) {
                    chineseWordsPin.add(temp);
                    continue;
                }
                // append multi pinyin
                for (int j = 0; j < chineseWordsPin.size(); j++) {
                    chineseWordsPin.set(j, chineseWordsPin.get(j) + temp);
                }
            }

            index++;
        }
    }


}
