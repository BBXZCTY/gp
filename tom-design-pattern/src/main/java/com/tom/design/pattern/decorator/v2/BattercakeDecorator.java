package com.tom.design.pattern.decorator.v2;

public abstract class BattercakeDecorator extends Battercake {

    private Battercake battercake;

    public BattercakeDecorator(Battercake battercake) {
        this.battercake = battercake;
    }

    protected abstract void doSomething();

    protected String getMsg() {
        return this.battercake.getMsg();
    }

    protected Integer getPrice() {
        return this.battercake.getPrice();
    }
}