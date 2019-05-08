package com.tom.design.pattern.singleton.lazy.simple;

/**
 * 第一次运行结果：
 * Thread-0:com.tom.design.pattern.singleton.lazysimple.LazySimpleSingleton@73e3c327
 * Thread-1:com.tom.design.pattern.singleton.lazysimple.LazySimpleSingleton@2fd38f41
 *
 * 第二次运行结果：
 * Thread-1:com.tom.design.pattern.singleton.lazysimple.LazySimpleSingleton@3cadc1b2
 * Thread-0:com.tom.design.pattern.singleton.lazysimple.LazySimpleSingleton@3cadc1b2
 *
 * 反复几次运行，发现有时候结果相同，有时候结果不同
 * 结论：意味着这种懒汉式写法存在线程安全隐患
 */
public class LazySimpleSingletonTest {

    public static void main(String[] args) {
        Thread t1 = new Thread(new ExectorThreadLazySimple());
        Thread t2 = new Thread(new ExectorThreadLazySimple());
        Thread t3 = new Thread(new ExectorThreadLazySimple());
        Thread t4 = new Thread(new ExectorThreadLazySimple());
        Thread t5 = new Thread(new ExectorThreadLazySimple());
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        System.out.println("666");
    }

}