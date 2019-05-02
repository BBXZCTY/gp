package com.tom.springframework.core;

import com.tom.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import com.tom.springframework.core.SerializableTypeWrapper.TypeProvider;

public class ResolvableType implements Serializable {


    /**
     * The underlying Java type being managed (only ever {@code null} for {@link #NONE}).
     */
    private final Type type;

    /**
     * Optional provider for the type.
     */
    private final TypeProvider typeProvider;

    /**
     * The {@code VariableResolver} to use or {@code null} if no resolver is available.
     */
    private final VariableResolver variableResolver;

    /**
     * The component type for an array or {@code null} if the type should be deduced.
     */
    private final ResolvableType componentType;

    /**
     * Copy of the resolved value.
     */
    private final Class<?> resolved;

    private final Integer hash;

    /**
     * Private constructor used to create a new {@link ResolvableType} for cache key purposes,
     * with no upfront resolution.
     */
    private ResolvableType(Type type, TypeProvider typeProvider, VariableResolver variableResolver) {
        this.type = type;
        this.typeProvider = typeProvider;
        this.variableResolver = variableResolver;
        this.componentType = null;
        this.resolved = null;
        this.hash = calculateHashCode();
    }

    public Class<?> resolve() {
        return resolve(null);
    }

    public Class<?> resolve(Class<?> fallback) {
        return (this.resolved != null ? this.resolved : fallback);
    }

    private int calculateHashCode() {
        int hashCode = ObjectUtils.nullSafeHashCode(this.type);
        if (this.typeProvider != null) {
            hashCode = 31 * hashCode + ObjectUtils.nullSafeHashCode(this.typeProvider.getType());
        }
        if (this.variableResolver != null) {
            hashCode = 31 * hashCode + ObjectUtils.nullSafeHashCode(this.variableResolver.getSource());
        }
        if (this.componentType != null) {
            hashCode = 31 * hashCode + ObjectUtils.nullSafeHashCode(this.componentType);
        }
        return hashCode;
    }

    interface VariableResolver extends Serializable {

        /**
         * Return the source of the resolver (used for hashCode and equals).
         */
        Object getSource();

        /**
         * Resolve the specified variable.
         * @param variable the variable to resolve
         * @return the resolved variable, or {@code null} if not found
         */
        ResolvableType resolveVariable(TypeVariable<?> variable);
    }

}