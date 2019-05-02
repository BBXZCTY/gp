package com.tom.springframework.core.env;

import java.util.Map;

public interface ConfigurableEnvironment extends Environment {

    Map<String, Object> getSystemProperties();

    Map<String, Object> getSystemEnvironment();

}
