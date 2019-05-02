package com.tom.springframework.context;

import com.tom.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.tom.springframework.beans.factory.config.BeanPostProcessor;
import com.tom.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.tom.springframework.core.env.ConfigurableEnvironment;

public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * Any number of these characters are considered delimiters between
     * multiple context config paths in a single String value.
     */
    String CONFIG_LOCATION_DELIMITERS = ",; \t\n";

    /**
     * Name of the ConversionService bean in the factory.
     * If none is supplied, default conversion rules apply.
     * @since 3.0
     */
    String CONVERSION_SERVICE_BEAN_NAME = "conversionService";

    /**
     * Name of the LoadTimeWeaver bean in the factory. If such a bean is supplied,
     * the context will use a temporary ClassLoader for type matching, in order
     * to allow the LoadTimeWeaver to process all actual bean classes.
     * @since 2.5
     */
    String LOAD_TIME_WEAVER_BEAN_NAME = "loadTimeWeaver";

    /**
     * @since 3.1
     */
    String ENVIRONMENT_BEAN_NAME = "environment";

    /**
     * Name of the System properties bean in the factory.
     * @see java.lang.System#getProperties()
     */
    String SYSTEM_PROPERTIES_BEAN_NAME = "systemProperties";

    /**
     * Name of the System environment bean in the factory.
     * @see java.lang.System#getenv()
     */
    String SYSTEM_ENVIRONMENT_BEAN_NAME = "systemEnvironment";


    void refresh() throws Exception;

    ConfigurableListableBeanFactory getBeanFactory();

    @Override
    ConfigurableEnvironment getEnvironment();

//    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

//    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);



}
