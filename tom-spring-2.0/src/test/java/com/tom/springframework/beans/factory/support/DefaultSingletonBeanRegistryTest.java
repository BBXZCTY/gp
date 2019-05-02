package com.tom.springframework.beans.factory.support;

import com.tom.springframework.beans.factory.ObjectFactoryTest;

public class DefaultSingletonBeanRegistryTest {

    private static int a;

    {
        System.out.println(888);
    }

    static {
        System.out.println(666);
    }

    public Object getSingleton(String beanName, ObjectFactoryTest<?> singletonFactory) {
        System.out.println("step 2");
        Object singletonObject = singletonFactory.getObject();
        System.out.println("step 4");
        System.out.println("singletonObject=" + singletonObject);
        return singletonObject;
    }


}