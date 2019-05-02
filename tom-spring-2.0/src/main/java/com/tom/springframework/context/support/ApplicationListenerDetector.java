package com.tom.springframework.context.support;

import com.tom.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;

class ApplicationListenerDetector implements DestructionAwareBeanPostProcessor {

    private transient final AbstractApplicationContext applicationContext;

    public ApplicationListenerDetector(AbstractApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

}