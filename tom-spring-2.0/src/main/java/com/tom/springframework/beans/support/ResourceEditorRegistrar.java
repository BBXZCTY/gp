package com.tom.springframework.beans.support;

import com.tom.springframework.beans.PropertyEditorRegistrar;
import com.tom.springframework.core.env.PropertyResolver;
import com.tom.springframework.core.io.ResourceLoader;

public class ResourceEditorRegistrar implements PropertyEditorRegistrar {

    private final PropertyResolver propertyResolver;

    private final ResourceLoader resourceLoader;

    public ResourceEditorRegistrar(ResourceLoader resourceLoader, PropertyResolver propertyResolver) {
        this.resourceLoader = resourceLoader;
        this.propertyResolver = propertyResolver;
    }

}