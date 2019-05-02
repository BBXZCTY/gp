package com.tom.springframework.context;

import com.tom.springframework.beans.factory.Aware;

public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext);

}
