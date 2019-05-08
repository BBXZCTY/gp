package com.tom.design.pattern.singleton.hungry;

/**
 * 饿汉式单例
 * 在类加载的时候就立即初始化，并且创建单例对象，绝对线程安全。适用在单例对象较少的情况。
 * 优点：没有加任何的锁，执行效率高，在用户体验上比懒汉式好
 * 缺点：不管用不用都占空间，浪费内存
 */
public class HungrySingleton {

    private static final HungrySingleton instance = new HungrySingleton();

    private HungrySingleton(){}

    public static HungrySingleton getInstance() {
        return instance;
    }

}