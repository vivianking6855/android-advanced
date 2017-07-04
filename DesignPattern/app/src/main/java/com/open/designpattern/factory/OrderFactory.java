package com.open.designpattern.factory;

/**
 * Created by vivian on 2017/7/4.
 */

public class OrderFactory {
    //创建一份巨无霸套餐
    public static Order createBigMacCombo() {
        return new Order.OrderBuilder()
                .addBurger(new BigMac())
                .addBeverage(new Coke())
                .build();
    }
}