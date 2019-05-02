package com.tom.springframework.beans.factory.config;

public interface BeanDefinition {

    int ROLE_APPLICATION = 0;

    int ROLE_SUPPORT = 1;

    int ROLE_INFRASTRUCTURE = 2;

    String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;

    String getResourceDescription();

    int getRole();

    String getParentName();

    void setScope(String scope);

    String getBeanClassName();

    String getFactoryMethodName();

    String getScope();

    boolean isSingleton();


}