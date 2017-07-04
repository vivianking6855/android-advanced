package com.open.designpattern.factory;

/**
 * Created by vivian on 2017/7/4.
 */

public class Order {
    private IBurgers mBurger;
    private IBeverages mBeverages;

    private Order(OrderBuilder builder){
        mBurger = builder. mBurger;
        mBeverages = builder. mBeverages;
    }


    public String makeOrder(){
        StringBuilder sb = new StringBuilder();
        if ( mBurger!= null) {
            sb.append( mBurger.makeBurger()).append( " ");
        }
        if ( mBeverages!= null) {
            sb.append( mBeverages.makeDrinking()).append( " ");
        }
        return sb.toString();
    }

    public static class OrderBuilder{
        private IBurgers mBurger;
        private IBeverages mBeverages;
        public OrderBuilder(){

        }
        public OrderBuilder addBurger(IBurgers burgers){
            this. mBurger = burgers;
            return this;
        }
        public OrderBuilder addBeverage(IBeverages beverages){
            this. mBeverages = beverages;
            return this;
        }

        public Order build(){
            return new Order( this);
        }
    }
}
