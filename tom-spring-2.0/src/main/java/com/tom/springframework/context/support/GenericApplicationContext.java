package com.tom.springframework.context.support;


import com.tom.springframework.beans.factory.config.BeanDefinition;
import com.tom.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.tom.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.tom.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.tom.springframework.core.io.ResourceLoader;
import com.tom.springframework.util.Assert;

import java.util.concurrent.atomic.AtomicBoolean;

public class GenericApplicationContext extends AbstractApplicationContext implements BeanDefinitionRegistry {

    private final DefaultListableBeanFactory beanFactory;

    private ResourceLoader resourceLoader;

    private boolean customClassLoader = false;

    private final AtomicBoolean refreshed = new AtomicBoolean();

    public GenericApplicationContext(DefaultListableBeanFactory beanFactory) {
        Assert.notNull(beanFactory, "BeanFactory must not be null");
        this.beanFactory = beanFactory;
    }

    public GenericApplicationContext() {
        this.beanFactory = new DefaultListableBeanFactory();
    }

    @Override
    public Object getBean(String name)  {
        return null;
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    public final DefaultListableBeanFactory getDefaultListableBeanFactory() {
        return this.beanFactory;
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {

        this.beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    @Override
    public void registerAlias(String beanName, String alias) {
        this.beanFactory.registerAlias(beanName, alias);
    }

    @Override
    protected final void refreshBeanFactory() throws IllegalStateException {
        if (!this.refreshed.compareAndSet(false, true)) {
            throw new IllegalStateException(
                    "GenericApplicationContext does not support multiple refresh attempts: just call 'refresh' once");
        }
        this.beanFactory.setSerializationId(getId());
    }

    @Override
    public ClassLoader getClassLoader() {
        if (this.resourceLoader != null && !this.customClassLoader) {
            return this.resourceLoader.getClassLoader();
        }
        return super.getClassLoader();
    }
}