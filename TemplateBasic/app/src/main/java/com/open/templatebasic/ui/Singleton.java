package com.open.templatebasic.ui;

/**
 * Created by vivian on 2017/5/22.
 * refers to "http://www.jianshu.com/p/eb30a388c5fc"
 */

public class Singleton {
    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    private Singleton() {
    }

    public static final Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}