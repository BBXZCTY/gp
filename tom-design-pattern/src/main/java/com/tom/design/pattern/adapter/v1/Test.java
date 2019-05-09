package com.tom.design.pattern.adapter.v1;

public class Test {

    public static void main(String[] args) {
        IUserInfo userInfo1 = new UserInfo();
        userInfo1.getMobileNumber();

        IUserInfo userInfo2 = new OuterUserInfo();
        userInfo2.getMobileNumber();
    }

}