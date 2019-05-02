package com.tom.springframework.core;

import java.util.Properties;

public abstract class SpringProperties {

    private static final Properties localProperties = new Properties();

    public static String getProperty(String key) {
        String value = localProperties.getProperty(key);
        if (value == null) {
            try {
                value = System.getProperty(key);
            }
            catch (Throwable ex) {
                System.out.println("Could not retrieve system property '" + key + "': " + ex);
            }
        }
        return value;
    }

}