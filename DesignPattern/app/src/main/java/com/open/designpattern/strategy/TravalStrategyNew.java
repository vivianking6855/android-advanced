package com.open.designpattern.strategy;

/**
 * Created by vivian on 2017/7/5.
 */

public class TravalStrategyNew {
    public static void main(String[] args) {
        TravelContext travelContext = new TravelContext();
        travelContext.setStrategy(new PlaneStrategy());
        travelContext.travel();
        travelContext.setStrategy(new WalkStrategy());
        travelContext.travel();
        travelContext.setStrategy(new SubwayStrategy());
        travelContext.travel();
    }
}