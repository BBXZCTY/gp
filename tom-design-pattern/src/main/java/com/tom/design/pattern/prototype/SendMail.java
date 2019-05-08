package com.tom.design.pattern.prototype;

import java.util.Random;

public class SendMail implements Runnable {

    MailClone mailClone;

    public SendMail() {

    }

    public SendMail(MailClone mailClone) {
        this.mailClone = mailClone.clone();
    }

    public void run() {
        send();
    }

    private void send() {
//        v1();
        v2();
    }

    private void v2() {
        mailClone.setTail("✘✘银行版权所有");

        mailClone.setAppellation(getRandString(6) + " 先生/女士");
        mailClone.setReceiver(getRandString(6) + "@" + getRandString(8));
        sendMail2(mailClone);
    }

    private void v1() {
        Mail mail = new Mail(new AdvTemplate());
        mail.setTail("✘✘银行版权所有");

        mail.setAppellation(getRandString(6) + " 先生/女士");
        mail.setReceiver(getRandString(6) + "@" + getRandString(8));
        sendMail1(mail);
    }

    private void sendMail2(MailClone mail) {
        System.out.println("标题："+mail.getSubject() + " 姓名：" + mail.getAppellation() + "\t收件人："
                +mail.getReceiver()+"\t...发送成功！");
    }

    private void sendMail1(Mail mail) {
        System.out.println("标题："+mail.getSubject() + " 姓名：" + mail.getAppellation() + "\t收件人："
                +mail.getReceiver()+"\t...发送成功！");
    }

    private String getRandString(int maxLength) {
        String source ="abcdefghijklmnopqrskuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        Random rand = new Random();
        for(int i=0;i<maxLength;i++){
            sb.append(source.charAt(rand.nextInt(source.length())));
        }
        return sb.toString();
    }
}