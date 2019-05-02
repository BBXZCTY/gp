package com.tom.springframework.beans.factory;

public interface ListableBeanFactory extends BeanFactory {

    boolean containsBeanDefinition(String beanName);

}
