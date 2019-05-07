package com.tom.design.pattern.factory.abstractfactory;

public class Test {

    public static void main(String[] args) {
        AnimalFactory maleFactory = new MaleFactory();
        AnimalFactory femaleFactory = new FemaleFactory();

        Animal maleCat = maleFactory.createCat();
        Animal maleDog = maleFactory.createDog();
        Animal maleTiger = maleFactory.createTiger();

        Animal femaleCat = femaleFactory.createCat();
        Animal femaleDog = femaleFactory.createDog();
        Animal femaleTiger = femaleFactory.createTiger();

        maleCat.getType();
        maleCat.getSex();
        maleCat.run();
        print();

        maleDog.getType();
        maleDog.getSex();
        maleDog.run();
        print();

        maleTiger.getType();
        maleTiger.getSex();
        maleTiger.run();
        print();

        femaleCat.getType();
        femaleCat.getSex();
        femaleCat.run();
        print();

        femaleDog.getType();
        femaleDog.getSex();
        femaleDog.run();
        print();

        femaleTiger.getType();
        femaleTiger.getSex();
        femaleTiger.run();
        print();
    }

    private static void print() {
        System.out.println("================");
    }


}