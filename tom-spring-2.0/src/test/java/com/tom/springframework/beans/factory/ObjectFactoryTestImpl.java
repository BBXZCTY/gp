package com.tom.springframework.beans.factory;

public class ObjectFactoryTestImpl implements ObjectFactoryTest {

    private static int a ;

    public ObjectFactoryTestImpl() {
        System.out.println("999");
    }
    {
        System.out.println("888");
    }
    static {
        System.out.println("666");
    }



    @Override
    public Object getObject() {
        System.out.println("step 3");
        return null;
    }
}