package com.tom.springframework.beans.factory.support;

import com.tom.springframework.beans.factory.FactoryBean;
import com.tom.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.tom.springframework.beans.factory.config.BeanDefinition;
import com.tom.springframework.beans.factory.config.BeanPostProcessor;

import java.util.HashSet;
import java.util.Set;

public class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    /**
     * Dependency interfaces to ignore on dependency check and autowire, as Set of
     * Class objects. By default, only the BeanFactory interface is ignored.
     */
    private final Set<Class<?>> ignoredDependencyInterfaces = new HashSet<Class<?>>();

    @Override
    protected boolean containsBeanDefinition(String beanName) {
        return false;
    }

    public void ignoreDependencyInterface(Class<?> ifc) {
        this.ignoredDependencyInterfaces.add(ifc);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return null;
    }

    @Override
    protected Class<?> predictBeanType(String beanName, RootBeanDefinition mbd, Class<?>... typesToMatch) {
//        Class<?> targetType = determineTargetType(beanName, mbd, typesToMatch);

        // Apply SmartInstantiationAwareBeanPostProcessors to predict the
        // eventual type after a before-instantiation shortcut.
//        if (targetType != null && !mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {
//            for (BeanPostProcessor bp : getBeanPostProcessors()) {
//                if (bp instanceof SmartInstantiationAwareBeanPostProcessor) {
//                    SmartInstantiationAwareBeanPostProcessor ibp = (SmartInstantiationAwareBeanPostProcessor) bp;
//                    Class<?> predicted = ibp.predictBeanType(targetType, beanName);
//                    if (predicted != null && (typesToMatch.length != 1 || FactoryBean.class != typesToMatch[0] ||
//                            FactoryBean.class.isAssignableFrom(predicted))) {
//                        return predicted;
//                    }
//                }
//            }
//        }
//        return targetType;
        return null;
    }
}