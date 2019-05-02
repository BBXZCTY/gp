package com.tom.springframework.beans.factory.support;

import com.tom.springframework.beans.factory.DisposableBean;
import com.tom.springframework.beans.factory.ObjectFactory;
import com.tom.springframework.beans.factory.config.SingletonBeanRegistry;
import com.tom.springframework.core.SimpleAliasRegistry;
import com.tom.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {

    /** Cache of singleton objects: bean name --> bean instance */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    /** Cache of singleton factories: bean name --> ObjectFactory */
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);

    /** Cache of early singleton objects: bean name --> bean instance */
    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

    /** Set of registered singletons, containing the bean names in registration order */
    private final Set<String> registeredSingletons = new LinkedHashSet<>(256);

    /** Disposable bean instances: bean name --> disposable instance */
    private final Map<String, Object> disposableBeans = new LinkedHashMap<>();

    /** Map between dependent bean names: bean name --> Set of dependent bean names */
    private final Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);

    /** Map between containing bean names: bean name --> Set of bean names that the bean contains */
    private final Map<String, Set<String>> containedBeanMap = new ConcurrentHashMap<>(16);

    /** Map between depending bean names: bean name --> Set of bean names for the bean's dependencies */
    private final Map<String, Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>(64);

    /** Names of beans that are currently in creation */
    private final Set<String> singletonsCurrentlyInCreation =
            Collections.newSetFromMap(new ConcurrentHashMap<>(16));

    protected static final Object NULL_OBJECT = new Object();


    @Override
    public boolean containsSingleton(String beanName) {
        return this.singletonObjects.containsKey(beanName);
    }

    public void destroySingleton(String beanName) {
        // Remove a registered singleton of the given name, if any.
        removeSingleton(beanName);

        // Destroy the corresponding DisposableBean instance.
        DisposableBean disposableBean;
        synchronized (this.disposableBeans) {
            disposableBean = (DisposableBean) this.disposableBeans.remove(beanName);
        }
        destroyBean(beanName, disposableBean);
    }

    protected void removeSingleton(String beanName) {
        synchronized (this.singletonObjects) {
            this.singletonObjects.remove(beanName);
            this.singletonFactories.remove(beanName);
            this.earlySingletonObjects.remove(beanName);
            this.registeredSingletons.remove(beanName);
        }
    }

    protected void destroyBean(String beanName, DisposableBean bean) {
        // Trigger destruction of dependent beans first...
        Set<String> dependencies = this.dependentBeanMap.remove(beanName);
        if (dependencies != null) {
            System.out.println("Retrieved dependent beans for bean '" + beanName + "': " + dependencies);
            for (String dependentBeanName : dependencies) {
                destroySingleton(dependentBeanName);
            }
        }

        // Actually destroy the bean now...
        if (bean != null) {
            try {
                bean.destroy();
            }
            catch (Throwable ex) {
                System.out.println("Destroy method on bean with name '" + beanName + "' threw an exception" + ex);
            }
        }

        // Trigger destruction of contained beans...
        Set<String> containedBeans = this.containedBeanMap.remove(beanName);
        if (containedBeans != null) {
            for (String containedBeanName : containedBeans) {
                destroySingleton(containedBeanName);
            }
        }

        // Remove destroyed bean from other beans' dependencies.
        synchronized (this.dependentBeanMap) {
            for (Iterator<Map.Entry<String, Set<String>>> it = this.dependentBeanMap.entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, Set<String>> entry = it.next();
                Set<String> dependenciesToClean = entry.getValue();
                dependenciesToClean.remove(beanName);
                if (dependenciesToClean.isEmpty()) {
                    it.remove();
                }
            }
        }

        // Remove destroyed bean's prepared dependency information.
        this.dependenciesForBeanMap.remove(beanName);
    }

    protected Object getSingleton(String beanName, boolean allowEarlyReference) {
        Object singletonObject = this.singletonObjects.get(beanName);
        if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
            synchronized (this.singletonObjects) {
                singletonObject = this.earlySingletonObjects.get(beanName);
                if (singletonObject == null && allowEarlyReference) {
                    ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
                    if (singletonFactory != null) {
                        singletonObject = singletonFactory.getObject();
                        this.earlySingletonObjects.put(beanName, singletonObject);
                        this.singletonFactories.remove(beanName);
                    }
                }
            }
        }
        return (singletonObject != NULL_OBJECT ? singletonObject : null);
    }

    public boolean isSingletonCurrentlyInCreation(String beanName) {
        return this.singletonsCurrentlyInCreation.contains(beanName);
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) throws IllegalStateException {
        Assert.notNull(beanName, "'beanName' must not be null");
        synchronized (this.singletonObjects) {
            Object oldObject = this.singletonObjects.get(beanName);
            if (oldObject != null) {
                throw new IllegalStateException("Could not register object [" + singletonObject +
                        "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
            }
            addSingleton(beanName, singletonObject);
        }
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletonObjects) {
            this.singletonObjects.put(beanName, (singletonObject != null ? singletonObject : NULL_OBJECT));
            this.singletonFactories.remove(beanName);
            this.earlySingletonObjects.remove(beanName);
            this.registeredSingletons.add(beanName);
        }
    }



}
