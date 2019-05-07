package com.tom.design.pattern.factory.abstractfactory;

public class FemaleFactory implements AnimalFactory {

    public Animal createCat() {
        return new FemaleCatAnimal();
    }

    public Animal createDog() {
        return new FemaleDogAnimal();
    }

    public Animal createTiger() {
        return new FemaleTigerAnimal();
    }
}