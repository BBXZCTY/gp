package com.tom.springframework.context;

import com.tom.springframework.beans.factory.Aware;
import com.tom.springframework.util.StringValueResolver;

public interface EmbeddedValueResolverAware extends Aware {
    /**
     * Set the StringValueResolver to use for resolving embedded definition values.
     */
    void setEmbeddedValueResolver(StringValueResolver resolver);

}
