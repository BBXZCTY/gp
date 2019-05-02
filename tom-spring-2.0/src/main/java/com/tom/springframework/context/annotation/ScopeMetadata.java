package com.tom.springframework.context.annotation;

import com.tom.springframework.beans.factory.config.BeanDefinition;
import com.tom.springframework.util.Assert;

public class ScopeMetadata {

    private String scopeName = BeanDefinition.SCOPE_SINGLETON;

    private ScopedProxyMode scopedProxyMode = ScopedProxyMode.NO;

    public String getScopeName() {
        return this.scopeName;
    }

    public void setScopeName(String scopeName) {
        Assert.notNull(scopeName, "'scopeName' must not be null");
        this.scopeName = scopeName;
    }

    public void setScopedProxyMode(ScopedProxyMode scopedProxyMode) {
        Assert.notNull(scopedProxyMode, "'scopedProxyMode' must not be null");
        this.scopedProxyMode = scopedProxyMode;
    }

    public ScopedProxyMode getScopedProxyMode() {
        return this.scopedProxyMode;
    }
}