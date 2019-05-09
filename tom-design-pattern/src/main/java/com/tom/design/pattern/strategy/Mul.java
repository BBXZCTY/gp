package com.tom.design.pattern.strategy;

public class Mul implements CalculatorStrategy {

    public int exec(int a, int b) {
        System.out.print("开始做乘法，得数为：");
        return a * b;
    }
}