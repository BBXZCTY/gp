package com.tom.springframework.core.annotation;

import com.tom.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.LinkedHashMap;
import java.util.Map;

public class AnnotationAttributes extends LinkedHashMap<String, Object> {

    private static final String UNKNOWN = "unknown";

    private final Class<? extends Annotation> annotationType;

    private final String displayName;

    boolean validated = false;

    public AnnotationAttributes(Map<String, Object> map) {
        super(map);
        this.annotationType = null;
        this.displayName = UNKNOWN;
    }

    public static AnnotationAttributes fromMap(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        if (map instanceof AnnotationAttributes) {
            return (AnnotationAttributes) map;
        }
        return new AnnotationAttributes(map);
    }

    public String getString(String attributeName) {
        return getRequiredAttribute(attributeName, String.class);
    }

    private <T> T getRequiredAttribute(String attributeName, Class<T> expectedType) {
        Assert.hasText(attributeName, "'attributeName' must not be null or empty");
        Object value = get(attributeName);
        assertAttributePresence(attributeName, value);
        assertNotException(attributeName, value);
        if (!expectedType.isInstance(value) && expectedType.isArray() &&
                expectedType.getComponentType().isInstance(value)) {
            Object array = Array.newInstance(expectedType.getComponentType(), 1);
            Array.set(array, 0, value);
            value = array;
        }
        assertAttributeType(attributeName, value, expectedType);
        return (T) value;
    }

    private void assertAttributePresence(String attributeName, Object attributeValue) {
        if (attributeValue == null) {
            throw new IllegalArgumentException(String.format(
                    "Attribute '%s' not found in attributes for annotation [%s]", attributeName, this.displayName));
        }
    }

    private void assertNotException(String attributeName, Object attributeValue) {
        if (attributeValue instanceof Exception) {
            throw new IllegalArgumentException(String.format(
                    "Attribute '%s' for annotation [%s] was not resolvable due to exception [%s]",
                    attributeName, this.displayName, attributeValue), (Exception) attributeValue);
        }
    }

    private void assertAttributeType(String attributeName, Object attributeValue, Class<?> expectedType) {
        if (!expectedType.isInstance(attributeValue)) {
            throw new IllegalArgumentException(String.format(
                    "Attribute '%s' is of type [%s], but [%s] was expected in attributes for annotation [%s]",
                    attributeName, attributeValue.getClass().getSimpleName(), expectedType.getSimpleName(),
                    this.displayName));
        }
    }

    public <E extends Enum<?>> E getEnum(String attributeName) {
        return (E) getRequiredAttribute(attributeName, Enum.class);
    }

}