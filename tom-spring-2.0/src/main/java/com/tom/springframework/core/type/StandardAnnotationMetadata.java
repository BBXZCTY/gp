package com.tom.springframework.core.type;

import com.tom.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.util.Map;

public class StandardAnnotationMetadata extends StandardClassMetadata implements AnnotationMetadata {

    private final Annotation[] annotations;

    private final boolean nestedAnnotationsAsMap;


    public StandardAnnotationMetadata(Class<?> introspectedClass, boolean nestedAnnotationsAsMap) {
        super(introspectedClass);
        this.annotations = introspectedClass.getAnnotations();
        this.nestedAnnotationsAsMap = nestedAnnotationsAsMap;
    }

    @Override
    public Map<String, Object> getAnnotationAttributes(String annotationName, boolean classValuesAsString) {
        return (this.annotations.length > 0 ? AnnotatedElementUtils.getMergedAnnotationAttributes(
                getIntrospectedClass(), annotationName, classValuesAsString, this.nestedAnnotationsAsMap) : null);
    }

    @Override
    public boolean isAnnotated(String annotationName) {
        return (this.annotations.length > 0 &&
                AnnotatedElementUtils.isAnnotated(getIntrospectedClass(), annotationName));
    }

}