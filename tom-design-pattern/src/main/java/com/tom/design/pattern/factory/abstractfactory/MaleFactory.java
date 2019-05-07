package com.tom.design.pattern.factory.abstractfactory;

public class MaleFactory implements AnimalFactory {

    public Animal createCat() {
        return new MaleCatAnimal();
    }

    public Animal createDog() {
        return new MaleDogAnimal();
    }

    public Animal createTiger() {
        return new MaleTigerAnimal();
    }
}