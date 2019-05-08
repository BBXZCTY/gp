package com.tom.design.pattern.decorator.v1;

public class BattercakeEggSausage extends BattercakeEgg {

    private String msg = "1根香肠";

    private Integer price = 2;

    @Override
    public String getMsg() {
        return super.getMsg() + this.msg;
    }

    @Override
    public Integer getPrice() {
        return super.getPrice() + this.price;
    }
}