package com.tom.design.pattern.factory.method;

public class Test {

    public static void main(String[] args) {
        AnimalFactory animalFactory = new DogFactory();
        Animal animal = animalFactory.getAnimal();
        animal.run();
    }

}