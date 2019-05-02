package com.tom.springframework.beans.factory.config;

public interface BeanExpressionResolver {

    Object evaluate(String value, BeanExpressionContext evalContext);


}
