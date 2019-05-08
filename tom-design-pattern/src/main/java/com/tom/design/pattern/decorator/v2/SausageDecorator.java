package com.tom.design.pattern.decorator.v2;

public class SausageDecorator extends BattercakeDecorator {

    private String msg = "+1根香肠";

    private Integer price = 2;

    public SausageDecorator(Battercake battercake) {
        super(battercake);
    }

    protected void doSomething() {

    }

    @Override
    public String getMsg() {
        return super.getMsg() + this.msg;
    }

    @Override
    public Integer getPrice() {
        return super.getPrice() + this.price;
    }
}