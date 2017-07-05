package com.open.designpattern.strategy;

/**
 * Created by vivian on 2017/7/5.
 */

public class SubwayStrategy implements IStrategy {
    @Override
    public void travel() {
        System.out.println("subway");
    }
}
