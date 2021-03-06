package com.tom.springframework.aop.scope;

import com.tom.springframework.beans.factory.config.BeanDefinition;
import com.tom.springframework.beans.factory.config.BeanDefinitionHolder;
import com.tom.springframework.beans.factory.support.AbstractBeanDefinition;
import com.tom.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.tom.springframework.beans.factory.support.RootBeanDefinition;

public class ScopedProxyUtils {

    private static final String TARGET_NAME_PREFIX = "scopedTarget.";


    /**
     * Generate a scoped proxy for the supplied target bean, registering the target
     * bean with an internal name and setting 'targetBeanName' on the scoped proxy.
     * @param definition the original bean definition
     * @param registry the bean definition registry
     * @param proxyTargetClass whether to create a target class proxy
     * @return the scoped proxy definition
     */
    public static BeanDefinitionHolder createScopedProxy(BeanDefinitionHolder definition,
                                                         BeanDefinitionRegistry registry, boolean proxyTargetClass) {

        String originalBeanName = definition.getBeanName();
        BeanDefinition targetDefinition = definition.getBeanDefinition();
        String targetBeanName = getTargetBeanName(originalBeanName);

        // Create a scoped proxy definition for the original bean name,
        // "hiding" the target bean in an internal target definition.
//        RootBeanDefinition proxyDefinition = new RootBeanDefinition(ScopedProxyFactoryBean.class);
//        proxyDefinition.setDecoratedDefinition(new BeanDefinitionHolder(targetDefinition, targetBeanName));
//        proxyDefinition.setOriginatingBeanDefinition(targetDefinition);
//        proxyDefinition.setSource(definition.getSource());
//        proxyDefinition.setRole(targetDefinition.getRole());
//
//        proxyDefinition.getPropertyValues().add("targetBeanName", targetBeanName);
//        if (proxyTargetClass) {
//            targetDefinition.setAttribute(AutoProxyUtils.PRESERVE_TARGET_CLASS_ATTRIBUTE, Boolean.TRUE);
//            // ScopedProxyFactoryBean's "proxyTargetClass" default is TRUE, so we don't need to set it explicitly here.
//        }
//        else {
//            proxyDefinition.getPropertyValues().add("proxyTargetClass", Boolean.FALSE);
//        }
//
//        // Copy autowire settings from original bean definition.
//        proxyDefinition.setAutowireCandidate(targetDefinition.isAutowireCandidate());
//        proxyDefinition.setPrimary(targetDefinition.isPrimary());
//        if (targetDefinition instanceof AbstractBeanDefinition) {
//            proxyDefinition.copyQualifiersFrom((AbstractBeanDefinition) targetDefinition);
//        }
//
//        // The target bean should be ignored in favor of the scoped proxy.
//        targetDefinition.setAutowireCandidate(false);
//        targetDefinition.setPrimary(false);
//
//        // Register the target bean as separate bean in the factory.
//        registry.registerBeanDefinition(targetBeanName, targetDefinition);

        // Return the scoped proxy definition as primary bean definition
        // (potentially an inner bean).
        return new BeanDefinitionHolder(null, originalBeanName, definition.getAliases());
    }

    /**
     * Generate the bean name that is used within the scoped proxy to reference the target bean.
     * @param originalBeanName the original name of bean
     * @return the generated bean to be used to reference the target bean
     */
    public static String getTargetBeanName(String originalBeanName) {
        return TARGET_NAME_PREFIX + originalBeanName;
    }

    /**
     * Specify if the {@code beanName} is the name of a bean that references the target
     * bean within a scoped proxy.
     * @since 4.1.4
     */
    public static boolean isScopedTarget(String beanName) {
        return (beanName != null && beanName.startsWith(TARGET_NAME_PREFIX));
    }

}