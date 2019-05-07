package com.tom.design.pattern.factory.method;

public class CatFactory extends AnimalFactory {

    public Animal getAnimal() {
        return new Cat();
    }
}