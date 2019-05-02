package com.tom.springframework.beans.factory.support;

import com.tom.springframework.beans.factory.config.BeanDefinition;
import com.tom.springframework.beans.factory.config.BeanDefinitionHolder;
import com.tom.springframework.core.ResolvableType;

public class RootBeanDefinition extends AbstractBeanDefinition {

    volatile ResolvableType targetType;

    private BeanDefinitionHolder decoratedDefinition;

    boolean allowCaching = true;

    boolean isFactoryMethodUnique = false;


    /** Package-visible field for caching the determined Class of a given bean definition */
    volatile Class<?> resolvedTargetType;

    public RootBeanDefinition(Class<?> beanClass) {
        super();
        setBeanClass(beanClass);
    }

    @Override
    public String getParentName() {
        return null;
    }

    public Class<?> getTargetType() {
        if (this.resolvedTargetType != null) {
            return this.resolvedTargetType;
        }
        return (this.targetType != null ? this.targetType.resolve() : null);
    }

    @Override
    public RootBeanDefinition cloneBeanDefinition() {
        return new RootBeanDefinition(this);
    }

    public RootBeanDefinition(RootBeanDefinition original) {
        super(original);
        this.decoratedDefinition = original.decoratedDefinition;
//        this.qualifiedElement = original.qualifiedElement;
        this.allowCaching = original.allowCaching;
        this.isFactoryMethodUnique = original.isFactoryMethodUnique;
        this.targetType = original.targetType;
    }

    RootBeanDefinition(BeanDefinition original) {
        super(original);
    }

}