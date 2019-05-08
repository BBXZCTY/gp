package com.tom.design.pattern.singleton.container;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ContainerSingleton {

    private ContainerSingleton(){}

    private static Map<String, Object> IOC = new ConcurrentHashMap<String, Object>();

    public static Object getBean(String className) {
        synchronized (IOC) {
            if (!IOC.containsKey(className)) {
                Object obj = null;
                try {
                    obj = Class.forName(className).newInstance();
                    IOC.put(className, obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return obj;
            } else {
                return IOC.get(className);
            }
        }
    }

}