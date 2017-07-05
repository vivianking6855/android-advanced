package com.open.designpattern.strategy;

/**
 * Created by vivian on 2017/7/5.
 * before optimization
 */

public class TravelStrategy {
    enum Strategy {
        WALK, PLANE, SUBWAY
    }

    private Strategy strategy;

    public TravelStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void travel() {
        if (strategy == Strategy.WALK) {
            print("walk");
        } else if (strategy == Strategy.PLANE) {
            print("plane");
        } else if (strategy == Strategy.SUBWAY) {
            print("subway");
        }
    }

    public void print(String str) {
        System.out.println("出行旅游的方式为：" + str);
    }

    public static void main(String[] args) {
        TravelStrategy walk = new TravelStrategy(Strategy.WALK);
        walk.travel();

        TravelStrategy plane = new TravelStrategy(Strategy.PLANE);
        plane.travel();

        TravelStrategy subway = new TravelStrategy(Strategy.SUBWAY);
        subway.travel();
    }
}
