package com.tom.springframework.context.annotation;

import com.tom.springframework.beans.factory.config.BeanDefinition;

public interface ScopeMetadataResolver {

    ScopeMetadata resolveScopeMetadata(BeanDefinition definition);
}
