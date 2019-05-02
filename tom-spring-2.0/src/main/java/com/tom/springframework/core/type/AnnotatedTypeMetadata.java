package com.tom.springframework.core.type;

import java.util.Map;

public interface AnnotatedTypeMetadata {

    Map<String, Object> getAnnotationAttributes(String annotationName, boolean classValuesAsString);

    boolean isAnnotated(String annotationName);
}
