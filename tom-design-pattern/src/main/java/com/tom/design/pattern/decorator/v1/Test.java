package com.tom.design.pattern.decorator.v1;

/**
 * 不用设计模式的写法
 * 缺点：一直在继承
 */
public class Test {

    public static void main(String[] args) {
        Battercake battercake = new Battercake();
        System.out.println(battercake.getMsg() + ", 总价格：" + battercake.getPrice());

        Battercake battercakeEgg = new BattercakeEgg();
        System.out.println(battercakeEgg.getMsg() + ", 总价格：" + battercakeEgg.getPrice());

        BattercakeEggSausage battercakeEggSausage = new BattercakeEggSausage();
        System.out.println(battercakeEggSausage.getMsg() + ", 总价格：" + battercakeEggSausage.getPrice());

    }

}