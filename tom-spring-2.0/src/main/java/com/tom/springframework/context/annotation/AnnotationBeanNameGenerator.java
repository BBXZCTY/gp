package com.tom.springframework.context.annotation;

import com.tom.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import com.tom.springframework.beans.factory.config.BeanDefinition;
import com.tom.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.tom.springframework.beans.factory.support.BeanNameGenerator;
import com.tom.springframework.core.annotation.AnnotationAttributes;
import com.tom.springframework.core.type.AnnotationMetadata;
import com.tom.springframework.util.ClassUtils;
import com.tom.springframework.util.StringUtils;

import java.beans.Introspector;
import java.util.Set;

public class AnnotationBeanNameGenerator implements BeanNameGenerator {

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        if (definition instanceof AnnotatedBeanDefinition) {
            String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition) definition);
            if (StringUtils.hasText(beanName)) {
                // Explicit bean name found.
                return beanName;
            }
        }
        // Fallback: generate a unique default bean name.
        return buildDefaultBeanName(definition, registry);
    }

    protected String determineBeanNameFromAnnotation(AnnotatedBeanDefinition annotatedDef) {
        AnnotationMetadata amd = annotatedDef.getMetadata();
//        Set<String> types = amd.getAnnotationTypes();
        String beanName = null;
//        for (String type : types) {
//            AnnotationAttributes attributes = AnnotationConfigUtils.attributesFor(amd, type);
//            if (isStereotypeWithNameValue(type, amd.getMetaAnnotationTypes(type), attributes)) {
//                Object value = attributes.get("value");
//                if (value instanceof String) {
//                    String strVal = (String) value;
//                    if (StringUtils.hasLength(strVal)) {
//                        if (beanName != null && !strVal.equals(beanName)) {
//                            throw new IllegalStateException("Stereotype annotations suggest inconsistent " +
//                                    "component names: '" + beanName + "' versus '" + strVal + "'");
//                        }
//                        beanName = strVal;
//                    }
//                }
//            }
//        }
        return beanName;
    }

    protected String buildDefaultBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        return buildDefaultBeanName(definition);
    }

    protected String buildDefaultBeanName(BeanDefinition definition) {
        String shortClassName = ClassUtils.getShortName(definition.getBeanClassName());
        return Introspector.decapitalize(shortClassName);
    }

}