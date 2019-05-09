package com.tom.design.pattern.observer.v1;

import java.util.Observable;

/**
 * 被观察者
 */
public class GPer extends Observable {

    private String name = "GPer生态圈";

    private static GPer gPer = null;

    private static Object lock = new Object();

    public static GPer getInstance() {
        synchronized (lock) {
            if (null == gPer) {
                gPer = new GPer();
            }
            return gPer;
        }
    }

    public String getName() {
        return name;
    }

    public void publishQuestion(Question question) {
        System.out.println(question.getUsername() + "在" + this.getName() + "上提交了一个问题");
        setChanged();
        notifyObservers(question);
    }


}