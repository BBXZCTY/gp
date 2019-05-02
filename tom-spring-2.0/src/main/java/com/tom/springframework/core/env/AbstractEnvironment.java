package com.tom.springframework.core.env;

import com.tom.springframework.core.SpringProperties;

import java.security.AccessControlException;
import java.util.Collections;
import java.util.Map;

/**
 * @Author: hfr
 * @Date: 2019-04-26 17:24
 * @Version 1.0
 */
public class AbstractEnvironment implements ConfigurableEnvironment {

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Map<String, Object> getSystemProperties() {
        try {
            return (Map) System.getProperties();
        }
        catch (AccessControlException ex) {
//            return (Map) new ReadOnlySystemAttributesMap() {
//                @Override
//                protected String getSystemAttribute(String attributeName) {
//                    try {
//                        return System.getProperty(attributeName);
//                    }
//                    catch (AccessControlException ex) {
//                        if (logger.isInfoEnabled()) {
//                            logger.info("Caught AccessControlException when accessing system property '" +
//                                    attributeName + "'; its value will be returned [null]. Reason: " + ex.getMessage());
//                        }
//                        return null;
//                    }
//                }
//            };
        }
        return null;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Map<String, Object> getSystemEnvironment() {
        if (suppressGetenvAccess()) {
            return Collections.emptyMap();
        }
        try {
            return (Map) System.getenv();
        }
        catch (AccessControlException ex) {
//            return (Map) new ReadOnlySystemAttributesMap() {
//                @Override
//                protected String getSystemAttribute(String attributeName) {
//                    try {
//                        return System.getenv(attributeName);
//                    }
//                    catch (AccessControlException ex) {
//                        if (logger.isInfoEnabled()) {
//                            logger.info("Caught AccessControlException when accessing system environment variable '" +
//                                    attributeName + "'; its value will be returned [null]. Reason: " + ex.getMessage());
//                        }
//                        return null;
//                    }
//                }
//            };
            return null;
        }
    }

    protected boolean suppressGetenvAccess() {
//        return SpringProperties.getFlag(IGNORE_GETENV_PROPERTY_NAME);
        return true;
    }

}