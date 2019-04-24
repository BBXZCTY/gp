package com.tom.demo.service.impl;

import com.tom.demo.service.IDemoService;
import com.tom.framework.annotation.TomService;

@TomService
public class DemoServiceImpl implements IDemoService {

    @Override
    public void test() {
        System.out.println("DemoServiceImpl, test Service");
    }

    @Override
    public void hello(String name, Integer age) {
        System.out.println("DemoServiceImpl, my name is " + name + " age is " + age);
    }
}