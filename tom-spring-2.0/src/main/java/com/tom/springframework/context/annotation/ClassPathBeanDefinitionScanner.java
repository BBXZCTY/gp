package com.tom.springframework.context.annotation;

import com.tom.springframework.beans.factory.support.BeanDefinitionRegistry;

public class ClassPathBeanDefinitionScanner {

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this(registry, true);
    }

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
//        this(registry, useDefaultFilters, getOrCreateEnvironment(registry));
    }



}