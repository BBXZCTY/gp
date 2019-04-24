package com.test;

import com.tom.demo.mvc.controller.DemoController;
import com.tom.framework.annotation.TomRequestMapping;
import com.tom.framework.annotation.TomRequestParam;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TT {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        Map<String, Object> map = new ConcurrentHashMap<String, Object>();
        map.put("name", "zhangs");
        map.put("age", 23);
        map.put("addr", "深圳");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry);
            System.out.println("key=" + entry.getKey() + " value=" + entry.getValue());
        }

        Class<DemoController> demoActionClass = DemoController.class;
        TomRequestMapping requestMapping = demoActionClass.getAnnotation(TomRequestMapping.class);
        System.out.println(requestMapping);
        System.out.println(requestMapping.value());
        Method[] methods = demoActionClass.getMethods();

        Class<?> aClass = Class.forName("com.tom.demo.mvc.controller.DemoController");
        System.out.println(666);
        System.out.println(aClass.getSimpleName());
        Object newInstance = aClass.newInstance();

        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println("declaredFild: " + declaredField);
            System.out.println("declaredFildType: " + declaredField.getType());
            System.out.println("declaredFildName: " + declaredField.getName());
        }

        Object[] objects = new Object[10];

        for (Method method : methods) {
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (Annotation[] parameterAnnotation : parameterAnnotations) {
                System.out.println("注解列表：" + Arrays.asList(parameterAnnotation));
            }
            Class<?>[] parameterTypes = method.getParameterTypes();
            Parameter[] parameters = method.getParameters();
            System.out.println("方法:" + method
                    + "\n方法参数类型数组：" + method.getParameterTypes() + "\n方法参数数组：" + parameters);
            for (Parameter parameter : parameters) {
                System.out.println("parameter = " + parameter);
                System.out.println("注解： " + parameter.getAnnotation(TomRequestParam.class));
                if (null != parameter.getAnnotation(TomRequestParam.class)) {
                    System.out.println("注解value: " + parameter.getAnnotation(TomRequestParam.class).value());
                }
                if (parameter.isAnnotationPresent(TomRequestParam.class)) {
                    System.out.println("该参数含有TomRequestParam注解");
                }
            }

            for (Class<?> parameterType : parameterTypes) {
                System.out.println("parameterType = " + parameterType);
            }
            System.out.println("====================================");

            /*
            if (method.isAnnotationPresent(TomRequestMapping.class)) {
                System.out.println("含有requestMapping注解的方法：" + method
                        + ", 该注解的value为：" + method.getAnnotation(TomRequestMapping.class).value());
                System.out.println("类型长度：" + parameterTypes.length);
                for (Class<?> parameterType : parameterTypes) {
                    System.out.println("parameterType=" + parameterType);
                }
                Object invoke = method.invoke(newInstance, args);
                System.out.println(invoke);
            }
            */
        }


        String str = "[zhangsan]";
        str = str.replaceAll("\\[|\\]", "");
        System.out.println(str);

        String className = "UserServiceImpl";
        System.out.println(String.valueOf(className.charAt(0)).toLowerCase());
        System.out.println(className.replaceFirst(String.valueOf(className.charAt(0)), String.valueOf(className.charAt(0)).toLowerCase()));

    }
}