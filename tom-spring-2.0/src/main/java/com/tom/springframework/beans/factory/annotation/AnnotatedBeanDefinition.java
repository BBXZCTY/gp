package com.tom.springframework.beans.factory.annotation;

import com.tom.springframework.beans.factory.config.BeanDefinition;
import com.tom.springframework.core.type.AnnotationMetadata;

public interface AnnotatedBeanDefinition extends BeanDefinition {

    AnnotationMetadata getMetadata();

}
