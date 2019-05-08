package com.tom.design.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyTest implements InvocationHandler {

    private Object targetClass;

    public DynamicProxyTest(Object targetClass) {
        this.targetClass = targetClass;
    }

    private void before() {
        System.out.println("before");
    }

    private void after() {
        System.out.println("after");
    }

    public Object bind() {
        return Proxy.newProxyInstance(targetClass.getClass().getClassLoader(), targetClass.getClass().getInterfaces(), this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object invoke = method.invoke(targetClass, args);
        after();
        return invoke;
    }

    public static void main(String[] args) {
        DynamicProxyTest dynamicProxyTest1 = new DynamicProxyTest(new UserServiceImpl());
        IUserService userService = (IUserService) dynamicProxyTest1.bind();
        userService.getName();
        System.out.println("===========");
        DynamicProxyTest dynamicProxyTest2 = new DynamicProxyTest(new OrderServiceImpl());
        IOrderService orderService = (IOrderService) dynamicProxyTest2.bind();
        orderService.buy(userService);
    }
}

interface IUserService {void getName();}

class UserServiceImpl implements IUserService {

    public void getName() {
        System.out.println("我叫张三");
    }

}

interface IOrderService {void buy(IUserService userService);}

class OrderServiceImpl implements IOrderService {

    public void buy(IUserService userService) {
        userService.getName();
        System.out.println("我要去买东西");
    }
}