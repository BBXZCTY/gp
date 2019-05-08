package com.tom.design.pattern.singleton.lazy.inner;

public class ExectorThreadLazyInner implements Runnable {

    public void run() {
        LazyInnerClassSingleton lazyInnerClassSingleton = LazyInnerClassSingleton.getInstance();
        System.out.println(Thread.currentThread().getName() + ":" + lazyInnerClassSingleton);
    }

}