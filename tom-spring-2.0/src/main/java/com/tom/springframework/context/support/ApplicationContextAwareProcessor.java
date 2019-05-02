package com.tom.springframework.context.support;


import com.tom.springframework.beans.factory.config.BeanPostProcessor;
import com.tom.springframework.beans.factory.config.EmbeddedValueResolver;
import com.tom.springframework.context.ConfigurableApplicationContext;
import com.tom.springframework.util.StringValueResolver;

class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ConfigurableApplicationContext applicationContext;

    private final StringValueResolver embeddedValueResolver;


    /**
     * Create a new ApplicationContextAwareProcessor for the given context.
     */
    public ApplicationContextAwareProcessor(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.embeddedValueResolver = new EmbeddedValueResolver(applicationContext.getBeanFactory());
    }

}