package com.tom.springframework.context;

import com.tom.springframework.beans.factory.Aware;

public interface MessageSourceAware extends Aware {

    void setMessageSource(MessageSource messageSource);

}
