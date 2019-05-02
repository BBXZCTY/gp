package com.tom.springframework.context.support;

import com.tom.springframework.beans.factory.BeanFactory;
import com.tom.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.tom.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.tom.springframework.beans.support.ResourceEditorRegistrar;
import com.tom.springframework.context.*;
import com.tom.springframework.context.expression.StandardBeanExpressionResolver;
import com.tom.springframework.core.env.ConfigurableEnvironment;
import com.tom.springframework.core.env.StandardEnvironment;
import com.tom.springframework.core.io.DefaultResourceLoader;
import com.tom.springframework.core.io.ResourceLoader;
import com.tom.springframework.util.Assert;
import com.tom.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    /** Synchronization monitor for the "refresh" and "destroy" */
    private final Object startupShutdownMonitor = new Object();

    /** Display name */
    private String displayName = ObjectUtils.identityToString(this);

    /** Unique id for this context, if any */
    private String id = ObjectUtils.identityToString(this);

    /** Environment used by this context */
    private ConfigurableEnvironment environment;

    /** BeanFactoryPostProcessors to apply on refresh */
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors =
            new ArrayList<>();

    private ApplicationContext parent;

    @Override
    public BeanFactory getParentBeanFactory() {
        return getParent();
    }

    @Override
    public ApplicationContext getParent() {
        return this.parent;
    }

    @Override
    public boolean containsLocalBean(String name) {
        return getBeanFactory().containsLocalBean(name);
    }


    @Override
    public void refresh() {
        synchronized (this.startupShutdownMonitor) {
            // Prepare this context for refreshing.
            prepareRefresh();

            // Tell the subclass to refresh the internal bean factory.
            ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

            // Prepare the bean factory for use in this context.
            prepareBeanFactory(beanFactory);

            try {
                // Allows post-processing of the bean factory in context subclasses.
                postProcessBeanFactory(beanFactory);

                // Invoke factory processors registered as beans in the context.
                invokeBeanFactoryPostProcessors(beanFactory);

                // Register bean processors that intercept bean creation.
                registerBeanPostProcessors(beanFactory);

                // Initialize message source for this context.
                initMessageSource();

                // Initialize event multicaster for this context.
                initApplicationEventMulticaster();

                // Initialize other special beans in specific context subclasses.
                onRefresh();

                // Check for listener beans and register them.
                registerListeners();

                // Instantiate all remaining (non-lazy-init) singletons.
                finishBeanFactoryInitialization(beanFactory);

                // Last step: publish corresponding event.
                finishRefresh();
            }

            catch (Exception ex) {

                // Destroy already created singletons to avoid dangling resources.
                destroyBeans();

                // Reset 'active' flag.
                cancelRefresh(ex);

                // Propagate exception to caller.
                throw ex;
            }

            finally {
                // Reset common introspection caches in Spring's core, since we
                // might not ever need metadata for singleton beans anymore...
                resetCommonCaches();
            }
        }
    }

    private void resetCommonCaches() {

    }

    private void cancelRefresh(Exception ex) {

    }

    private void destroyBeans() {

    }

    private void finishRefresh() {

    }

    private void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {

    }

    private void registerListeners() {

    }

    private void onRefresh() {

    }

    private void initApplicationEventMulticaster() {

    }

    private void initMessageSource() {

    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {

    }

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {

    }

    private void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

    }

    protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        // Tell the internal bean factory to use the context's class loader etc.
        beanFactory.setBeanClassLoader(getClassLoader());
        beanFactory.setBeanExpressionResolver(new StandardBeanExpressionResolver(beanFactory.getBeanClassLoader()));
        beanFactory.addPropertyEditorRegistrar(new ResourceEditorRegistrar(this, getEnvironment()));

        // Configure the bean factory with context callbacks.
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
        beanFactory.ignoreDependencyInterface(EnvironmentAware.class);
        beanFactory.ignoreDependencyInterface(EmbeddedValueResolverAware.class);
        beanFactory.ignoreDependencyInterface(ResourceLoaderAware.class);
        beanFactory.ignoreDependencyInterface(ApplicationEventPublisherAware.class);
        beanFactory.ignoreDependencyInterface(MessageSourceAware.class);
        beanFactory.ignoreDependencyInterface(ApplicationContextAware.class);

        // BeanFactory interface not registered as resolvable type in a plain factory.
        // MessageSource registered (and found for autowiring) as a bean.
//        beanFactory.registerResolvableDependency(BeanFactory.class, beanFactory);
        beanFactory.registerResolvableDependency(ResourceLoader.class, this);
        beanFactory.registerResolvableDependency(ApplicationEventPublisher.class, this);
        beanFactory.registerResolvableDependency(ApplicationContext.class, this);

        // Register early post-processor for detecting inner beans as ApplicationListeners.
        beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(this));

        // Detect a LoadTimeWeaver and prepare for weaving, if found.
//        if (beanFactory.containsBean(LOAD_TIME_WEAVER_BEAN_NAME)) {
//            beanFactory.addBeanPostProcessor(new LoadTimeWeaverAwareProcessor(beanFactory));
//            // Set a temporary ClassLoader for type matching.
//            beanFactory.setTempClassLoader(new ContextTypeMatchClassLoader(beanFactory.getBeanClassLoader()));
//        }

        // Register default environment beans.
        if (!beanFactory.containsLocalBean(ENVIRONMENT_BEAN_NAME)) {
            beanFactory.registerSingleton(ENVIRONMENT_BEAN_NAME, getEnvironment());
        }
        if (!beanFactory.containsLocalBean(SYSTEM_PROPERTIES_BEAN_NAME)) {
            beanFactory.registerSingleton(SYSTEM_PROPERTIES_BEAN_NAME, getEnvironment().getSystemProperties());
        }
        if (!beanFactory.containsLocalBean(SYSTEM_ENVIRONMENT_BEAN_NAME)) {
            beanFactory.registerSingleton(SYSTEM_ENVIRONMENT_BEAN_NAME, getEnvironment().getSystemEnvironment());
        }
    }

    @Override
    public ConfigurableEnvironment getEnvironment() {
        if (this.environment == null) {
            this.environment = createEnvironment();
        }
        return this.environment;
    }

    protected ConfigurableEnvironment createEnvironment() {
        return new StandardEnvironment();
    }

    protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        System.out.println("Bean factory for " + getDisplayName() + ": " + beanFactory);
        return beanFactory;
    }

    protected void prepareRefresh() {

    }

    @Override
    public abstract ConfigurableListableBeanFactory getBeanFactory();

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return getBeanFactory().containsBeanDefinition(beanName);
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    protected abstract void refreshBeanFactory();

    @Override
    public String getId() {
        return this.id;
    }

//    @Override
//    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
//        Assert.notNull(postProcessor, "BeanFactoryPostProcessor must not be null");
//        this.beanFactoryPostProcessors.add(postProcessor);
//    }

    @Override
    public boolean containsBean(String name) {
        return getBeanFactory().containsBean(name);
    }

}