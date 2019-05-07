package com.tom.design.pattern.factory.abstractfactory;

public abstract class AbstractCatAnimal implements Animal {

    public void run() {
        System.out.println("小猫在奔跑");
    }

    public void getType() {
        System.out.println("小猫是小型猫科动物");
    }
}