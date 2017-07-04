package com.open.designpattern.abstractfactory;

/**
 * Created by vivian on 2017/7/4.
 */

public class MacAppFactory extends BaseAppFactory {
    @Override
    public BaseTextEditor createTextEditor() {
        return new MacTextEditor();
    }

    @Override
    public BaseEditor createImageEditor() {
        return new MacImageEditor();
    }
}
