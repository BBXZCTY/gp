package com.tom.design.pattern.factory.simple;

public class AnimalFactory {

    Animal getAnimal(Class<? extends Animal> clazz) {
        Animal animal = null;
        try {
            animal = (Animal) Class.forName(clazz.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return animal;
    }


}