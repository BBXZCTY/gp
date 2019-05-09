package com.tom.design.pattern.template.v1;

public class HummerH2Model extends HummerModel {

    public void start() {
        System.out.println("悍马H2启动");
    }

    public void stop() {
        System.out.println("悍马H2停车");
    }

    public void alarm() {
        System.out.println("悍马H2鸣笛");
    }

    public void engineBoom() {
        System.out.println("悍马H2引擎");
    }

    public void run() {
        this.start();
        this.engineBoom();
        this.alarm();
        this.stop();
    }
}