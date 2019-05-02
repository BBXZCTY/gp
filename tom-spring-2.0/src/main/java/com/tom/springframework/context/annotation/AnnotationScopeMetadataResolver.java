package com.tom.springframework.context.annotation;

import com.tom.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import com.tom.springframework.beans.factory.config.BeanDefinition;
import com.tom.springframework.core.annotation.AnnotationAttributes;
import com.tom.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.Annotation;

public class AnnotationScopeMetadataResolver implements ScopeMetadataResolver {

    private final ScopedProxyMode defaultProxyMode;

    protected Class<? extends Annotation> scopeAnnotationType = Scope.class;

    public AnnotationScopeMetadataResolver() {
        this.defaultProxyMode = ScopedProxyMode.NO;
    }

    @Override
    public ScopeMetadata resolveScopeMetadata(BeanDefinition definition) {
        ScopeMetadata metadata = new ScopeMetadata();
        if (definition instanceof AnnotatedBeanDefinition) {
            AnnotatedBeanDefinition annDef = (AnnotatedBeanDefinition) definition;
            AnnotationAttributes attributes = AnnotationConfigUtils.attributesFor(
                    annDef.getMetadata(), this.scopeAnnotationType);
            if (attributes != null) {
                metadata.setScopeName(attributes.getString("value"));
                ScopedProxyMode proxyMode = attributes.getEnum("proxyMode");
                if (proxyMode == null || proxyMode == ScopedProxyMode.DEFAULT) {
                    proxyMode = this.defaultProxyMode;
                }
                metadata.setScopedProxyMode(proxyMode);
            }
        }
        return metadata;
    }

    static AnnotationAttributes attributesFor(AnnotatedTypeMetadata metadata, String annotationClassName) {
        return AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annotationClassName, false));
    }

}