package com.tom.springframework.beans.factory.support;

import com.tom.springframework.beans.factory.ObjectFactoryTest;
import com.tom.springframework.beans.factory.ObjectFactoryTestImpl;
import sun.dc.pr.PRError;

public class AbstractBeanFactoryTest extends DefaultSingletonBeanRegistryTest {

    protected Object doGetBean() {
        String beanName = "com.tom.springframework.test.config.TestConfig";
        System.out.println("step 1");
        Object sharedInstance = getSingleton(beanName, new ObjectFactoryTest<Object>() {
            @Override
            public Object getObject() {
                System.out.println("step 3");
                return "123";
            }
    });
        System.out.println("step 5");
        System.out.println("sharedInstance=" + sharedInstance);
    return sharedInstance;
    }

    protected Object doGetBean2() {
        String beanName = "com.tom.springframework.test.config.TestConfig";
        System.out.println("step 1");
        Object sharedInstance = getSingleton(beanName, new ObjectFactoryTestImpl());
        System.out.println("step 5");
        System.out.println("sharedInstance=" + sharedInstance);
        return sharedInstance;
    }

    public static void main(String[] args) {
        AbstractBeanFactoryTest abstractBeanFactoryTest = new AbstractBeanFactoryTest();
        abstractBeanFactoryTest.doGetBean();
        System.out.println("============");
        abstractBeanFactoryTest.doGetBean2();
    }

}