package com.tom.demo.mvc.controller;

import com.tom.demo.service.IDemoService;
import com.tom.framework.annotation.TomAutowired;
import com.tom.framework.annotation.TomController;
import com.tom.framework.annotation.TomRequestMapping;
import com.tom.framework.annotation.TomRequestParam;

import javax.servlet.http.HttpServletRequest;

@TomController
@TomRequestMapping("/demo")
public class DemoController {

    @TomAutowired
    private IDemoService demoServiceImpl;

    // http://localhost:8080/demo/hello
    @TomRequestMapping("/hello")
    public String hello(HttpServletRequest request) {
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        System.out.println("DemoAction, name=" + name);
        System.out.println("DemoAction, age=" + age);
        demoServiceImpl.hello(name, Integer.valueOf(age));
        System.out.println(666);
        return "success";
    }

    @TomRequestMapping("/hi")
    public void hi() {
        demoServiceImpl.test();
    }

    // http://localhost:8080/demo/hello2
    @TomRequestMapping("/hello2")
    public String hello2(@TomRequestParam("name") String name, @TomRequestParam("age") Integer age) {
        demoServiceImpl.hello(name, age);
        System.out.println(666);
        return "success";
    }
}