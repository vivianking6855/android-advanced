package com.open.designpattern.abstractfactory;

/**
 * Created by vivian on 2017/7/4.
 */

public class MacImageEditor extends BaseEditor {
    @Override
    public void edit() {
        System.out.println("图片处理编辑器,edit -- Mac版");
    }

    @Override
    public void save() {
        System.out.println("图片处理编辑器,save -- Mac版");
    }
}
