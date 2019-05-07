package com.tom.design.pattern.factory.method;

public class DogFactory extends AnimalFactory {

    protected Animal getAnimal() {
        return new Dog();
    }
}