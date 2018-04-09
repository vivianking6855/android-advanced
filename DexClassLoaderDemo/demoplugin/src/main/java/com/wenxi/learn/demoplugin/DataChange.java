package com.wenxi.learn.demoplugin;

import com.wenxi.learn.plugin.IChange;

public class DataChange implements IChange {

    @Override
    public String start() {
        return "Change from plugin";
    }
}
