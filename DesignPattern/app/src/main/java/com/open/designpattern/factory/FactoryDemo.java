package com.open.designpattern.factory;

/**
 * Created by vivian on 2017/7/4.
 */

public class FactoryDemo {
    public static void start() {
        Order order = OrderFactory.createBigMacCombo();
        System.out.println(order.makeOrder());
    }
}
