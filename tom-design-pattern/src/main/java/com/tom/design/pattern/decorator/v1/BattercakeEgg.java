package com.tom.design.pattern.decorator.v1;

public class BattercakeEgg extends Battercake {

    private String msg = "1个鸡蛋";

    private Integer price = 1;

    @Override
    public String getMsg() {
        return super.getMsg() + this.msg;
    }

    @Override
    public Integer getPrice() {
        return super.getPrice() + this.price;
    }
}