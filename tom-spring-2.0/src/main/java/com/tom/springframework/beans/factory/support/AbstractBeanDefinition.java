package com.tom.springframework.beans.factory.support;

import com.tom.springframework.beans.BeanMetadataAttributeAccessor;
import com.tom.springframework.beans.factory.config.BeanDefinition;
import com.tom.springframework.core.io.Resource;
import com.tom.springframework.util.ClassUtils;

public abstract class AbstractBeanDefinition extends BeanMetadataAttributeAccessor implements BeanDefinition {

    private int role = BeanDefinition.ROLE_APPLICATION;

    public static final String SCOPE_DEFAULT = "";

    private Resource resource;

    private volatile Object beanClass;

    private String scope = SCOPE_DEFAULT;

    private String factoryMethodName;

    @Override
    public String getResourceDescription() {
        return (this.resource != null ? this.resource.getDescription() : null);
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public int getRole() {
        return this.role;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String getBeanClassName() {
        Object beanClassObject = this.beanClass;
        if (beanClassObject instanceof Class) {
            return ((Class<?>) beanClassObject).getName();
        }
        else {
            return (String) beanClassObject;
        }
    }

    @Override
    public String getFactoryMethodName() {
        return this.factoryMethodName;
    }

    public boolean hasBeanClass() {
        return (this.beanClass instanceof Class);
    }

    public Class<?> getBeanClass() throws IllegalStateException {
        Object beanClassObject = this.beanClass;
        if (beanClassObject == null) {
            throw new IllegalStateException("No bean class specified on bean definition");
        }
        if (!(beanClassObject instanceof Class)) {
            throw new IllegalStateException(
                    "Bean class name [" + beanClassObject + "] has not been resolved into an actual Class");
        }
        return (Class<?>) beanClassObject;
    }

    public Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException {
        String className = getBeanClassName();
        if (className == null) {
            return null;
        }
        Class<?> resolvedClass = ClassUtils.forName(className, classLoader);
        this.beanClass = resolvedClass;
        return resolvedClass;
    }

    @Override
    public String getScope() {
        return this.scope;
    }

    public abstract AbstractBeanDefinition cloneBeanDefinition();

    protected AbstractBeanDefinition(BeanDefinition original) {
//        setParentName(original.getParentName());
//        setBeanClassName(original.getBeanClassName());
        setScope(original.getScope());
//        setAbstract(original.isAbstract());
//        setLazyInit(original.isLazyInit());
//        setFactoryBeanName(original.getFactoryBeanName());
//        setFactoryMethodName(original.getFactoryMethodName());
//        setConstructorArgumentValues(new ConstructorArgumentValues(original.getConstructorArgumentValues()));
//        setPropertyValues(new MutablePropertyValues(original.getPropertyValues()));
        setRole(original.getRole());
//        setSource(original.getSource());
//        copyAttributesFrom(original);

        if (original instanceof AbstractBeanDefinition) {
            AbstractBeanDefinition originalAbd = (AbstractBeanDefinition) original;
            if (originalAbd.hasBeanClass()) {
                setBeanClass(originalAbd.getBeanClass());
            }
//            setAutowireMode(originalAbd.getAutowireMode());
//            setDependencyCheck(originalAbd.getDependencyCheck());
//            setDependsOn(originalAbd.getDependsOn());
//            setAutowireCandidate(originalAbd.isAutowireCandidate());
//            setPrimary(originalAbd.isPrimary());
//            copyQualifiersFrom(originalAbd);
//            setNonPublicAccessAllowed(originalAbd.isNonPublicAccessAllowed());
//            setLenientConstructorResolution(originalAbd.isLenientConstructorResolution());
//            setMethodOverrides(new MethodOverrides(originalAbd.getMethodOverrides()));
//            setInitMethodName(originalAbd.getInitMethodName());
//            setEnforceInitMethod(originalAbd.isEnforceInitMethod());
//            setDestroyMethodName(originalAbd.getDestroyMethodName());
//            setEnforceDestroyMethod(originalAbd.isEnforceDestroyMethod());
//            setSynthetic(originalAbd.isSynthetic());
//            setResource(originalAbd.getResource());
        }
        else {
//            setResourceDescription(original.getResourceDescription());
        }
    }

    protected AbstractBeanDefinition() {
//        this(null, null);
    }

//    protected AbstractBeanDefinition(ConstructorArgumentValues cargs, MutablePropertyValues pvs) {
//        setConstructorArgumentValues(cargs);
//        setPropertyValues(pvs);
//    }

    @Override
    public boolean isSingleton() {
        return SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
    }

}