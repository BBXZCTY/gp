package com.tom.springframework.beans;

public class BeanMetadataAttributeAccessor implements BeanMetadataElement {

    private Object source;

    @Override
    public Object getSource() {
        return this.source;
    }

    public void setSource(Object source) {
        this.source = source;
    }
}