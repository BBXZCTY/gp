package com.tom.springframework.beans.factory;

public interface BeanFactory {

    String FACTORY_BEAN_PREFIX = "&";

    Object getBean(String name) throws Exception;

    boolean containsBean(String name);

}
