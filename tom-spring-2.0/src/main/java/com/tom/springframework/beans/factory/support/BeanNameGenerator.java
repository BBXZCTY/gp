package com.tom.springframework.beans.factory.support;

import com.tom.springframework.beans.factory.config.BeanDefinition;

public interface BeanNameGenerator {

    String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry);

}
