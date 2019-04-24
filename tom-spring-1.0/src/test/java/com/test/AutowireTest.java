package com.test;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class AutowireTest {

    private String name;
    @Resource
    private Integer age;
    public Long id;

    @Resource
    private TT tt;


    @Override
    public String toString() {
        return "AutowireTest{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                ", tt=" + tt +
                '}';
    }

    public static void main(String[] args) {
        AutowireTest autowireTest = new AutowireTest();

        Field[] declaredFields = AutowireTest.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println();
            System.out.println(declaredField);
            System.out.println(declaredField.isAnnotationPresent(Resource.class));
            System.out.println(declaredField.getType());
            System.out.println(declaredField.getType().getName());
//            System.out.println(declaredField.getType().get);
            if (declaredField.isAnnotationPresent(Resource.class)) {
                declaredField.setAccessible(true);
                try {
                    Class<?> aClass = Class.forName(declaredField.getType().getName());
                    Object newInstance = aClass.newInstance();
                    System.out.println(newInstance);
                    declaredField.set(autowireTest, newInstance);
                    System.out.println(autowireTest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            Annotation[] declaredAnnotations = declaredField.getDeclaredAnnotations();
            for (Annotation declaredAnnotation : declaredAnnotations) {
                System.out.println(declaredAnnotation);
            }
        }
    }


}