package com.tom.design.pattern.observer.v1;

public class Test {

    public static void main(String[] args) {
        GPer gPer = GPer.getInstance();

        Teacher tom = new Teacher("Tom");
        Teacher mic = new Teacher("Mic");

        gPer.addObserver(tom);
        gPer.addObserver(mic);

        Question question = new Question();
        question.setUsername("张三");
        question.setContent("设计模式在项目中具体的应用场景能介绍一下吗");

        gPer.publishQuestion(question);
    }

}