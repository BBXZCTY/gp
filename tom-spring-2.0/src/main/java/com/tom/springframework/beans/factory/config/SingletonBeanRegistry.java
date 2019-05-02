package com.tom.springframework.beans.factory.config;

public interface SingletonBeanRegistry {

    boolean containsSingleton(String beanName);

    void registerSingleton(String beanName, Object singletonObject);


}
