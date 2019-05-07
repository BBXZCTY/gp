package com.tom.design.pattern.factory.abstractfactory;

public interface AnimalFactory {
    Animal createCat();
    Animal createDog();
    Animal createTiger();
}