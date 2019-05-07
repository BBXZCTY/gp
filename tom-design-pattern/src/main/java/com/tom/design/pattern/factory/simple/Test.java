package com.tom.design.pattern.factory.simple;

public class Test {

    public static void main(String[] args) {
        AnimalFactory animalFactory = new AnimalFactory();
        Animal animal = animalFactory.getAnimal(Dog.class);
        animal.run();
    }

}