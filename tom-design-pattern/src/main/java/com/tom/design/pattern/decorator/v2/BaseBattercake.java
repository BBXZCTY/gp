package com.tom.design.pattern.decorator.v2;

public class BaseBattercake extends Battercake {

    private String msg = "煎饼";

    private Integer price = 5;

    protected String getMsg() {
        return this.msg;
    }

    protected Integer getPrice() {
        return this.price;
    }
}