package com.tom.springframework.core.type;

import com.tom.springframework.util.Assert;

public class StandardClassMetadata implements ClassMetadata {

    private final Class<?> introspectedClass;

    public StandardClassMetadata(Class<?> introspectedClass) {
        Assert.notNull(introspectedClass, "Class must not be null");
        this.introspectedClass = introspectedClass;
    }

    public final Class<?> getIntrospectedClass() {
        return this.introspectedClass;
    }

}