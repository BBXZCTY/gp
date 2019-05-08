package com.tom.design.pattern.decorator.v2;

public class EggDecorator extends BattercakeDecorator {

    private String msg = "+1个鸡蛋";

    private Integer price = 1;

    public EggDecorator(Battercake battercake) {
        super(battercake);
    }

    protected void doSomething() {

    }

    @Override
    protected String getMsg() {
        return super.getMsg() + this.msg;
    }

    @Override
    public Integer getPrice() {
        return super.getPrice() + this.price;
    }
}