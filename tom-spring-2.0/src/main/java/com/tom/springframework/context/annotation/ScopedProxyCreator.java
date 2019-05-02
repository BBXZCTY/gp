package com.tom.springframework.context.annotation;


import com.tom.springframework.aop.scope.ScopedProxyUtils;
import com.tom.springframework.beans.factory.config.BeanDefinitionHolder;
import com.tom.springframework.beans.factory.support.BeanDefinitionRegistry;

class ScopedProxyCreator {

    public static BeanDefinitionHolder createScopedProxy(
            BeanDefinitionHolder definitionHolder, BeanDefinitionRegistry registry, boolean proxyTargetClass) {

        return ScopedProxyUtils.createScopedProxy(definitionHolder, registry, proxyTargetClass);
    }

}