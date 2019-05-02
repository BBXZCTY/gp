package com.tom.springframework.beans.factory.support;

import com.tom.springframework.beans.factory.config.BeanDefinition;
import com.tom.springframework.core.AliasRegistry;

public interface BeanDefinitionRegistry extends AliasRegistry {

    boolean containsBeanDefinition(String beanName);

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);



}
