package com.open.designpattern.strategy;

/**
 * Created by vivian on 2017/7/5.
 */

public class WalkStrategy implements IStrategy {
    @Override
    public void travel() {
        System.out.println("walk");
    }
}
