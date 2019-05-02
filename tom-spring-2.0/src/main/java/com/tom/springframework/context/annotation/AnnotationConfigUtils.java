package com.tom.springframework.context.annotation;

import com.tom.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import com.tom.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.tom.springframework.beans.factory.annotation.RequiredAnnotationBeanPostProcessor;
import com.tom.springframework.beans.factory.config.BeanDefinition;
import com.tom.springframework.beans.factory.config.BeanDefinitionHolder;
import com.tom.springframework.beans.factory.support.AbstractBeanDefinition;
import com.tom.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.tom.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.tom.springframework.beans.factory.support.RootBeanDefinition;
import com.tom.springframework.context.event.DefaultEventListenerFactory;
import com.tom.springframework.context.event.EventListenerMethodProcessor;
import com.tom.springframework.context.support.GenericApplicationContext;
import com.tom.springframework.core.annotation.AnnotationAttributes;
import com.tom.springframework.core.type.AnnotatedTypeMetadata;

import java.util.LinkedHashSet;
import java.util.Set;

public class AnnotationConfigUtils {

    public static final String CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME =
            "com.tom.springframework.context.annotation.internalConfigurationAnnotationProcessor";

    public static final String CONFIGURATION_BEAN_NAME_GENERATOR =
            "com.tom.springframework.context.annotation.internalConfigurationBeanNameGenerator";

    public static final String AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME =
            "com.tom.springframework.context.annotation.internalAutowiredAnnotationProcessor";

    public static final String REQUIRED_ANNOTATION_PROCESSOR_BEAN_NAME =
            "com.tom.springframework.context.annotation.internalRequiredAnnotationProcessor";

    public static final String COMMON_ANNOTATION_PROCESSOR_BEAN_NAME =
            "com.tom.springframework.context.annotation.internalCommonAnnotationProcessor";

    public static final String PERSISTENCE_ANNOTATION_PROCESSOR_BEAN_NAME =
            "com.tom.springframework.context.annotation.internalPersistenceAnnotationProcessor";


    private static final String PERSISTENCE_ANNOTATION_PROCESSOR_CLASS_NAME =
            "com.tom.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor";

    public static final String EVENT_LISTENER_PROCESSOR_BEAN_NAME =
            "com.tom.springframework.context.event.internalEventListenerProcessor";

    public static final String EVENT_LISTENER_FACTORY_BEAN_NAME =
            "com.tom.springframework.context.event.internalEventListenerFactory";


    public static void registerAnnotationConfigProcessors(BeanDefinitionRegistry registry) {
        registerAnnotationConfigProcessors(registry, null);
    }

