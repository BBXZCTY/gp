package com.tom.design.pattern.singleton.container;

public class ContainerSingletonTest {
    public static void main(String[] args) {
//        test1();
        test2();
    }

    private static void test2() {
        Thread t1 = new Thread(new ExectorThreadContainer());
        Thread t2 = new Thread(new ExectorThreadContainer());
        Thread t3 = new Thread(new ExectorThreadContainer());
        Thread t4 = new Thread(new ExectorThreadContainer());
        Thread t5 = new Thread(new ExectorThreadContainer());
        Thread t6 = new Thread(new ExectorThreadContainer());

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }

    private static void test1() {
        Object containerSingleton1 =
                ContainerSingleton.getBean("com.tom.design.pattern.singleton.container.Person");
        Object containerSingleton2 =
                ContainerSingleton.getBean("com.tom.design.pattern.singleton.container.Person");
        System.out.println(containerSingleton1);
        System.out.println(containerSingleton2);
        System.out.println(containerSingleton1 == containerSingleton2);
    }
}