package com.tom.springframework.beans.factory.support;

import com.tom.springframework.beans.factory.config.BeanDefinition;

public class GenericBeanDefinition extends AbstractBeanDefinition {

    private String parentName;

    @Override
    public String getParentName() {
        return this.parentName;
    }

    @Override
    public AbstractBeanDefinition cloneBeanDefinition() {
        return new GenericBeanDefinition(this);
    }

    public GenericBeanDefinition(BeanDefinition original) {
        super(original);
    }

    public GenericBeanDefinition() {
        super();
    }

}