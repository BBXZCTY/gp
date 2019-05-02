package com.tom.springframework.core;


import java.io.Serializable;
import java.lang.reflect.Type;

class SerializableTypeWrapper {

    interface TypeProvider extends Serializable {

        /**
         * Return the (possibly non {@link Serializable}) {@link Type}.
         */
        Type getType();

        /**
         * Return the source of the type or {@code null}.
         */
        Object getSource();
    }

}