    public static Set<BeanDefinitionHolder> registerAnnotationConfigProcessors(
            BeanDefinitionRegistry registry, Object source) {

        DefaultListableBeanFactory beanFactory = unwrapDefaultListableBeanFactory(registry);
//        if (beanFactory != null) {
//            if (!(beanFactory.getDependencyComparator() instanceof AnnotationAwareOrderComparator)) {
//                beanFactory.setDependencyComparator(AnnotationAwareOrderComparator.INSTANCE);
//            }
//            if (!(beanFactory.getAutowireCandidateResolver() instanceof ContextAnnotationAutowireCandidateResolver)) {
//                beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
//            }
//        }

        Set<BeanDefinitionHolder> beanDefs = new LinkedHashSet<>(4);

        if (!registry.containsBeanDefinition(CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME)) {
            RootBeanDefinition def = new RootBeanDefinition(ConfigurationClassPostProcessor.class);
            def.setSource(source);
            beanDefs.add(registerPostProcessor(registry, def, CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME));
        }

        if (!registry.containsBeanDefinition(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)) {
            RootBeanDefinition def = new RootBeanDefinition(AutowiredAnnotationBeanPostProcessor.class);
            def.setSource(source);
            beanDefs.add(registerPostProcessor(registry, def, AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
        }

        if (!registry.containsBeanDefinition(REQUIRED_ANNOTATION_PROCESSOR_BEAN_NAME)) {
            RootBeanDefinition def = new RootBeanDefinition(RequiredAnnotationBeanPostProcessor.class);
            def.setSource(source);
            beanDefs.add(registerPostProcessor(registry, def, REQUIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
        }

        if (!registry.containsBeanDefinition(EVENT_LISTENER_PROCESSOR_BEAN_NAME)) {
            RootBeanDefinition def = new RootBeanDefinition(EventListenerMethodProcessor.class);
            def.setSource(source);
            beanDefs.add(registerPostProcessor(registry, def, EVENT_LISTENER_PROCESSOR_BEAN_NAME));
        }
        if (!registry.containsBeanDefinition(EVENT_LISTENER_FACTORY_BEAN_NAME)) {
            RootBeanDefinition def = new RootBeanDefinition(DefaultEventListenerFactory.class);
            def.setSource(source);
            beanDefs.add(registerPostProcessor(registry, def, EVENT_LISTENER_FACTORY_BEAN_NAME));
        }

        return beanDefs;
    }

    private static DefaultListableBeanFactory unwrapDefaultListableBeanFactory(BeanDefinitionRegistry registry) {
        if (registry instanceof DefaultListableBeanFactory) {
            return (DefaultListableBeanFactory) registry;
        }
        else if (registry instanceof GenericApplicationContext) {
            return ((GenericApplicationContext) registry).getDefaultListableBeanFactory();
        }
        else {
            return null;
        }
    }

    private static BeanDefinitionHolder registerPostProcessor(
            BeanDefinitionRegistry registry, RootBeanDefinition definition, String beanName) {

        definition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        registry.registerBeanDefinition(beanName, definition);
        return new BeanDefinitionHolder(definition, beanName);
    }

    static AnnotationAttributes attributesFor(AnnotatedTypeMetadata metadata, Class<?> annotationClass) {
        return attributesFor(metadata, annotationClass.getName());
    }

    static AnnotationAttributes attributesFor(AnnotatedTypeMetadata metadata, String annotationClassName) {
        return AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annotationClassName, false));
    }

    public static void processCommonDefinitionAnnotations(AnnotatedBeanDefinition abd) {
        processCommonDefinitionAnnotations(abd, abd.getMetadata());
    }

    static void processCommonDefinitionAnnotations(AnnotatedBeanDefinition abd, AnnotatedTypeMetadata metadata) {
//        if (metadata.isAnnotated(Lazy.class.getName())) {
//            abd.setLazyInit(attributesFor(metadata, Lazy.class).getBoolean("value"));
//        }
//        else if (abd.getMetadata() != metadata && abd.getMetadata().isAnnotated(Lazy.class.getName())) {
//            abd.setLazyInit(attributesFor(abd.getMetadata(), Lazy.class).getBoolean("value"));
//        }
//
//        if (metadata.isAnnotated(Primary.class.getName())) {
//            abd.setPrimary(true);
//        }
//        if (metadata.isAnnotated(DependsOn.class.getName())) {
//            abd.setDependsOn(attributesFor(metadata, DependsOn.class).getStringArray("value"));
//        }
//
//        if (abd instanceof AbstractBeanDefinition) {
//            AbstractBeanDefinition absBd = (AbstractBeanDefinition) abd;
//            if (metadata.isAnnotated(Role.class.getName())) {
//                absBd.setRole(attributesFor(metadata, Role.class).getNumber("value").intValue());
//            }
//            if (metadata.isAnnotated(Description.class.getName())) {
//                absBd.setDescription(attributesFor(metadata, Description.class).getString("value"));
//            }
//        }
    }

    static BeanDefinitionHolder applyScopedProxyMode(
            ScopeMetadata metadata, BeanDefinitionHolder definition, BeanDefinitionRegistry registry) {

        ScopedProxyMode scopedProxyMode = metadata.getScopedProxyMode();
        if (scopedProxyMode.equals(ScopedProxyMode.NO)) {
            return definition;
        }
        boolean proxyTargetClass = scopedProxyMode.equals(ScopedProxyMode.TARGET_CLASS);
        return ScopedProxyCreator.createScopedProxy(definition, registry, proxyTargetClass);
    }



}