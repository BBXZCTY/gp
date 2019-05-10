package com.tom.design.pattern.observer.guava;

import com.google.common.eventbus.Subscribe;

public class GuavaEvent {

    @Subscribe
    public void subscribe(String s) {
        System.out.println("执行subscribe方法，传入参数为：" + s);
    }
}