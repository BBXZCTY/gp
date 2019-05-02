package com.tom.springframework.context.annotation;

import com.tom.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.tom.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.tom.springframework.context.ConfigurableApplicationContext;
import com.tom.springframework.core.env.Environment;
import com.tom.springframework.core.env.EnvironmentCapable;
import com.tom.springframework.core.io.ResourceLoader;
import com.tom.springframework.core.type.AnnotatedTypeMetadata;

import java.util.ArrayList;
import java.util.List;

public class ConditionEvaluator {

    private final ConditionContextImpl context;


    public ConditionEvaluator(BeanDefinitionRegistry registry, Environment environment, ResourceLoader resourceLoader) {
        this.context = new ConditionContextImpl(registry, environment, resourceLoader);
    }

    public boolean shouldSkip(AnnotatedTypeMetadata metadata) {
        return false;
//        return shouldSkip(metadata, null);
    }

//
//    public boolean shouldSkip(AnnotatedTypeMetadata metadata, ConfigurationPhase phase) {
//        if (metadata == null || !metadata.isAnnotated(Conditional.class.getName())) {
//            return false;
//        }
//
//        if (phase == null) {
//            if (metadata instanceof AnnotationMetadata &&
//                    ConfigurationClassUtils.isConfigurationCandidate((AnnotationMetadata) metadata)) {
//                return shouldSkip(metadata, ConfigurationPhase.PARSE_CONFIGURATION);
//            }
//            return shouldSkip(metadata, ConfigurationPhase.REGISTER_BEAN);
//        }
//
//        List<Condition> conditions = new ArrayList<Condition>();
//        for (String[] conditionClasses : getConditionClasses(metadata)) {
//            for (String conditionClass : conditionClasses) {
//                Condition condition = getCondition(conditionClass, this.context.getClassLoader());
//                conditions.add(condition);
//            }
//        }
//
//        AnnotationAwareOrderComparator.sort(conditions);
//
//        for (Condition condition : conditions) {
//            ConfigurationPhase requiredPhase = null;
//            if (condition instanceof ConfigurationCondition) {
//                requiredPhase = ((ConfigurationCondition) condition).getConfigurationPhase();
//            }
//            if (requiredPhase == null || requiredPhase == phase) {
//                if (!condition.matches(this.context, metadata)) {
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }

    private static class ConditionContextImpl implements ConditionContext {

        private final BeanDefinitionRegistry registry;

        private final ConfigurableListableBeanFactory beanFactory;

        private final Environment environment;

        private final ResourceLoader resourceLoader;

        public ConditionContextImpl(BeanDefinitionRegistry registry, Environment environment, ResourceLoader resourceLoader)  {
            this.registry = registry;
            this.beanFactory = deduceBeanFactory(registry);
            this.environment = (environment != null ? environment : deduceEnvironment(registry));
            this.resourceLoader = (resourceLoader != null ? resourceLoader : deduceResourceLoader(registry));
        }

        private ConfigurableListableBeanFactory deduceBeanFactory(BeanDefinitionRegistry source) {
            if (source instanceof ConfigurableListableBeanFactory) {
                return (ConfigurableListableBeanFactory) source;
            }
            if (source instanceof ConfigurableApplicationContext) {
                return (((ConfigurableApplicationContext) source).getBeanFactory());
            }
            return null;
        }

        private Environment deduceEnvironment(BeanDefinitionRegistry source) {
            if (source instanceof EnvironmentCapable) {
                return ((EnvironmentCapable) source).getEnvironment();
            }
            return null;
        }

        private ResourceLoader deduceResourceLoader(BeanDefinitionRegistry source) {
            if (source instanceof ResourceLoader) {
                return (ResourceLoader) source;
            }
            return null;
        }
    }
}