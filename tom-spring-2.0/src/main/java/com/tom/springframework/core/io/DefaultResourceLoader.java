package com.tom.springframework.core.io;

import com.tom.springframework.util.ClassUtils;

public class DefaultResourceLoader implements ResourceLoader  {

    private ClassLoader classLoader;

    @Override
    public ClassLoader getClassLoader() {
        return (this.classLoader != null ? this.classLoader : ClassUtils.getDefaultClassLoader());
    }
}