package com.tom.design.pattern.singleton.lazy.simple;

public class ExectorThreadLazySimple implements Runnable {

    public void run() {
        LazySimpleSingleton lazySimpleSingleton = LazySimpleSingleton.getInstance3();
        System.out.println(Thread.currentThread().getName() + ":" + lazySimpleSingleton);
    }

}