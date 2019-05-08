package com.tom.design.pattern.singleton.lazy.inner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author: hfr
 * @Date: 2019-05-07 20:12
 * @Version 1.0
 */
public class LazyInnerClassSingletonTest {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        test1();
//        test2();
    }

    private static void test2() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<?> clazz = LazyInnerClassSingleton.class;
        Constructor<?> constructor = clazz.getDeclaredConstructor(null);
        constructor.setAccessible(true);
        Object o1 = constructor.newInstance();
        Object o2 = constructor.newInstance();
        System.out.println(o1 == o2);
    }


    private static void test1() {
        Thread t1 = new Thread(new ExectorThreadLazyInner());
        Thread t2 = new Thread(new ExectorThreadLazyInner());
        Thread t3 = new Thread(new ExectorThreadLazyInner());
        Thread t4 = new Thread(new ExectorThreadLazyInner());
        Thread t5 = new Thread(new ExectorThreadLazyInner());

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        System.out.println("888");
    }
}