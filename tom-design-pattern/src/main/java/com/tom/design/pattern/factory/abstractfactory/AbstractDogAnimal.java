package com.tom.design.pattern.factory.abstractfactory;

public abstract class AbstractDogAnimal implements Animal {

    public void run() {
        System.out.println("小狗在奔跑");
    }

    public void getType() {
        System.out.println("小狗是犬科动物");
    }
}