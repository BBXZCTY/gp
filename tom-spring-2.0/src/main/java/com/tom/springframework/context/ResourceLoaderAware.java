package com.tom.springframework.context;

import com.tom.springframework.beans.factory.Aware;
import com.tom.springframework.core.io.ResourceLoader;

public interface ResourceLoaderAware extends Aware {

    void setResourceLoader(ResourceLoader resourceLoader);

}
