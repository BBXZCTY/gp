package com.tom.design.pattern.strategy;

public class Test {

    public static void main(String[] args) {
        Context context = new Context();

        int add = context.exec(3, 8, 1);
        System.out.println(add);
        int sub = context.exec(3, 8, 2);
        System.out.println(sub);
        int mul = context.exec(3, 8, 3);
        System.out.println(mul);
    }

}