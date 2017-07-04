package com.open.designpattern.abstractfactory;

/**
 * Created by vivian on 2017/7/4.
 */

public class MacTextEditor extends BaseEditor {
    @Override
    public void edit() {
        System.out.println("文本编辑器,edit -- Mac版");
    }

    @Override
    public void save() {
        System.out.println("文本编辑器,edit -- Mac版");
    }
}
