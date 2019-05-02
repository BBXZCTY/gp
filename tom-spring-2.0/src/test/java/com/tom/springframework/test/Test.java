package com.tom.springframework.test;

import com.tom.springframework.test.config.TestConfig;
import com.tom.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {

    @org.junit.Test
    public void test01() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfig.class);
    }

}