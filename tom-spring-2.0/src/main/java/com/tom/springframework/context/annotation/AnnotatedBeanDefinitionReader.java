package com.tom.springframework.context.annotation;

import com.tom.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import com.tom.springframework.beans.factory.config.BeanDefinitionHolder;
import com.tom.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import com.tom.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.tom.springframework.beans.factory.support.BeanNameGenerator;
import com.tom.springframework.core.env.Environment;
import com.tom.springframework.core.env.EnvironmentCapable;
import com.tom.springframework.core.env.StandardEnvironment;

import java.lang.annotation.Annotation;

public class AnnotatedBeanDefinitionReader {

    private final BeanDefinitionRegistry registry;

    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();

    private ConditionEvaluator conditionEvaluator;

    public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, getOrCreateEnvironment(registry));
    }

    public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry, Environment environment) {
        this.registry = registry;
        this.conditionEvaluator = new ConditionEvaluator(registry, environment, null);
        AnnotationConfigUtils.registerAnnotationConfigProcessors(this.registry);
    }

    private static Environment getOrCreateEnvironment(BeanDefinitionRegistry registry) {
        if (registry instanceof EnvironmentCapable) {
            return ((EnvironmentCapable) registry).getEnvironment();
        }
        return new StandardEnvironment();
    }

    public void register(Class<?>... annotatedClasses) {
        for (Class<?> annotatedClass : annotatedClasses) {
            registerBean(annotatedClass);
        }
    }

    @SuppressWarnings("unchecked")
    public void registerBean(Class<?> annotatedClass) {
        registerBean(annotatedClass, null, (Class<? extends Annotation>[]) null);
    }

    @SuppressWarnings("unchecked")
    public void registerBean(Class<?> annotatedClass, String name, Class<? extends Annotation>... qualifiers) {
        AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(annotatedClass);
        if (this.conditionEvaluator.shouldSkip(abd.getMetadata())) {
            return;
        }

        ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(abd);
        abd.setScope(scopeMetadata.getScopeName());
        String beanName = (name != null ? name : this.beanNameGenerator.generateBeanName(abd, this.registry));
        AnnotationConfigUtils.processCommonDefinitionAnnotations(abd);
//        if (qualifiers != null) {
//            for (Class<? extends Annotation> qualifier : qualifiers) {
//                if (Primary.class == qualifier) {
//                    abd.setPrimary(true);
//                }
//                else if (Lazy.class == qualifier) {
//                    abd.setLazyInit(true);
//                }
//                else {
//                    abd.addQualifier(new AutowireCandidateQualifier(qualifier));
//                }
//            }
//        }

        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(abd, beanName);
        definitionHolder = AnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder, this.registry);
        BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, this.registry);
    }

}