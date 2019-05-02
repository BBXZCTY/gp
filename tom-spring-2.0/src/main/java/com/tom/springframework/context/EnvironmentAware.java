package com.tom.springframework.context;

import com.tom.springframework.beans.factory.Aware;
import com.tom.springframework.core.env.Environment;

public interface EnvironmentAware extends Aware {

    void setEnvironment(Environment environment);


}
