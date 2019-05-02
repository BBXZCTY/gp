package com.tom.springframework.beans.factory.annotation;

import com.tom.springframework.beans.factory.support.GenericBeanDefinition;
import com.tom.springframework.core.type.AnnotationMetadata;
import com.tom.springframework.core.type.StandardAnnotationMetadata;

public class AnnotatedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition {

    private final AnnotationMetadata metadata;

    public AnnotatedGenericBeanDefinition(Class<?> beanClass) {
        setBeanClass(beanClass);
        this.metadata = new StandardAnnotationMetadata(beanClass, true);
    }

    @Override
    public final AnnotationMetadata getMetadata() {
        return this.metadata;
    }
}