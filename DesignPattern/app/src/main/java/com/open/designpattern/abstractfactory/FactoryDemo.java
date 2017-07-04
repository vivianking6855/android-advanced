package com.open.designpattern.abstractfactory;

/**
 * Created by vivian on 2017/7/4.
 */

public class FactoryDemo {
    public static void start() {
        BaseAppFactory factory = new MacAppFactory();
        BaseTextEditor textEditor = factory.createTextEditor();
        textEditor.edit();
        textEditor.save();

        BaseEditor imageEditor = factory.createImageEditor();
        imageEditor.edit();
        imageEditor.save();
    }
}
