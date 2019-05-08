package com.tom.design.pattern.singleton.lazy.simple;

public class LazySimpleSingleton {

    private static LazySimpleSingleton instance = null;

    private LazySimpleSingleton(){}

    public static LazySimpleSingleton getInstance() {
        if (null == instance) {
            instance = new LazySimpleSingleton();
        }
        return instance;
    }

    public synchronized static LazySimpleSingleton getInstance2() {
        if (null == instance) {
            instance = new LazySimpleSingleton();
        }
        return instance;
    }

    public static LazySimpleSingleton getInstance3() {
        if (null == instance) {
            synchronized (LazySimpleSingleton.class) {
                if (null == instance) {
                    instance = new LazySimpleSingleton();
                }
            }
        }
        return instance;
    }

}