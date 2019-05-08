package com.tom.design.pattern.singleton.lazy.inner;

public class LazyInnerClassSingleton {

    private LazyInnerClassSingleton() {
        if (LazyHoler.INSTANCE != null) { // 防止反射破坏单例
            throw new RuntimeException("不允许创建多个实例");
        }
    }

    public static final LazyInnerClassSingleton getInstance() {
        return LazyHoler.INSTANCE;
    }

    private static class LazyHoler {
        private static final LazyInnerClassSingleton INSTANCE = new LazyInnerClassSingleton();
    }

}