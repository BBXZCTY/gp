package com.test;

import javax.annotation.Resource;
import java.lang.reflect.Field;

public class Person {

    private String name;
    private Integer age;
    private String addr;
    @Resource
    private TT tt;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", addr='" + addr + '\'' +
                ", tt=" + tt +
                '}';
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        Class<?> aClass = Class.forName("com.test.Person");
        Class<?> aClass1 = Class.forName("com.test.TT");
        Object newInstance = aClass.newInstance();
        Object newInstance1 = aClass1.newInstance();

        Field declaredField = aClass.getDeclaredField("name");
        Field declaredField1 = aClass.getDeclaredField("age");
        Field declaredField2 = aClass.getDeclaredField("tt");

        declaredField.set(newInstance, "张三");
        declaredField1.set(newInstance, 23);
        declaredField2.set(newInstance, newInstance1);
        System.out.println(newInstance);

        System.out.println(declaredField2.getType().getName());

    }

}