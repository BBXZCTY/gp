package com.tom.design.pattern.prototype;

public class Client {

    public static void main(String[] args) {

        MailClone mailClone = new MailClone(new AdvTemplate());

        for (int i = 0; i < 50; i++) {
            new Thread(new SendMail(mailClone)).start();
        }
    }

}