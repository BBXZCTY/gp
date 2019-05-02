package com.tom.springframework.beans.factory.config;

public class BeanDefinitionHolder {

    private final BeanDefinition beanDefinition;

    private final String beanName;

    private final String[] aliases;

    public BeanDefinitionHolder(BeanDefinition beanDefinition, String beanName) {
        this(beanDefinition, beanName, null);
    }

    public BeanDefinitionHolder(BeanDefinition beanDefinition, String beanName, String[] aliases) {
        this.beanDefinition = beanDefinition;
        this.beanName = beanName;
        this.aliases = aliases;
    }

    public String getBeanName() {
        return this.beanName;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public BeanDefinition getBeanDefinition() {
        return this.beanDefinition;
    }


}