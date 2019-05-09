package com.tom.design.pattern.template.v1;

public class Test {

    public static void main(String[] args) {
        HummerModel hummerModel1 = new HummerH1Model();
        HummerModel hummerModel2 = new HummerH2Model();
        HummerModel hummerModel3 = new HummerH3Model();

        hummerModel1.run();
        System.out.println("==============");
        hummerModel2.run();
        System.out.println("--------------");
        hummerModel3.run();
    }

}