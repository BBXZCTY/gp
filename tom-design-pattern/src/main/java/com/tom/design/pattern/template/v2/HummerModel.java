package com.tom.design.pattern.template.v2;

public abstract class HummerModel {

    protected abstract void start();

    protected abstract void stop();

    protected abstract void alarm();

    protected abstract void engineBoom();

    protected final void run() {
        this.start();
        this.engineBoom();
        if (isAlarm()) {
            this.alarm();
        }
        this.stop();
    }

    protected boolean isAlarm() { // 钩子方法
        return true;
    }


}