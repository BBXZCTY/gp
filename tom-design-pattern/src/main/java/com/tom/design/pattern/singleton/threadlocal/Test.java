package com.tom.design.pattern.singleton.threadlocal;

public class Test {

    public static void main(String[] args) {
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());

        Thread t1 = new Thread(new ExectorThreadLocal());
        Thread t2 = new Thread(new ExectorThreadLocal());

        t1.start();
        t2.start();
    }

}