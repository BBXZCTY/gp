package com.tom.springframework.beans.factory.support;

import com.tom.springframework.beans.PropertyEditorRegistrar;
import com.tom.springframework.beans.factory.BeanFactory;
import com.tom.springframework.beans.factory.BeanFactoryUtils;
import com.tom.springframework.beans.factory.FactoryBean;
import com.tom.springframework.beans.factory.config.*;
import com.tom.springframework.util.Assert;
import com.tom.springframework.util.ClassUtils;
import com.tom.springframework.util.ObjectUtils;
import com.tom.springframework.util.StringUtils;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    /** Names of beans that have already been created at least once */
    private final Set<String> alreadyCreated =
            Collections.newSetFromMap(new ConcurrentHashMap<>(256));

    /** Map from bean name to merged RootBeanDefinition */
    private final Map<String, RootBeanDefinition> mergedBeanDefinitions =
            new ConcurrentHashMap<>(256);

    /** ClassLoader to resolve bean class names with, if necessary */
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    /** Resolution strategy for expressions in bean definition values */
    private BeanExpressionResolver beanExpressionResolver;

    /** Custom PropertyEditorRegistrars to apply to the beans of this factory */
    private final Set<PropertyEditorRegistrar> propertyEditorRegistrars = new LinkedHashSet<>(4);

    /** BeanPostProcessors to apply in createBean */
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    /** Indicates whether any InstantiationAwareBeanPostProcessors have been registered */
    private boolean hasInstantiationAwareBeanPostProcessors;

    /** Indicates whether any DestructionAwareBeanPostProcessors have been registered */
    private boolean hasDestructionAwareBeanPostProcessors;

    /** Parent bean factory, for bean inheritance support */
    private BeanFactory parentBeanFactory;

    /** ClassLoader to temporarily resolve bean class names with, if necessary */
    private ClassLoader tempClassLoader;

    /** Map from scope identifier String to corresponding Scope */
    private final Map<String, Scope> scopes = new LinkedHashMap<>(8);

    /** Security context used when running with a SecurityManager */
    private SecurityContextProvider securityContextProvider;

    /** Whether to cache bean metadata or rather reobtain it for every access */
    private boolean cacheBeanMetadata = true;

    @Override
    public boolean isCacheBeanMetadata() {
        return this.cacheBeanMetadata;
    }


    @Override
    public Object getBean(String name) throws Exception {
        return null;
    }

    protected abstract boolean containsBeanDefinition(String beanName);

    protected boolean hasBeanCreationStarted() {
        return !this.alreadyCreated.isEmpty();
    }

    protected void clearMergedBeanDefinition(String beanName) {
        this.mergedBeanDefinitions.remove(beanName);
    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = (beanClassLoader != null ? beanClassLoader : ClassUtils.getDefaultClassLoader());
    }

    @Override
    public void setBeanExpressionResolver(BeanExpressionResolver resolver) {
        this.beanExpressionResolver = resolver;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    @Override
    public void addPropertyEditorRegistrar(PropertyEditorRegistrar registrar) {
        Assert.notNull(registrar, "PropertyEditorRegistrar must not be null");
        this.propertyEditorRegistrars.add(registrar);
    }

    @Override
    public BeanExpressionResolver getBeanExpressionResolver() {
        return this.beanExpressionResolver;
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        Assert.notNull(beanPostProcessor, "BeanPostProcessor must not be null");
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
        if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
            this.hasInstantiationAwareBeanPostProcessors = true;
        }
        if (beanPostProcessor instanceof DestructionAwareBeanPostProcessor) {
            this.hasDestructionAwareBeanPostProcessors = true;
        }
    }

    @Override
    public boolean containsBean(String name) {
        String beanName = transformedBeanName(name);
        if (containsSingleton(beanName) || containsBeanDefinition(beanName)) {
            return (!BeanFactoryUtils.isFactoryDereference(name) || isFactoryBean(name));
        }
        // Not found -> check parent.
        BeanFactory parentBeanFactory = getParentBeanFactory();
        return (parentBeanFactory != null && parentBeanFactory.containsBean(originalBeanName(name)));
    }

    protected String transformedBeanName(String name) {
        return canonicalName(BeanFactoryUtils.transformedBeanName(name));
    }

    @Override
    public boolean isFactoryBean(String name) {
        String beanName = transformedBeanName(name);

        Object beanInstance = getSingleton(beanName, false);
        if (beanInstance != null) {
            return (beanInstance instanceof FactoryBean);
        }
        else if (containsSingleton(beanName)) {
            // null instance registered
            return false;
        }

        // No singleton instance found -> check bean definition.
        if (!containsBeanDefinition(beanName) && getParentBeanFactory() instanceof ConfigurableBeanFactory) {
            // No bean definition found in this factory -> delegate to parent.
            return ((ConfigurableBeanFactory) getParentBeanFactory()).isFactoryBean(name);
        }

        return isFactoryBean(beanName, getMergedLocalBeanDefinition(beanName));
    }

    protected boolean isFactoryBean(String beanName, RootBeanDefinition mbd) {
        Class<?> beanType = predictBeanType(beanName, mbd, FactoryBean.class);
        return (beanType != null && FactoryBean.class.isAssignableFrom(beanType));
    }

    protected Class<?> predictBeanType(String beanName, RootBeanDefinition mbd, Class<?>... typesToMatch) {
        Class<?> targetType = mbd.getTargetType();
        if (targetType != null) {
            return targetType;
        }
        if (mbd.getFactoryMethodName() != null) {
            return null;
        }
        return resolveBeanClass(mbd, beanName, typesToMatch);
    }

    @Override
    public BeanFactory getParentBeanFactory() {
        return this.parentBeanFactory;
    }

    protected String originalBeanName(String name) {
        String beanName = transformedBeanName(name);
        if (name.startsWith(FACTORY_BEAN_PREFIX)) {
            beanName = FACTORY_BEAN_PREFIX + beanName;
        }
        return beanName;
    }

    protected RootBeanDefinition getMergedLocalBeanDefinition(String beanName) {
        // Quick check on the concurrent map first, with minimal locking.
        RootBeanDefinition mbd = this.mergedBeanDefinitions.get(beanName);
        if (mbd != null) {
            return mbd;
        }
        return getMergedBeanDefinition(beanName, getBeanDefinition(beanName));
    }

    protected Class<?> resolveBeanClass(final RootBeanDefinition mbd, String beanName, final Class<?>... typesToMatch) {
        try {
            if (mbd.hasBeanClass()) {
                return mbd.getBeanClass();
            }
            if (System.getSecurityManager() != null) {
                return AccessController.doPrivileged(new PrivilegedExceptionAction<Class<?>>() {
                    @Override
                    public Class<?> run() throws Exception {
                        return doResolveBeanClass(mbd, typesToMatch);
                    }
                }, getAccessControlContext());
            }
            else {
                return doResolveBeanClass(mbd, typesToMatch);
            }
        }
        catch (PrivilegedActionException pae) {
            ClassNotFoundException ex = (ClassNotFoundException) pae.getException();
//            throw new CannotLoadBeanClassException(mbd.getResourceDescription(), beanName, mbd.getBeanClassName(), ex);
        }
        catch (ClassNotFoundException ex) {
//            throw new CannotLoadBeanClassException(mbd.getResourceDescription(), beanName, mbd.getBeanClassName(), ex);
        }
        catch (LinkageError ex) {
//            throw new CannotLoadBeanClassException(mbd.getResourceDescription(), beanName, mbd.getBeanClassName(), ex);
        }
        return null;
    }

    private Class<?> doResolveBeanClass(RootBeanDefinition mbd, Class<?>... typesToMatch)
            throws ClassNotFoundException {

        ClassLoader beanClassLoader = getBeanClassLoader();
        ClassLoader classLoaderToUse = beanClassLoader;
        if (!ObjectUtils.isEmpty(typesToMatch)) {
            // When just doing type checks (i.e. not creating an actual instance yet),
            // use the specified temporary class loader (e.g. in a weaving scenario).
            ClassLoader tempClassLoader = getTempClassLoader();
            if (tempClassLoader != null) {
                classLoaderToUse = tempClassLoader;
//                if (tempClassLoader instanceof DecoratingClassLoader) {
//                    DecoratingClassLoader dcl = (DecoratingClassLoader) tempClassLoader;
//                    for (Class<?> typeToMatch : typesToMatch) {
//                        dcl.excludeClass(typeToMatch.getName());
//                    }
//                }
            }
        }
        String className = mbd.getBeanClassName();
        if (className != null) {
            Object evaluated = evaluateBeanDefinitionString(className, mbd);
            if (!className.equals(evaluated)) {
                // A dynamically resolved expression, supported as of 4.2...
                if (evaluated instanceof Class) {
                    return (Class<?>) evaluated;
                }
                else if (evaluated instanceof String) {
                    return ClassUtils.forName((String) evaluated, classLoaderToUse);
                }
                else {
                    throw new IllegalStateException("Invalid class name expression result: " + evaluated);
                }
            }
            // When resolving against a temporary class loader, exit early in order
            // to avoid storing the resolved Class in the bean definition.
            if (classLoaderToUse != beanClassLoader) {
                return ClassUtils.forName(className, classLoaderToUse);
            }
        }
        return mbd.resolveBeanClass(beanClassLoader);
    }

    @Override
    public ClassLoader getTempClassLoader() {
        return this.tempClassLoader;
    }

    protected Object evaluateBeanDefinitionString(String value, BeanDefinition beanDefinition) {
        if (this.beanExpressionResolver == null) {
            return value;
        }
        Scope scope = (beanDefinition != null ? getRegisteredScope(beanDefinition.getScope()) : null);
        return this.beanExpressionResolver.evaluate(value, new BeanExpressionContext(this, scope));
    }

    @Override
    public Scope getRegisteredScope(String scopeName) {
        Assert.notNull(scopeName, "Scope identifier must not be null");
        return this.scopes.get(scopeName);
    }

    @Override
    public AccessControlContext getAccessControlContext() {
        return (this.securityContextProvider != null ?
                this.securityContextProvider.getAccessControlContext() :
                AccessController.getContext());
    }

    protected RootBeanDefinition getMergedBeanDefinition(String beanName, BeanDefinition bd) {

        return getMergedBeanDefinition(beanName, bd, null);
    }

    protected RootBeanDefinition getMergedBeanDefinition(
            String beanName, BeanDefinition bd, BeanDefinition containingBd) {

        synchronized (this.mergedBeanDefinitions) {
            RootBeanDefinition mbd = null;

            // Check with full lock now in order to enforce the same merged instance.
            if (containingBd == null) {
                mbd = this.mergedBeanDefinitions.get(beanName);
            }

            if (mbd == null) {
                if (bd.getParentName() == null) {
                    // Use copy of given root bean definition.
                    if (bd instanceof RootBeanDefinition) {
                        mbd = ((RootBeanDefinition) bd).cloneBeanDefinition();
                    }
                    else {
                        mbd = new RootBeanDefinition(bd);
                    }
                }
                else {
                    // Child bean definition: needs to be merged with parent.
                    BeanDefinition pbd = null;
                    try {
                        String parentBeanName = transformedBeanName(bd.getParentName());
                        if (!beanName.equals(parentBeanName)) {
                            pbd = getMergedBeanDefinition(parentBeanName);
                        }
                        else {
                            BeanFactory parent = getParentBeanFactory();
                            if (parent instanceof ConfigurableBeanFactory) {
                                pbd = ((ConfigurableBeanFactory) parent).getMergedBeanDefinition(parentBeanName);
                            }
                            else {
                                System.out.println("Parent name '" + parentBeanName + "' is equal to bean name '" + beanName +
                                        "': cannot be resolved without an AbstractBeanFactory parent");
                            }
                        }
                    }
                    catch (Exception ex) {
                        System.out.println("Could not resolve parent bean definition '" + bd.getParentName() + "'" + ex);
                    }
                    // Deep copy with overridden values.
                    mbd = new RootBeanDefinition(pbd);
//                    mbd.overrideFrom(bd);
                }

                // Set default singleton scope, if not configured before.
                if (!StringUtils.hasLength(mbd.getScope())) {
                    mbd.setScope(RootBeanDefinition.SCOPE_SINGLETON);
                }

                // A bean contained in a non-singleton bean cannot be a singleton itself.
                // Let's correct this on the fly here, since this might be the result of
                // parent-child merging for the outer bean, in which case the original inner bean
                // definition will not have inherited the merged outer bean's singleton status.
                if (containingBd != null && !containingBd.isSingleton() && mbd.isSingleton()) {
                    mbd.setScope(containingBd.getScope());
                }

                // Cache the merged bean definition for the time being
                // (it might still get re-merged later on in order to pick up metadata changes)
                if (containingBd == null && isCacheBeanMetadata()) {
                    this.mergedBeanDefinitions.put(beanName, mbd);
                }
            }

            return mbd;
        }
    }

    @Override
    public BeanDefinition getMergedBeanDefinition(String name) {
        String beanName = transformedBeanName(name);

        // Efficiently check whether bean definition exists in this factory.
        if (!containsBeanDefinition(beanName) && getParentBeanFactory() instanceof ConfigurableBeanFactory) {
            return ((ConfigurableBeanFactory) getParentBeanFactory()).getMergedBeanDefinition(beanName);
        }
        // Resolve merged bean definition locally.
        return getMergedLocalBeanDefinition(beanName);
    }

    @Override
    public boolean containsLocalBean(String name) {
        String beanName = transformedBeanName(name);
        return ((containsSingleton(beanName) || containsBeanDefinition(beanName)) &&
                (!BeanFactoryUtils.isFactoryDereference(name) || isFactoryBean(beanName)));
    }

}