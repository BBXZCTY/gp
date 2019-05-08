package com.tom.design.pattern.prototype;

public class MailClone implements Cloneable {

    private String receiver;

    private String subject;

    private String appellation;

    private String content;

    private String tail;

    public MailClone(AdvTemplate advTemplate) {
        this.subject = advTemplate.getAdvSubject();
        this.content = advTemplate.getAdvContext();
    }

    @Override
    protected MailClone clone() {
        MailClone mailClone = null;
        try {
            mailClone = (MailClone) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return mailClone;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAppellation() {
        return appellation;
    }

    public void setAppellation(String appellation) {
        this.appellation = appellation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }
}