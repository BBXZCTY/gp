package com.tom.design.pattern.strategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Context {

    private static Map<Integer, CalculatorStrategy> map = new ConcurrentHashMap<Integer, CalculatorStrategy>();

    static {
        map.put(CalcTypeEnum.ADD.getType(), new Add());
        map.put(CalcTypeEnum.SUB.getType(), new Sub());
        map.put(CalcTypeEnum.MUL.getType(), new Mul());
    }


    public int exec(int a, int b, Integer type) {
        return map.get(type).exec(a, b);
    }
}