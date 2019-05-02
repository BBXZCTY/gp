package com.tom.springframework.beans.factory.config;

import com.tom.springframework.util.StringValueResolver;

public class EmbeddedValueResolver implements StringValueResolver {

    private final BeanExpressionContext exprContext;

    private final BeanExpressionResolver exprResolver;


    public EmbeddedValueResolver(ConfigurableBeanFactory beanFactory) {
        this.exprContext = new BeanExpressionContext(beanFactory, null);
        this.exprResolver = beanFactory.getBeanExpressionResolver();
    }

}