package com.open.designpattern.observer;

/**
 * Created by vivian on 2017/7/3.
 */

public class HouseDemo {
    public void start() {
        House h = new House(1000000);
        HousePriceObserver hpo1 = new HousePriceObserver("购房者A");
        HousePriceObserver hpo2 = new HousePriceObserver("购房者B");
        HousePriceObserver hpo3 = new HousePriceObserver("购房者C");
        h.addObserver(hpo1);
        h.addObserver(hpo2);
        h.addObserver(hpo3);
        System.out.println(h); // 输出房子价格
        h.setPrice(666666);    // 修改房子价格
        System.out.println(h); // 输出房子价格
    }
}
