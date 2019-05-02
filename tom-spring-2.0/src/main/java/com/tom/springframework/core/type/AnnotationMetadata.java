package com.tom.springframework.core.type;

import java.util.Map;

public interface AnnotationMetadata extends ClassMetadata, AnnotatedTypeMetadata {

    Map<String, Object> getAnnotationAttributes(String annotationName, boolean classValuesAsString);

}