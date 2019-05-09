package com.tom.design.pattern.strategy;

public class Add implements CalculatorStrategy{

    public int exec(int a, int b) {
        System.out.print("开始做加法，得数为：");
        return a + b;
    }
}