package com.tom.springframework.core.annotation;

import com.tom.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashSet;

public class AnnotatedElementUtils {

    public static AnnotationAttributes getMergedAnnotationAttributes(AnnotatedElement element,
                                                                     String annotationName, boolean classValuesAsString, boolean nestedAnnotationsAsMap) {

        Assert.hasLength(annotationName, "'annotationName' must not be null or empty");
//        AnnotationAttributes attributes = searchWithGetSemantics(element, null, annotationName,
//                new MergedAnnotationAttributesProcessor(classValuesAsString, nestedAnnotationsAsMap));
//        AnnotationUtils.postProcessAnnotationAttributes(element, attributes, classValuesAsString, nestedAnnotationsAsMap);
//        return attributes;
        return new AnnotationAttributes(null);
    }

    private static <T> T searchWithGetSemantics(AnnotatedElement element, Class<? extends Annotation> annotationType,
                                                String annotationName, Processor<T> processor) {

        return searchWithGetSemantics(element, annotationType, annotationName, null, processor);
    }

    private static <T> T searchWithGetSemantics(AnnotatedElement element, Class<? extends Annotation> annotationType,
                                                String annotationName, Class<? extends Annotation> containerType, Processor<T> processor) {

//        try {
//            return searchWithGetSemantics(element, annotationType, annotationName, containerType, processor,
//                    new HashSet<AnnotatedElement>(), 0);
//        }
//        catch (Throwable ex) {
//            AnnotationUtils.rethrowAnnotationConfigurationException(ex);
//            throw new IllegalStateException("Failed to introspect annotations on " + element, ex);
//        }
        return null;
    }

    public static boolean isAnnotated(AnnotatedElement element, String annotationName) {
        Assert.notNull(element, "AnnotatedElement must not be null");
        Assert.hasLength(annotationName, "'annotationName' must not be null or empty");

//        return Boolean.TRUE.equals(searchWithGetSemantics(element, null, annotationName, alwaysTrueAnnotationProcessor));
        return true;
    }

    private interface Processor<T> {

    }

}