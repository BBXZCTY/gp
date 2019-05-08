package com.tom.design.pattern.singleton.enums;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EnumSingletonTest {

    public static void main(String[] args) {

        test1();


    }

    private static void test1() {
        try {
            EnumSingleton e1 = null;
            EnumSingleton e2 = EnumSingleton.getInstance();
            e2.setData(new Object());

            FileOutputStream fos = new FileOutputStream("EnumSingleton.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.flush();
            oos.close();

            FileInputStream fis = new FileInputStream("EnumSingleton.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            e1 = (EnumSingleton) ois.readObject();
            ois.close();

            System.out.println(e1.getData());
            System.out.println(e2.getData());
            System.out.println(e1.getData() == e2.getData());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}