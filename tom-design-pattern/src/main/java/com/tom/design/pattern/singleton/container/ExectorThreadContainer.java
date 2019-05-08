package com.tom.design.pattern.singleton.container;

public class ExectorThreadContainer implements Runnable {

    public void run() {
        Object containerSingleton =
                ContainerSingleton.getBean("com.tom.design.pattern.singleton.container.Person");
        System.out.println(Thread.currentThread().getName() + ":" + containerSingleton);
    }
}