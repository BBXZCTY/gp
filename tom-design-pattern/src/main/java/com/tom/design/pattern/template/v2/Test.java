package com.tom.design.pattern.template.v2;

public class Test {

    public static void main(String[] args) {
        HummerModel hummerModel1 = new HummerH1Model();
        HummerModel hummerModel2 = new HummerH2Model();

        ((HummerH1Model) hummerModel1).setAlarmFlag(true);
        hummerModel1.run();
        System.out.println("=========");
        hummerModel2.run();
    }

}