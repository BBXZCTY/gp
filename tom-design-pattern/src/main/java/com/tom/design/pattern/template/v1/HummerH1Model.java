package com.tom.design.pattern.template.v1;

public class HummerH1Model extends HummerModel {

    public void start() {
        System.out.println("悍马H1启动");
    }

    public void stop() {
        System.out.println("悍马H1停车");
    }

    public void alarm() {
        System.out.println("悍马H1鸣笛");
    }

    public void engineBoom() {
        System.out.println("悍马H1引擎");
    }

    public void run() {
        this.start();
        this.engineBoom();
        this.alarm();
        this.stop();
    }
}