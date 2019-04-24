package com.test.invoke;


public class InvokeObj {
    public void show() {
        System.out.println("无参数show方法");
    }
    public void show(String name){
        System.out.println("show方法："+name);
    }
    public String[] arrayShow(String[] arr){
        return arr;
    }
    public String StringShow(String str){
        return str;
    }
    public int intShow(int num){
        return num;
    }
    public void stringIntShow(String name, Integer age) {
        System.out.println("name=" + name + ", age=" + age);
    }


}