package com.tom.springframework.beans.factory.config;

import com.tom.springframework.beans.PropertyEditorRegistrar;
import com.tom.springframework.beans.factory.HierarchicalBeanFactory;

import java.security.AccessControlContext;

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    void setBeanClassLoader(ClassLoader beanClassLoader);

    void setBeanExpressionResolver(BeanExpressionResolver resolver);

    ClassLoader getBeanClassLoader();

    void addPropertyEditorRegistrar(PropertyEditorRegistrar registrar);

    BeanExpressionResolver getBeanExpressionResolver();

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    boolean isFactoryBean(String name);

    BeanDefinition getBeanDefinition(String beanName);

    ClassLoader getTempClassLoader();

    Scope getRegisteredScope(String scopeName);

    AccessControlContext getAccessControlContext();

    BeanDefinition getMergedBeanDefinition(String beanName);

    boolean isCacheBeanMetadata();



}