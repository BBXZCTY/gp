package com.tom.design.pattern.factory.abstractfactory;

public abstract class AbstractTigerAnimal implements Animal {

    public void run() {
        System.out.println("老虎在奔跑");
    }

    public void getType() {
        System.out.println("老虎是大型猫科动物");
    }
}