package com.open.designpattern.strategy;

/**
 * Created by vivian on 2017/7/5.
 */

public class TravelContext {
    IStrategy strategy;

    public IStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(IStrategy strategy) {
        this.strategy = strategy;
    }

    public void travel() {
        if (strategy != null) {
            strategy.travel();
        }
    }
}
