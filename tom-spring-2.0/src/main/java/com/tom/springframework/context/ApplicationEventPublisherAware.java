package com.tom.springframework.context;

import com.tom.springframework.beans.factory.Aware;

public interface ApplicationEventPublisherAware extends Aware {

    void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher);

}
