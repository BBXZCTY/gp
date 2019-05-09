package com.tom.design.pattern.adapter.v1;

public class UserInfo implements IUserInfo {

    public String getUserName() {
        System.out.println("员工姓名");
        return null;
    }

    public String getHomeAddress() {
        System.out.println("家庭地址");
        return null;
    }

    public String getJobPosition() {
        System.out.println("职位");
        return null;
    }

    public String getMobileNumber() {
        System.out.println("手机号");
        return null;
    }

    public String getOfficeTelNumber() {
        System.out.println("工作电话");
        return null;
    }

    public String getHomeTelNumber() {
        System.out.println("家庭电话");
        return null;
    }
}