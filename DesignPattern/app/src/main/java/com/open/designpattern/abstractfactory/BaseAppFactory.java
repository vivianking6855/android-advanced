package com.open.designpattern.abstractfactory;

/**
 * Created by vivian on 2017/7/4.
 */

public abstract class BaseAppFactory {
    public abstract BaseTextEditor createTextEditor();

    public abstract BaseEditor createImageEditor();
}
