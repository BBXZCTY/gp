package com.tom.springframework.beans.factory.config;

import com.tom.springframework.util.Assert;

public class BeanExpressionContext {

    private final ConfigurableBeanFactory beanFactory;

    private final Scope scope;

    public BeanExpressionContext(ConfigurableBeanFactory beanFactory, Scope scope) {
        Assert.notNull(beanFactory, "BeanFactory must not be null");
        this.beanFactory = beanFactory;
        this.scope = scope;
    }

}