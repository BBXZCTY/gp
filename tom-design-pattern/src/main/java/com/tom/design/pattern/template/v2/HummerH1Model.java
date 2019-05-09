package com.tom.design.pattern.template.v2;

public class HummerH1Model extends HummerModel {

    private boolean alarmFlag;

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

    @Override
    protected boolean isAlarm() {
        return this.alarmFlag;
    }

    public void setAlarmFlag(boolean alarmFlag) {
        this.alarmFlag = alarmFlag;
    }
}