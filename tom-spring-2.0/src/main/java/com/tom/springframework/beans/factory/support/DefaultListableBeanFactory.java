package com.tom.springframework.beans.factory.support;

import com.tom.springframework.beans.factory.ObjectFactory;
import com.tom.springframework.beans.factory.config.BeanDefinition;
import com.tom.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.tom.springframework.util.Assert;

import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
        implements ConfigurableListableBeanFactory, BeanDefinitionRegistry, Serializable {

    /** Map of bean definition objects, keyed by bean name */
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    /** Map of singleton and non-singleton bean names, keyed by dependency type */
    private final Map<Class<?>, String[]> allBeanNamesByType = new ConcurrentHashMap<>(64);

    /** Map of singleton-only bean names, keyed by dependency type */
    private final Map<Class<?>, String[]> singletonBeanNamesByType = new ConcurrentHashMap<>(64);

    /** List of bean definition names, in registration order */
    private volatile List<String> beanDefinitionNames = new ArrayList<>(256);

    /** List of names of manually registered singletons, in registration order */
    private volatile Set<String> manualSingletonNames = new LinkedHashSet<>(16);

    /** Cached array of bean definition names in case of frozen configuration */
    private volatile String[] frozenBeanDefinitionNames;

    /** Map from dependency type to corresponding autowired value */
    private final Map<Class<?>, Object> resolvableDependencies = new ConcurrentHashMap<>(16);

    /** Map from serialized id to factory instance */
    private static final Map<String, Reference<DefaultListableBeanFactory>> serializableFactories =
            new ConcurrentHashMap<>(8);


    private boolean allowBeanDefinitionOverriding = true;

    private String serializationId;

    public boolean isAllowBeanDefinitionOverriding() {
        return this.allowBeanDefinitionOverriding;
    }



    @Override
    public boolean containsBeanDefinition(String beanName) {
        return this.beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {

        BeanDefinition oldBeanDefinition;

        oldBeanDefinition = this.beanDefinitionMap.get(beanName);
        if (oldBeanDefinition != null) {
            if (!isAllowBeanDefinitionOverriding()) {
                System.out.println(beanDefinition.getResourceDescription() + beanName +
                        "Cannot register bean definition [" + beanDefinition + "] for bean '" + beanName +
                                "': There is already [" + oldBeanDefinition + "] bound.");
            }
            else if (oldBeanDefinition.getRole() < beanDefinition.getRole()) {
                // e.g. was ROLE_APPLICATION, now overriding with ROLE_SUPPORT or ROLE_INFRASTRUCTURE
                System.out.println("Overriding user-defined bean definition for bean '" + beanName +
                        "' with a framework-generated bean definition: replacing [" +
                        oldBeanDefinition + "] with [" + beanDefinition + "]");
            }
            else if (!beanDefinition.equals(oldBeanDefinition)) {
                System.out.println("Overriding bean definition for bean '" + beanName +
                        "' with a different definition: replacing [" + oldBeanDefinition +
                        "] with [" + beanDefinition + "]");
            }
            else {
                System.out.println("Overriding bean definition for bean '" + beanName +
                        "' with an equivalent definition: replacing [" + oldBeanDefinition +
                        "] with [" + beanDefinition + "]");
            }
            this.beanDefinitionMap.put(beanName, beanDefinition);
        }
        else {
            if (hasBeanCreationStarted()) {
                // Cannot modify startup-time collection elements anymore (for stable iteration)
                synchronized (this.beanDefinitionMap) {
                    this.beanDefinitionMap.put(beanName, beanDefinition);
                    List<String> updatedDefinitions = new ArrayList<String>(this.beanDefinitionNames.size() + 1);
                    updatedDefinitions.addAll(this.beanDefinitionNames);
                    updatedDefinitions.add(beanName);
                    this.beanDefinitionNames = updatedDefinitions;
                    if (this.manualSingletonNames.contains(beanName)) {
                        Set<String> updatedSingletons = new LinkedHashSet<String>(this.manualSingletonNames);
                        updatedSingletons.remove(beanName);
                        this.manualSingletonNames = updatedSingletons;
                    }
                }
            }
            else {
                // Still in startup registration phase
                this.beanDefinitionMap.put(beanName, beanDefinition);
                this.beanDefinitionNames.add(beanName);
                this.manualSingletonNames.remove(beanName);
            }
            this.frozenBeanDefinitionNames = null;
        }

        if (oldBeanDefinition != null || containsSingleton(beanName)) {
            resetBeanDefinition(beanName);
        }
    }

    protected void resetBeanDefinition(String beanName) {
        // Remove the merged bean definition for the given bean, if already created.
        clearMergedBeanDefinition(beanName);

        // Remove corresponding bean from singleton cache, if any. Shouldn't usually
        // be necessary, rather just meant for overriding a context's default beans
        // (e.g. the default StaticMessageSource in a StaticApplicationContext).
        destroySingleton(beanName);

        // Reset all bean definitions that have the given bean as parent (recursively).
        for (String bdName : this.beanDefinitionNames) {
            if (!beanName.equals(bdName)) {
                BeanDefinition bd = this.beanDefinitionMap.get(bdName);
                if (beanName.equals(bd.getParentName())) {
                    resetBeanDefinition(bdName);
                }
            }
        }
    }

    @Override
    public void destroySingleton(String beanName) {
        super.destroySingleton(beanName);
        this.manualSingletonNames.remove(beanName);
        clearByTypeCache();
    }

    private void clearByTypeCache() {
        this.allBeanNamesByType.clear();
        this.singletonBeanNamesByType.clear();
    }

    public void setSerializationId(String serializationId) {
        if (serializationId != null) {
            serializableFactories.put(serializationId, new WeakReference<DefaultListableBeanFactory>(this));
        }
        else if (this.serializationId != null) {
            serializableFactories.remove(this.serializationId);
        }
        this.serializationId = serializationId;
    }

    @Override
    public void registerResolvableDependency(Class<?> dependencyType, Object autowiredValue) {
        Assert.notNull(dependencyType, "Dependency type must not be null");
        if (autowiredValue != null) {
//            if (!(autowiredValue instanceof ObjectFactory || dependencyType.isInstance(autowiredValue))) {
//                throw new IllegalArgumentException("Value [" + autowiredValue +
//                        "] does not implement specified dependency type [" + dependencyType.getName() + "]");
//            }
            this.resolvableDependencies.put(dependencyType, autowiredValue);
        }
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        BeanDefinition bd = this.beanDefinitionMap.get(beanName);
        if (bd == null) {
             System.out.println("No bean named '" + beanName + "' found in " + this);
        }
        return bd;
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) throws IllegalStateException {
        super.registerSingleton(beanName, singletonObject);

        if (hasBeanCreationStarted()) {
            // Cannot modify startup-time collection elements anymore (for stable iteration)
            synchronized (this.beanDefinitionMap) {
                if (!this.beanDefinitionMap.containsKey(beanName)) {
                    Set<String> updatedSingletons = new LinkedHashSet<String>(this.manualSingletonNames.size() + 1);
                    updatedSingletons.addAll(this.manualSingletonNames);
                    updatedSingletons.add(beanName);
                    this.manualSingletonNames = updatedSingletons;
                }
            }
        }
        else {
            // Still in startup registration phase
            if (!this.beanDefinitionMap.containsKey(beanName)) {
                this.manualSingletonNames.add(beanName);
            }
        }

        clearByTypeCache();
    }


}