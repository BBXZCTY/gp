package com.tom.springframework.context;

import com.tom.springframework.beans.factory.HierarchicalBeanFactory;
import com.tom.springframework.beans.factory.ListableBeanFactory;
import com.tom.springframework.core.env.EnvironmentCapable;

public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, HierarchicalBeanFactory {

    String getDisplayName();

    String getId();

    ApplicationContext getParent();


}
