package com.tom.design.pattern.singleton.seriable;

import java.io.Serializable;

/**
 * 序列化破坏单例
 */
public class SeriableSingleton implements Serializable {

    private static final SeriableSingleton INSTANCE = new SeriableSingleton();

    private SeriableSingleton(){}

    public static SeriableSingleton getInstance() {
        return INSTANCE;
    }

    /**
     * 保证序列化的时候也能实现单例
     * @return
     */
    public Object readResolve() {
        return INSTANCE;
    }


}