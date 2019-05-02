package com.tom.springframework.beans.factory.config;

import com.tom.springframework.beans.factory.ListableBeanFactory;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, ConfigurableBeanFactory {

    void ignoreDependencyInterface(Class<?> ifc);

    void registerResolvableDependency(Class<?> dependencyType, Object autowiredValue);

}
