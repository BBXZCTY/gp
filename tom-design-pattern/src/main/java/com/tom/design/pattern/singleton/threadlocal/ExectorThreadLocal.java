package com.tom.design.pattern.singleton.threadlocal;

public class ExectorThreadLocal implements Runnable {

    public void run() {
        ThreadLocalSingleton threadLocalSingleton = ThreadLocalSingleton.getInstance();
        System.out.println(Thread.currentThread().getName() + ":" + threadLocalSingleton);
    }
}